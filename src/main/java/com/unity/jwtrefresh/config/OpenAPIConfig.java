package com.unity.jwtrefresh.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.*;
import io.swagger.v3.oas.models.security.SecurityScheme.In;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration
public class OpenAPIConfig {

    @Value("${contact.email}")
    private String contactEmail;

    @Value("${contact.url}")
    private String contactUrl;

    @Value("${server.port}")
    private String serverPort;


    @Bean
    public OpenAPI subscriptionAPI() {
        return new OpenAPI().info(new Info().title("Authentication  API").description("API group to manage users, roles and permissions")
                        .contact(new Contact().email(contactEmail).url(contactUrl)))
                .addServersItem(new Server().url("http://localhost:" + serverPort).description("LOCAL Server"))
                .components(new Components()
                                .addSecuritySchemes("bearerAuth", bearerSecurity())
//                        .addSecuritySchemes("Github-OAuth2", oAuthSecurity())
                                .addSecuritySchemes("LinkedIn-OAuth2", oAuthSecurityLinkedIn())
                )

                .security(Collections.singletonList(new SecurityRequirement()
                                .addList("bearerAuth")
                                .addList("Github-OAuth2")
                                .addList("Linkedin-OAuth2")
                        )

                )
                ;
    }

    private SecurityScheme bearerSecurity() {
        return new SecurityScheme().name("bearerAuth").type(Type.HTTP).in(In.HEADER).bearerFormat("JWT").scheme("bearer");

    }

    private SecurityScheme oAuthSecurity() {
        return new SecurityScheme().name("Github-OAuth2")
                .type(Type.OAUTH2)
                .flows(
                        new OAuthFlows()
                                .authorizationCode(new OAuthFlow()
                                        .authorizationUrl("https://github.com/login/oauth/authorize")
                                        .scopes(new Scopes()
                                                .addString("read", "for read operations")
                                                .addString("write", "for write operations")
                                        )));

    }

    private SecurityScheme oAuthSecurityLinkedIn() {
        return new SecurityScheme().name("LinkedIn-OAuth2")
                .type(Type.OAUTH2)
                .flows(
                        new OAuthFlows()
                                .authorizationCode(new OAuthFlow()
                                        .authorizationUrl("https://www.linkedin.com/oauth/v2/authorization")
                                        .scopes(new Scopes()
                                                .addString("openid", "for openid operations")
                                                .addString("profile", "for profile operations")
                                        )));

    }

}