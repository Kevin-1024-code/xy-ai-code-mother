package com.xy.xyaicodemother;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

// 在主类排除数据源自动配置，测试类就能正常加载上下文
@SpringBootApplication
@EnableAspectJAutoProxy(exposeProxy = true)
@MapperScan("com.xy.xyaicodemother.mapper")
public class XyAiCodeMotherApplication {
    public static void main(String[] args) {
        SpringApplication.run(XyAiCodeMotherApplication.class, args);
    }
}