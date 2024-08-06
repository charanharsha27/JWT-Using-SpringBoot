package com.jwt.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {
    private static final String SECRET_KEY = "02CB6FDD27DA9C0C3312B9F117D715A1DF575825CDB136C6C08A49290C58A82A07C1028843800558CDD5EF3362BC7ADFEFB662E75118E6DE175575DC3F5A2EA1";
    private static final SecretKey KEY = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    public String generateToken(UserDetails userDetails){

        return Jwts.builder()
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime()+1800000))
                .claim("username",userDetails.getUsername())
                .signWith(KEY)
                .compact();

    }

    // return a new jwt token setting issued date, expiration date,payload,secret-key,convert to json mode.
}