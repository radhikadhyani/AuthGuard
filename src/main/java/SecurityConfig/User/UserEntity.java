package SecurityConfig.User;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table (name="CheckUsers")
public class UserEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(unique=true, nullable=false)
    private Long id;
    @ Column
    private String firstName;
    @ Column
    private String middleName;
    @Column
    private String lastName;
    @Column
    public String getFullName() {
        return Stream.of(firstName, middleName, lastName)
                .filter(Objects::nonNull)
                .filter(s -> !s.isBlank())
                .collect(Collectors.joining(" "));
    }
    @Column(unique=true, nullable=false)
    private String username;
    @Column( nullable=false)
    private String password;
    @Column(unique=true, nullable=false)
    private String email;

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }
@Column
    private boolean enabled=true;
    @Column
    private boolean accountNonExpired=true;
    @Column
    private boolean credentialsNonExpired=true;
    @Column
    private boolean accountNonLocked=true;

}
