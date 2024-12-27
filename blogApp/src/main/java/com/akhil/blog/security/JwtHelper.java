package com.akhil.blog.security;

import io.jsonwebtoken.*;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtHelper {

    private static final String SECRET_KEY = "JwtTokenKey"; // Replace with a secure secret key
    private static final long TOKEN_VALIDITY = 5 * 60 * 60  ; // 5 hours in milliseconds

    /**
     * Generate a JWT token for a user.
     *
     * @param username The username to include in the token.
     * @return The generated JWT token.
     */
    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, username);
    }

    /**
     * Validate the token's claims and signature.
     *
     * @param token    The JWT token to validate.
     * @param username The username to match with the token's subject.
     * @return True if the token is valid, false otherwise.
     */
    public boolean validateToken(String token, UserDetails userDetails) {
        final String tokenUsername = getUsernameFromToken(token);
        return (tokenUsername.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }


    /**
     * Retrieve the username from the token.
     *
     * @param token The JWT token.
     * @return The username extracted from the token.
     */
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    /**
     * Check if the token is expired.
     *
     * @param token The JWT token.
     * @return True if the token is expired, false otherwise.
     */
    public boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    /**
     * Retrieve the expiration date from the token.
     *
     * @param token The JWT token.
     * @return The expiration date extracted from the token.
     */
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    /**
     * Retrieve a specific claim from the token.
     *
     * @param token          The JWT token.
     * @param claimsResolver A function to resolve the claim.
     * @param <T>            The type of the claim.
     * @return The resolved claim.
     */
    public <T> T getClaimFromToken(String token, java.util.function.Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Generate the token with claims and subject.
     */
    @SuppressWarnings("deprecation")
	private String doGenerateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_VALIDITY))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    /**
     * Parse the token and retrieve all claims.
     *
     * @param token The JWT token.
     * @return All claims contained in the token.
     */
    @SuppressWarnings("deprecation")
	private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }
}
