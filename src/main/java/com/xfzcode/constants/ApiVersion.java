package com.xfzcode.constants;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.http.HttpMethod;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: XMLee
 * @Date: 2023/6/27 15:52
 * @Description:
 */
public class ApiVersion {
    public static final String V1 = "/api/v1";

    public static final String V1_AUTH = V1 + "/auth";
    public static final String V1_USER = V1 + "/users";
    public static final String V1_OPERATION_LOG = V1 + "/operationLog";
    public static final String V1_ROLE = "/role";
    public static final String V1_PERMISSION = "/permission";


    // 对于白名单中的URL，不检查JWT和鉴权
    public static final List<Pair<HttpMethod, String>> ANT_WHITE_LIST = new ArrayList<>();
    public static final List<Pair<HttpMethod, String>> REG_WHITE_LIST = new ArrayList<>();

    static {

        // TODO 默认全部放开-线上取消
        ANT_WHITE_LIST.add(Pair.of(HttpMethod.GET,  V1 +"/**"));
        ANT_WHITE_LIST.add(Pair.of(HttpMethod.POST,  V1 +"/**"));
        ANT_WHITE_LIST.add(Pair.of(HttpMethod.DELETE,  V1 +"/**"));
        ANT_WHITE_LIST.add(Pair.of(HttpMethod.PUT,  V1 +"/**"));

        ANT_WHITE_LIST.add(Pair.of(HttpMethod.GET, V1_AUTH + "/getVerCode"));
        ANT_WHITE_LIST.add(Pair.of(HttpMethod.GET, V1_AUTH + "/register"));

        ANT_WHITE_LIST.add(Pair.of(HttpMethod.POST, V1_AUTH + "/login"));
        ANT_WHITE_LIST.add(Pair.of(HttpMethod.POST, V1_AUTH + "/loginPhone"));
        ANT_WHITE_LIST.add(Pair.of(HttpMethod.POST, V1_AUTH + "/token/refresh"));

        ANT_WHITE_LIST.add(Pair.of(HttpMethod.POST, V1_OPERATION_LOG + "/*"));



        // CORS，放通所有 options
        ANT_WHITE_LIST.add(Pair.of(HttpMethod.OPTIONS, "/**"));
        // Swagger 全部放通
        REG_WHITE_LIST.add(Pair.of(HttpMethod.GET, "/swagger-ui.html"));
        REG_WHITE_LIST.add(Pair.of(HttpMethod.GET, "/webjars/**"));
        REG_WHITE_LIST.add(Pair.of(HttpMethod.GET, "/swagger-resources/**"));
        REG_WHITE_LIST.add(Pair.of(HttpMethod.GET, "/v2/**"));

    }
}
