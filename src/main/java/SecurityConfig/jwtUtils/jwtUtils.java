package SecurityConfig.jwtUtils;


import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;

@Component
@AllArgsConstructor
public class jwtUtils {
    private static final Logger logger=  LoggerFactory.getLogger(jwtUtils.class);
    @Value("${secret.key}")
    private String jwtSecret;
    private SecretKey signingKey(){
        return Keys.
                hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
}
 String generateJwt(UserDetails userDetails) {
       List< String> roles = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
return Jwts.builder()
        .subject(userDetails.getUsername())
        .issuedAt(new Date())
        .expiration(new Date(System.currentTimeMillis()+1000*60))
        .signWith(signingKey())
        .compact();

    }
    public String getJwtFromHeader(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        logger.debug("Bearer Token: {}", bearerToken);
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);

        }
        return null;
    }
    public String getUsernameFromJwt(String token) {
        return Jwts.parser()
                .verifyWith(signingKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }
    public boolean validateJwt(String token) {
        try{
            System.out.println("validateJwtToken");
            Jwts.parser().verifyWith(signingKey()).build().parseSignedClaims((token));
            return true;
        }
        catch (ExpiredJwtException e ){
            logger.error("ExpiredJwtException");
        }
        return false;
    }


}
