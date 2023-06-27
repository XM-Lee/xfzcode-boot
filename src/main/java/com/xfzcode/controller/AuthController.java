package com.xfzcode.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.xfzcode.annotation.OperationLogAnnotation;
import com.xfzcode.config.redis.RedisService;
import com.xfzcode.constants.*;
import com.xfzcode.exception.AuthException;
import com.xfzcode.pojo.LoginUser;
import com.xfzcode.pojo.User;
import com.xfzcode.service.PermissionService;
import com.xfzcode.service.UserService;
import com.xfzcode.utils.JacksonUtil;
import com.xfzcode.utils.JwtUtils;
import com.xfzcode.utils.auth.VerifyCodeUtil;
import com.xfzcode.vo.auth.LoginUserVo;
import com.xfzcode.vo.auth.UserInfoVo;
import com.xfzcode.vo.auth.VerifyCodeVo;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.util.*;

import static com.xfzcode.constants.ApiVersion.V1_AUTH;
import static com.xfzcode.constants.Constants.*;
import static com.xfzcode.exception.AuthException.accountLocked;
import static com.xfzcode.exception.AuthException.usernameOrPasswordInvalid;
import static com.xfzcode.utils.JwtUtils.decodedJWT;

/**
 * @Author: XMLee
 * @Date: 2023/6/27 16:05
 * @Description:
 */
@Slf4j
@RestController
@RequestMapping(ApiVersion.V1_AUTH)
@ApiOperation("登录相关接口-AuthController")
public class AuthController {

    @Autowired
    private Environment env;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisService redisService;


    @Autowired
    private PermissionService permissionService;


    @Autowired
    private UserService userService;

    @PostMapping("/login")
    @OperationLogAnnotation(description = "登录", detailParam = "LoginUserVo")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "LoginUserVo",value = "手机号",required = true)
    })
    public HttpResult<?> login(@RequestBody LoginUserVo loginUserVo) {
        try {
            if (StringUtils.isBlank(loginUserVo.getVerCode()) || StringUtils.isBlank(loginUserVo.getVerKey())) {
                return HttpResult.failure(ResultCode.VERIFY_CODE_FAIL);
            }
            String codeInRedis = redisService.get(loginUserVo.getVerKey());
            if (StringUtils.isBlank(codeInRedis)) {
                return HttpResult.failure(ResultCode.VERIFY_CODE_FAIL);
            }
            //TODO 测试环境跳过验证码校验
            if (!loginUserVo.getVerCode().equalsIgnoreCase(codeInRedis)) {
                if (!isDevOrPrevEnv()) {
                    return HttpResult.failure(ResultCode.VERIFY_CODE_FAIL);
                } else {
                    log.info("Skip captcha verify at dev env {}", loginUserVo.getUsername());
                }
            }
            redisService.del(loginUserVo.getVerKey());

            // 创建Authentication对象
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUserVo.getUsername(), loginUserVo.getPassword());

            // 调用AuthenticationManager的authenticate方法进行认证
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            if (authentication == null) {
                throw usernameOrPasswordInvalid();
            }

            // 将用户的数据存储到Redis中
            LoginUser loginUser = (LoginUser) authentication.getPrincipal();
            if (!loginUser.getUser().isEnabled()) {
                throw new AuthException(ResultMessage.ACCOUNT_NOT_ENABLE);
            }
            if (!loginUser.getUser().isAccountNonLocked()) {
                throw accountLocked();
            }
            String userId = loginUser.getUser().getId().toString();
            // 生成JWT令牌并进行返回
            Map<String, String> map = new HashMap<>();
            map.put("userId", userId);
            map.put("userName", loginUser.getUser().getUsername());
            map.put("nickName", loginUser.getUser().getRealName());
            String token = JwtUtils.getToken(map, Calendar.HOUR, 24 * 3);
            // 构建返回数据
            UserInfoVo userInfoVo = new UserInfoVo();
            userInfoVo.setUser(loginUser.getUser());
            userInfoVo.setToken(token);
            userInfoVo.setPermissions(loginUser.getPermissions());
            userInfoVo.setPermissionDetail(permissionService.findUserPermissionDetailById(loginUser.getUser().getId()));
            //todo 调整
            redisService.set("login_user:" + userId, JacksonUtil.toJson(userInfoVo), 60 * 60 * 24 * 3);
            return HttpResult.success(userInfoVo);

        } catch (Exception ex) {
            //throw new AuthException(ex.getMessage());
            ex.printStackTrace();
            return HttpResult.failure(ResultCode.UNAUTHORIZED, ex.getMessage());
        }
    }

    @GetMapping("/getVerCode")
    @OperationLogAnnotation(description = "获取验证码")
    public HttpResult captchaRegisterUserAccount() {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            String verifyCode = VerifyCodeUtil.generateVerifyCode(200, 50, 4, outputStream);
            String img = Base64.getEncoder().encodeToString(outputStream.toByteArray());
            String key = Constants.CAPTCHA_REDIS_PREFIX + UUID.randomUUID();
            redisService.set(key, verifyCode, 5 * 60);
            return HttpResult.success(new VerifyCodeVo("data:image/jpeg;base64," + img, key));
        } catch (Exception e) {
            log.error("", e);
            return HttpResult.failure(e.getMessage());
        }
    }

    @GetMapping("/getPermission")
    @OperationLogAnnotation(description = "获取用户权限")
    public HttpResult getPermission(@RequestHeader("Authorization") String token) {

        if (token == null || "".equals(token)) {
            throw new AuthException(HttpStatus.UNAUTHORIZED, ResultMessage.PERMISSION_DENIED);
        }
        try {

            token = token.replaceAll("Bearer ", "");
            String decodeToken = decodedJWT(token).getToken();
            DecodedJWT decode = JWT.decode(decodeToken);
            String userId = decode.getClaim("userId").asString();
            /*String resultInRedis = (String) redisOperator.get(USER_PERMISSION_PREFIX + userId);
            if (StringUtils.isNotBlank(resultInRedis)) {
                Map<String, Object> resultMap = Jackson.parseMap(resultInRedis, String.class, Object.class);
                return HttpResult.success(resultMap);
            }*/
            List<String> permsList = permissionService.findUserPermissionById(Long.valueOf(userId));
            User user = userService.getById(userId);
            HashMap<String, Object> result = new HashMap<>();
            result.put("userInfo", user);
            result.put("permsList", permsList);
            redisService.set(USER_PERMISSION_PREFIX + userId, JacksonUtil.toJson(result), 20 * 60);
            return HttpResult.success(result);
        } catch (Exception e) {
            log.error("", e);
            throw new AuthException(HttpStatus.UNAUTHORIZED, ResultMessage.PERMISSION_DENIED);
        }
    }

    @PostMapping("/logout")
    @OperationLogAnnotation(description = "退出")
    public HttpResult<?> logout(@RequestHeader("Authorization") String token) {
        try {
            // 1、从请求头中获取token，如果请求头中不存在token，直接放行即可！由Spring Security的过滤器进行校验！
            // 2、对token进行解析，取出其中的userId
            token = token.replaceAll(Bearer, "");
            String decodeToken = decodedJWT(token).getToken();
            DecodedJWT decode = JWT.decode(decodeToken);
            String userId = decode.getClaim("userId").asString();
            redisService.del(LOGIN_USER + userId);
            return HttpResult.success(ResultMessage.LOGOUT_OK);
        } catch (Exception e) {
            e.printStackTrace();
            return HttpResult.failure(ResultMessage.LOGOUT_ERROR);
        }
    }

    private boolean isDevOrPrevEnv() {
        return env.getActiveProfiles().length > 0 && (env.getActiveProfiles()[0].equals("dev") || env.getActiveProfiles()[0].equals("prev"));
    }
}
