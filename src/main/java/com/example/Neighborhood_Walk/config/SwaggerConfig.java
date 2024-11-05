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
    // Bean related to Swagger2 configuration
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                // Manage all APIs under the 'com' package with Swagger2
                .apis(RequestHandlerSelectors.basePackage("com"))
                .paths(PathSelectors.any())
                .build();
    }

    // Information displayed on the Swagger API documentation page
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("ProjectAPI")  // Title for the API documentation
                .description("CS1-1 Project")  // Description of the project for the documentation page
                .version("1.0")  // Version number for the API documentation
                .build();
    }


}


