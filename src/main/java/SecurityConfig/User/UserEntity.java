package SecurityConfig.User;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.Collection;
import java.util.List;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table (name="CheckUsers")
public class UserEntity implements UserDetails {
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
    @Column(unique=true, nullable=false)
    private String username;
    @Column( nullable=false)
    private String password;
    @Column(unique=true, nullable=false)
    private String email;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
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
