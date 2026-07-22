package SecurityConfig.Service;

import SecurityConfig.jwtUtils.jwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;

public class AuthService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private jwtUtils jwtUtils;

}
