package mirkoabozzi.U5S7L2.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import mirkoabozzi.U5S7L2.entities.Employee;
import mirkoabozzi.U5S7L2.exceptions.UnauthorizedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTTools {
    @Value("${jwt.secret}") //key segreta
    private String secret;

    public String generateToken(Employee employee) {
        return Jwts.builder()
                .issuedAt(new Date(System.currentTimeMillis())) //data rilascio
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) //data scadenza
                .subject(String.valueOf(employee.getId())) // id del proprietario del token
                .signWith(Keys.hmacShaKeyFor(secret.getBytes())) //firma il token utilizzando la key segreta present ein env.properties importata nella classe con il @Value
                .compact(); // genera il token
    }

    public void verifyToken(String token) {
        try {
            Jwts.parser().verifyWith(Keys.hmacShaKeyFor(secret.getBytes())).build().parse(token); // controllo integrità token se non lo passo lancio l'eccezione
        } catch (Exception ex) {
            throw new UnauthorizedException("Invalid token");
        }
    }

    public String extractIdFromToken(String id) {
        return Jwts.parser().verifyWith(Keys.hmacShaKeyFor(secret.getBytes())).build().parseSignedClaims(id).getPayload().getSubject(); //metodo che estrae l'id del token, getSubject è l'id presente dentro il token
    }
}
