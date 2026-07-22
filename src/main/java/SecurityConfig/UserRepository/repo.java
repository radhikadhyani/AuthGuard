package SecurityConfig.UserRepository;

import SecurityConfig.User.UserEntity;
import org.apache.catalina.User;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.lang.ScopedValue;
import java.util.Optional;
@Repository
public interface repo extends JpaRepository<User, Long> {
    Optional<UserEntity> findByUsername(String username);


}
