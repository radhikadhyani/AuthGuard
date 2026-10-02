package SecurityConfig.jwtUtils;


import SecurityConfig.Service.CustomUserDetails;
import SecurityConfig.Service.CustomUserDetailsService;
import SecurityConfig.User.UserEntity;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class jwtUtils {
    private static final Logger logger=  LoggerFactory.getLogger(jwtUtils.class);
    @Value("${secret.key}")
    private String jwtSecret;
    private SecretKey signingKey(){
        return Keys.
                hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
}
public String createAccessToken(UserDetails user) {
       List< String> roles = user.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
return Jwts.builder()
        .subject(user.getUsername())
        .issuedAt(new Date())
        .expiration(new Date(System.currentTimeMillis()+1000*60))
        .signWith(signingKey())
        .compact();

    }
    public String createRefreshToken(UserDetails user) {
        return Jwts.builder()
                .subject(user.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+1000L *60*60*24*30*6))
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
    public String getUsernameFromrefreshToken(String refreshToken) {
        return Jwts.parser()
                .verifyWith(signingKey())
                .build()
                .parseSignedClaims(refreshToken)
                .getPayload()
                .getSubject();
    }
    public boolean validaterefreshToken(String refreshToken) {
        try{
            System.out.println("validateJwtToken");
            Jwts.parser().verifyWith(signingKey()).build().parseSignedClaims((refreshToken));
            return true;
        }
        catch (ExpiredJwtException e ){
            logger.error("ExpiredJwtException");
        }
        return false;
    }


}
