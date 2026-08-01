package SecurityConfig.Service;
import SecurityConfig.Mapper.mapper;
import SecurityConfig.Tokens.RegisterDto;
import SecurityConfig.Tokens.RegisterResponseDto;
import SecurityConfig.User.Role;
import SecurityConfig.User.UserEntity;
import SecurityConfig.UserRepository.repo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
private repo repo;
    private PasswordEncoder encoder;
  private final AuthenticationManager authenticationManager;
  private final mapper mapper;
    public RegisterResponseDto RegisterUser(RegisterDto registerDto) {
        if(repo.findByUsername(registerDto.getUsername()).isPresent()){
            throw new RuntimeException("User Already Exists");
        }
        if(repo.findByEmail(registerDto.getEmail()).isPresent()){
            throw new RuntimeException("User Already Exists");
        }
          UserEntity userEntity = mapper.mappedEntity(registerDto);
        userEntity.setPassword(encoder.encode(registerDto.getPassword()));
        userEntity.setRole(Role.ROLE_USER);
        UserEntity savedEntity = repo.save(userEntity);
        return new RegisterResponseDto(registerDto.getUsername());
    }



}