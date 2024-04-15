package com.example.pfe.Employé.Service;


import com.example.pfe.Employé.Entity.Employee;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

// JWT token handling
// Token generation
// Token validation
// Extraction of claims
//  Managing token status
@Service
public class JwtService {



    private final  String SecretKey = "413F4428472B4B6250655368566D5970337336763979244226452948404D6351" ;

    private final long accessTokenValidityMilliseconds = 60* 60 * 1000; // Access token validity period (1 hour)
    private final long refreshTokenValidityMilliseconds = 24 * 60 * 60 * 1000; // Refresh token validity period (24 hours)


    // Extracting username from the JWT token's claims
    public String extractUsername(String token) {

        return extractClaim(token, Claims::getSubject);
    }

    // Extracting user role from the JWT token's claims with null handling
    public String extractUserRole(String token) {
        return extractClaim(token, claims -> claims.get("role", String.class));
    }

    // Checking token validity ; token not expired , not loggedout , user credentials are verfied

    // Checking token validity with optional role handling
    public Boolean isValid(String token, UserDetails employee) {
        String username = extractUsername(token);
       Optional<String> userRoleOptional = Optional.ofNullable(extractUserRole(token));


        // Retrieve the user's role from the UserDetails object
        String employeeRole = employee.getAuthorities().stream()
                .findFirst()
                .map(GrantedAuthority::getAuthority)
                .orElse("");

        // Use Optional methods for null-safe comparisons:
        return username.equals(employee.getUsername())
                && userRoleOptional.map(ur -> ur.equals(employeeRole)).orElse(false)
                && !isTokenExpired(token);

    }

// checking refresh token validity (not null + do not contain any whitespace characters)
    public Boolean isValidRefreshToken(String refreshToken) {
        return refreshToken != null && !refreshToken.trim().isEmpty();
    }



    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }


    private Date extractExpiration(String token) {

        return extractClaim(token,Claims::getExpiration);
    }



    //This method extracts various claims from the JWT token, such as subject (username) and expiration date
    private <T> T extractClaim(String Token , Function<Claims , T> claimsResolvers) {
        final Claims claims = extractallClaims(Token);
        return claimsResolvers.apply(claims);


    }

    private Claims extractallClaims(String token){
        return Jwts
                .parser()
                .verifyWith(getSigninKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // Generate both access and refresh tokens for the user
    public Map<String, String> generateTokens(Employee employee) {
        Map<String, String> tokens = new HashMap<>();

        // Generate access token
        String accessToken = generateToken(employee, accessTokenValidityMilliseconds);
        tokens.put("accessToken", accessToken);

        // Generate refresh token
        String refreshToken = generateToken(employee, refreshTokenValidityMilliseconds);
        tokens.put("refreshToken", refreshToken);

        return tokens;
    }


    public String generateToken(Employee employee, long validityMilliseconds) {

        Claims claims = Jwts.claims().build(); // Create claims object directly
        String token = Jwts.builder()
                .setClaims(claims) // Use the created claims object
                .claim("role", employee.getRole())
                .subject(employee.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+validityMilliseconds)) // the token is validate for 24hours
                .signWith(getSigninKey())
                .compact();

        return token;
    }


    private SecretKey getSigninKey(){
        byte[] KeyBytes = Decoders.BASE64.decode(SecretKey);
        return Keys.hmacShaKeyFor(KeyBytes);

    }
}



