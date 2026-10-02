package SecurityConfig.AuthFilter;

import SecurityConfig.Service.CustomUserDetailsService;
import SecurityConfig.jwtUtils.jwtUtils;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class AuthFilter extends OncePerRequestFilter {
    @Autowired
    private jwtUtils jwtUtils;
@Autowired
private CustomUserDetailsService customDetailsService;
    private static final Logger logger=  LoggerFactory.getLogger(AuthFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    logger.debug("doFilterInternal");
   try {
       String refreshToken = parseJwt(httpServletRequest);
       if (refreshToken != null && jwtUtils.validaterefreshToken(refreshToken)) {
           String username = jwtUtils.getUsernameFromrefreshToken(refreshToken);
           UserDetails userDetails = customDetailsService.loadUserByUsername(username);
           UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                   userDetails, null, userDetails.getAuthorities());
           authentication.setDetails(new WebAuthenticationDetailsSource()
                   .buildDetails(httpServletRequest));
           SecurityContextHolder.getContext().
                   setAuthentication(authentication);
       }
   }
       catch(JwtException e){
       response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

       }
   filterChain.doFilter(httpServletRequest, response);
   }


private String parseJwt(HttpServletRequest httpServletRequest) {
        String jwt = jwtUtils.getJwtFromHeader(httpServletRequest);
        logger.debug("AuthTokenFilter.java{}",jwt);
        return jwt;
}
}
