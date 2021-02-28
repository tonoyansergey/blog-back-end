package am.egs.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {

    private static final String SECRET_KEY = RandomStringUtils.random(10, true, true);

    public String extractUserName (final String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private Date extractExpirationDate(final String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims (final String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    private <T> T extractClaim(final String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private boolean isTokenExpired (final String token) {
        return extractExpirationDate(token).before(new Date());
    }

    public String generateToken (final UserDetails userDetails) {
        return createToken(userDetails.getUsername());
    }

    private String createToken (final String subject) {
        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public boolean validateToken (final String token, final UserDetails userDetails) {
        return (extractUserName(token).equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
