package com.xy.xyaicodemother.config;

// 注意：以下是OpenAPI 3.x的正确导入包（适配Spring Boot 3.x + Knife4j 4.x）
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Knife4j（OpenAPI 3）配置类
 * 注意：Spring Boot 3.x 不再使用 Swagger 2 的 io.swagger.annotations 包！
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        // 配置接口文档的基本信息
        return new OpenAPI()
                .info(new Info()
                        .title("XY-AI-Code-Mother 接口文档")
                        .version("1.0.0")
                        .description("AI代码生成工具的接口文档（基于Knife4j + OpenAPI 3）"));
    }
}