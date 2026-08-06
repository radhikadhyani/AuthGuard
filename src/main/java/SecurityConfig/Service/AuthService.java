package SecurityConfig.Service;
import SecurityConfig.Mapper.mapper;
import SecurityConfig.Tokens.LoginDto;
import SecurityConfig.Tokens.LoginResponseDto;
import SecurityConfig.Tokens.RegisterDto;
import SecurityConfig.Tokens.RegisterResponseDto;
import SecurityConfig.User.Role;
import SecurityConfig.User.UserEntity;
import SecurityConfig.UserRepository.repo;
import SecurityConfig.jwtUtils.jwtUtils;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Data
public class AuthService {
private repo repo;
    private PasswordEncoder encoder;
  private final AuthenticationManager authenticationManager;
  private final mapper mapper;
private final jwtUtils jwtUtils;
private UsernameGenerator usernamGenerator;
private final CustomUserDetailsService customUserDetailsService;
    public RegisterResponseDto RegisterUser(RegisterDto registerDto) throws RuntimeException {
        if(repo.findByUsername(registerDto.getUsername()).isPresent()){
            throw new RuntimeException("User Already Exists");
        }
        if(repo.findByEmail(registerDto.getEmail()).isPresent()){
            throw new RuntimeException("User Already Exists");
        }
          UserEntity userEntity = mapper.mappedEntity(registerDto);
        userEntity.setPassword(encoder.encode(registerDto.getPassword()));
        userEntity.setRole(Role.ROLE_USER);
        userEntity.setUsername(usernamGenerator.UsernameBuilder(registerDto.getFirstName(), registerDto.getMiddleName(), registerDto.getLastName()));

        UserEntity savedEntity = repo.save(userEntity);
        return new RegisterResponseDto(registerDto.getUsername());
    }
    public LoginResponseDto LoginUser(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getUsername(), loginDto.getPassword())
        );
        CustomUserDetails user =
                (CustomUserDetails) authentication.getPrincipal();
        String accessToken = jwtUtils.createAccessToken(user);
        String refreshToken = jwtUtils.createRefreshToken(user);
        LoginResponseDto loginResponseDto = new LoginResponseDto();
        loginResponseDto.setUsername(user.getUsername());
        loginResponseDto.setAccesstoken(accessToken);
        loginResponseDto.setRefreshtoken(refreshToken);
        loginResponseDto.setType("Bearer");
        loginResponseDto.setRole(user.getRole().name());
        return loginResponseDto;
    }
 public LoginResponseDto refreshToken(String refreshToken) {
        jwtUtils.validateRefreshToken(refreshToken);
String username=jwtUtils.getUsernameFromToken(refreshToken);
String NewaccessToken= jwtUtils.createAccessToken(customUserDetailsService.loadUserByUsername(username));
LoginResponseDto loginResponseDto = new LoginResponseDto();
loginResponseDto.setAccesstoken(NewaccessToken);
return loginResponseDto;
 }

}