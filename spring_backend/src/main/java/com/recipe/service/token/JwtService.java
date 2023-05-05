package com.recipe.service.token;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    // secret key is 512 bits long
    private static final String SECRET_KEY = "38792F423F4428472B4B6250655368566D597133743677397A244326462948404D635166546A576E5A7234753778214125442A472D4B614E645267556B58703273357638792F423F4528482B4D6251655368566D597133743677397A24432646294A404E635266556A576E5A7234753778214125442A472D4B6150645367566B59703273357638792F423F4528482B4D6251655468576D5A7134743677397A24432646294A404E635266556A586E327235753878214125442A472D4B6150645367566B597033733676397924423F4528482B4D6251655468576D5A7134743777217A25432A46294A404E635266556A586E3272357538782F413F4428472B4B61";
    private long jwtExpiration;
    private long refreshExpiration;

    // Extracts username from jwt token
    public String extractUsername(String jwtToken) {
        return extractClaim(jwtToken, Claims::getSubject);
    }

    // Extracts all claims from jwt token
    public <T> T extractClaim(String jwtToken, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(jwtToken);
        return claimsResolver.apply(claims);
    }

    // Generate a new token based on the user details
    public String generateJwtToken(UserDetails userDetails) {
        return generateJwtToken(new HashMap<>(), userDetails);
    }

    // Generate a new token based on the user details and extra claims
    public String generateJwtToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails) {
        return buildJwtToken(extraClaims, userDetails, jwtExpiration);
    }

    // Generate a new refresh token
    public String generateRefreshToken(UserDetails userDetails) {
        return buildJwtToken(new HashMap<>(), userDetails, refreshExpiration);
    }

    // Helper method to build a jwt token
    public String buildJwtToken(
            Map<String, Object> otherClaims,
            UserDetails userDetails,
            long expiration) {
        return Jwts
                .builder()
                .setClaims(otherClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1800000))
                .signWith(getSignInKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    // Validate jwt token
    public boolean isJwtTokenValid(String jwtToken, UserDetails userDetails) {
        final String username = extractUsername(jwtToken);
        return (username.equals(userDetails.getUsername())) && !isJwtTokenExpired(jwtToken);
    }

    // Check if jwt token is expired
    private boolean isJwtTokenExpired(String jwtToken) {
        return extractExpiration(jwtToken).before(new Date());
    }

    // Extracts expiration date from jwt token
    private Date extractExpiration(String jwtToken) {
        return extractClaim(jwtToken, Claims::getExpiration);
    }

    // Extracts all claims from jwt token
    private Claims extractAllClaims(String jwtToken) {
        return Jwts
                .parserBuilder()
                // Creates signature of the jwt
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(jwtToken)
                .getBody();
    }

    // Creates a sign in key from the secret key
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
