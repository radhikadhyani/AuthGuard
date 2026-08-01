package SecurityConfig.Tokens;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDto {
private String firstName;
private String middleName;
private String lastName;
    private String Username;
    private String Email;
    private String Password;


}
