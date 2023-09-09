package beom.moondoserver.repository;

import beom.moondoserver.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Integer> {
    @Override
    Optional<User> findById(Integer userId);
}
