package com.jwt;

import io.jsonwebtoken.Jwts;
import jakarta.xml.bind.DatatypeConverter;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import javax.crypto.SecretKey;

@SpringBootTest
public class JwtSecretMakerTest {

    @Test
    public void generateSecretKey(){
//        SecretKey key = Jwts.SIG.HS512.key().build(); // creates a secret key of length 512 using HS512 algo.
//        String secretKey = DatatypeConverter.printHexBinary(key.getEncoded());
//        System.out.println(secretKey);

    }
}
