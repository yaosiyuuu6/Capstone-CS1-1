package com.example.Neighborhood_Walk.config;

import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
//        swagger2相关bean
    public Docket createRestApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com")) //com包下所有api由swagger2管理
                .paths(PathSelectors.any()).build();
    }
//    api文档页面显示信息
    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("ProjectAPI")
                .description("CS1-1 Project")
                .version("1.0")
                .build();
    }

}


