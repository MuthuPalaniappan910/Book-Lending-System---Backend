package com.booklending.customer.config;

import com.booklending.customer.utils.ApplicationConstants; 
import org.springframework.beans.factory.annotation.Value; 
import org.springframework.context.annotation.Bean; 
import springfox.documentation.builders.PathSelectors; 
import springfox.documentation.builders.RequestHandlerSelectors; 
import springfox.documentation.spi.DocumentationType; 
import springfox.documentation.spring.web.plugins.Docket; 
import springfox.documentation.swagger2.annotations.EnableSwagger2; 
 
@EnableSwagger2 
public class SwaggerConfig { 
 
    @Value("${prop.swagger.enabled:true}") 
    private boolean enableSwagger; 
 
    @Bean 
    public Docket api() { 
        return new Docket(DocumentationType.SWAGGER_2) 
                .select() 
                .apis(RequestHandlerSelectors.basePackage(ApplicationConstants.PACKAGE_PATH)) 
                .paths(PathSelectors.any()) 
                .build(); 
    } 
 
} 
