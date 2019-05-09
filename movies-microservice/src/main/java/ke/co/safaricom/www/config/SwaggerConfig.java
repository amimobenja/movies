/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ke.co.safaricom.www.config;

import ke.co.safaricom.www.utils.Constants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import static springfox.documentation.builders.PathSelectors.regex;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 *
 * @author afro
 *
 *
 * A Springfox Docket instance provides the primary API configuration with
 * sensible defaults and convenience methods for configuration.
 *
 * In this configuration class, the @EnableSwagger2 annotation enables Swagger
 * support in the class. The select() method called on the Docket bean instance
 * returns an ApiSelectorBuilder, which provides the apis() and paths() methods
 * that are used to filter the controllers and methods that are being documented
 * using String predicates.
 *
 * In the code, the RequestHandlerSelectors.basePackage predicate matches the
 * ke.co.telkom.www.stkpushapi.controllers base package to filter the API. The
 * regex parameter passed to paths()acts as an additional filter to generate
 * documentation only for the path starting with /stk_push.
 *
 * At this point, you should be able to test the configuration by starting the
 * app and pointing your browser to http://localhost:8010/v2/api-docs.
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select().apis(RequestHandlerSelectors.any())
                .paths(regex("/apis.*"))
                .build().apiInfo(apiInfo());
    }
    
    private ApiInfo apiInfo() {
        
        return new ApiInfoBuilder()
            .title(Constants.API_DOC_TITLE)
            .description(Constants.API_DOC_DESCRIPTION)
            .license(Constants.API_DOC_LICENSE_TEXT)
            .version(Constants.API_DOC_SWAGGER_API_VERSION)
            .build();
    }
        

}
