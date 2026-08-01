package SecurityConfig.Tokens;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDto {
    private String Accesstoken;
    private String Refreshtoken;
    private String type = "Bearer";
    private String username;
    private String roles;
}
