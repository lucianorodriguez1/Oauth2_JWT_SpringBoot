package com.myPacket.oauth2_jwt.utils;

import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils {
    //firmar los metodos. El token tiene el permiso firmado; eso hace que sea autentico.
    //por ejemplo los hackers que duplican los token y no tienen la firma no pueden acceder.
    @Value("${jwt.secret.key}")
    private String secretKey;
    @Value("${jwt.time.expiration}")
    private String timeExpiration;


    //Generar token de acceso.
    //le mandamos por parametro la persona que va a crear el token.
    public String generateAccessToken(String username){
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
    }

}
