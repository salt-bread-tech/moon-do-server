package beom.moondoserver.repository;

import beom.moondoserver.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Integer> {
    Optional<User> findByUserId(Integer userId);
    Optional<User> findByEmail(String email);
}
