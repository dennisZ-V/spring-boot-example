package com.example.springbootdemo.demos.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhangdi
 * @date 2024/9/5 16:41
 */
@Configuration
public class SpringdocConfig {

    @Bean
    public OpenAPI openApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("工单API")
                        .description("工单相关接口DEMO")
                        .version("v1.0.0"));
    }
}
