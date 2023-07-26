package beom.moondoserver.repository;

import beom.moondoserver.model.entity.Problem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProblemRepo extends JpaRepository<Problem, Integer> {

}
