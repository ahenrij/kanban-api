package fr.istic.taa.jaxrs.utils;

import fr.istic.taa.jaxrs.dto.UserDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Objects;

public class JwtUtil {


    private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    /**
     * Tries to parse specified String as a JWT token. If successful, returns UserDto object with data.
     * If unsuccessful (token is invalid or not containing all required user properties), simply returns null.
     *
     * @param token the JWT token to parse
     * @return the UserDto object extracted from specified token or empty optinal if a token is invalid.
     */
    public static UserDto parseToken(String token) throws Exception {
        Objects.requireNonNull(token);
        Claims body = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody();

        UserDto u = new UserDto();
        u.setEmail(body.getSubject());
        u.setId(Long.parseLong((String) body.get("userId")));
        u.setFirstName((String) body.get("firstName"));
        u.setLastName((String) body.get("lastName"));

        return u;
    }

    /**
     * Generates a JWT token containing UserDto information as subject and claims. Tokens validity is 7 days.
     *
     * @param u the user's dto for which the token will be generated
     * @return the JWT token
     */
    public static String generateToken(UserDto u) {
        Claims claims = Jwts.claims().setSubject(u.getEmail());
        claims.put("userId", u.getId() + "");
        claims.put("firstName", u.getFirstName());
        claims.put("lastName", u.getLastName());

        return Jwts.builder()
                .setIssuedAt(new Date())
                .setExpiration(Date.from(LocalDateTime.now().plusDays(7).toInstant(ZoneOffset.UTC)))
                .setClaims(claims)
                .signWith(key)
                .compact();
    }
}