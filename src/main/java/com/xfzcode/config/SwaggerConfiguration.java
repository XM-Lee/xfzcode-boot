package com.xfzcode.config;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration // 配置类
@EnableSwagger2 // 开启 swagger2 的自动配置
@EnableSwaggerBootstrapUI
public class SwaggerConfiguration {

    @Bean
    public Docket docket() {
        // 创建一个 swagger 的 bean 实例
        return new Docket(DocumentationType.SWAGGER_2)
                // 配置基本信息
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.regex("(?!/error.*).*"))
                .build();
    }

    // 基本信息设置
    private ApiInfo apiInfo() {
        Contact contact = new Contact(
                // 作者姓名
                "XMLee",
                // 作者网址
                "http://www.xfzcode.com",
                // 作者邮箱
                "2864746340qq.com");
        return new ApiInfoBuilder()
                // 标题
                .title("小肥宅Boot-接口文档")
                // 描述
                .description("众里寻他千百度，慕然回首那人却在灯火阑珊处")
                // 跳转连接
                .termsOfServiceUrl("http://www.xfzcode.com")
                // 版本
                .version("1.0")
                .license("Swagger-的使用(详细教程)")
                .licenseUrl("http://www.xfzcode.com")
                .contact(contact)
                .build();
    }

}
