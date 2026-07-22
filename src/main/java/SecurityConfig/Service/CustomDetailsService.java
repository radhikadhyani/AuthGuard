package SecurityConfig.Service;

import SecurityConfig.UserRepository.repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import SecurityConfig.User.UserEntity;

@Service

public class CustomDetailsService implements
        org.springframework.security.core.userdetails.UserDetailsService {
@Autowired
private repo repo;
    @Override
    public UserDetails loadUserByUsername(String username) {
        UserEntity user = repo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException
                        ("No user found with username: " + username));
return loadUserByUsername(username);
        /*.password(user.getPassword())
        .accountExpired(!user.isAccountNonExpired())
        .accountLocked(!user.isAccountNonLocked())
        .credentialsExpired(!user.isCredentialsNonExpired())
        .disabled(!user.isEnabled())
        .build();*/
    }
}
