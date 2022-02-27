package br.com.ialmeida.jwtspringboot.security.jwt.utils;

import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

    public static int TOKEN_EXPIRATION = 600000;
    public static String TOKEN_PASSWORD = "6ebde667-ee59-44a6-a928-81d71bb3b4a8";

}
