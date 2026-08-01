package SecurityConfig.Mapper;

import SecurityConfig.Tokens.LoginDto;
import SecurityConfig.Tokens.LoginResponseDto;
import SecurityConfig.Tokens.RegisterDto;
import SecurityConfig.Tokens.RegisterResponseDto;
import SecurityConfig.User.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Component
@Mapper (componentModel = "spring")
public interface mapper {
@Mapping(target="id",ignore=true)
UserEntity mappedEntity(RegisterDto registerDto);


}
