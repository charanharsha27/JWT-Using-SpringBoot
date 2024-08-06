package com.jwt.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.List;

@Configuration
public class JwtAuthFilter extends OncePerRequestFilter {
    private static final String SECRET_KEY = "02CB6FDD27DA9C0C3312B9F117D715A1DF575825CDB136C6C08A49290C58A82A07C1028843800558CDD5EF3362BC7ADFEFB662E75118E6DE175575DC3F5A2EA1";

    @Override // the below method will be executed for every request that is hit
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        if(authHeader!=null){
            authHeader = authHeader.substring(7); // Bearer-token

            try{
                SecretKey secretKey = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
                Claims claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(authHeader).getBody(); // get the payload from the body

                String username = String.valueOf(claims.get("username"));
                String authorities = String.valueOf(claims.get("authorities"));

                List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);
                Authentication auth = new UsernamePasswordAuthenticationToken(username, null, grantedAuthorities);
                SecurityContextHolder.getContext().setAuthentication(auth); // set the user as logged in
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
        filterChain.doFilter(request, response);
    }
}