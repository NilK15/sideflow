package dev.nil.sideflow.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expirationMillis;

    public String generateToken(String email, List<String> roles) {
        return JWT.create()
                  .withSubject(email)
                  .withClaim("roles", roles)
                  .withIssuedAt(new Date())
                  .withExpiresAt(new Date(System.currentTimeMillis() + expirationMillis))
                  .sign(Algorithm.HMAC256(secret));

    }

    public boolean isTokenValid(String token) {
        try {
            getVerifier().verify(token);
            return true;
        } catch (JWTVerificationException e) {
            return false;
        }
    }

    public String getEmail(String token) {
        return decodeToken(token).getSubject();
    }

    public List<String> getRoles(String token) {
        return decodeToken(token).getClaim("roles")
                                 .asList(String.class);
    }

    private DecodedJWT decodeToken(String token) {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret))
                                  .build();
        return verifier.verify(token);
    }

    private JWTVerifier getVerifier() {
        return JWT.require(Algorithm.HMAC256(secret))
                  .build();
    }

}
