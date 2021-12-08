package com.vmware.kafka.demo.config;
/****
 * Author : Anitha Rajamuthu
 * Date: 09-0ct-2021
 * Description : Swagger configuration
 * Local URL : http://localhost:8080/swagger-ui.html
 */
import java.util.Collections;

import org.springframework.context.annotation.Bean;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

public class SwaggerConfig {
	@Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.vmware.kafka.demo"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(getApiInformation());
    }
	
	
	 private ApiInfo getApiInformation(){
	        return new ApiInfo("Kafka Producer REST API",
	                "This API is kafka producer consumer poc",
	                "1.0",
	                "VMWare Integrations",
	                new Contact("VUD Confluence", "https://confluence.eng.vmware.com/pages/viewpage.action?pageId=1067094936", ""),
	                "",
	                "",
	                Collections.emptyList()
	                );
	    }

}
