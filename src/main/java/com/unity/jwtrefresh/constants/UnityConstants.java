package com.unity.jwtrefresh.constants;

public class UnityConstants {
    private UnityConstants() {
    }

    public static final String DATE_PATTERN = "MM-dd-yyyy hh:mm:ss";
    public static final String DATE_ONLY_PATTERN = "MM-dd-yyyy";
    public static final String TIME_ZONE = "Asia/Tokyo";

    public static final long FIVE_DAYS_EXPIRATION_TIME = 432_000_000; // 5 days expressed in milliseconds
    public static final long ACCESS_TOKEN_EXPIRATION_TIME = 18_000_00; // 03 Minutes expressed in milliseconds
    public static final long THREE_DAYS_EXPIRATION_TIME = 259_200_000; // 3 days expressed in milliseconds
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String JWT_TOKEN_HEADER = "Jwt-Token";
    public static final String TOKEN_CANNOT_BE_VERIFIED = "Token cannot be verified";
    public static final String WITH_ISSUER = "http://localhost:8090";
    public static final String WITH_AUDIENCE = "http://localhost:8090";
    public static final String AUTHORITIES = "authorities";
    public static final String FORBIDDEN_MESSAGE = "You need to log in to access this page";
    public static final String ACCESS_DENIED_MESSAGE = "You do not have permission to access this page";
    public static final String NOT_ENOUGH_PERMISSION = "You do not have enough permission";


    public static final String OPTIONS_HTTP_METHOD = "OPTIONS";
    public static final String[] PUBLIC_URLS = {"/logout","/oauth2/success","/v1/auth/login", "/v1/auth/register", "/users/image/**", "/graphiql/**", "/graphql/**","/swagger-ui/**", "/v3/api-docs/**", "/swagger-resources/**", "/v3/api-docs/swagger-config", "/webjars/**"};
    //public static final String[] PUBLIC_URLS = {"**"};



}
