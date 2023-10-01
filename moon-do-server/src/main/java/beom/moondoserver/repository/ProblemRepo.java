package beom.moondoserver.repository;

import beom.moondoserver.model.entity.Problem;
import beom.moondoserver.model.entity.ProblemPaper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProblemRepo extends JpaRepository<Problem, Integer> {
    List<Problem> findAllByProblemPaperId(ProblemPaper problemPaperId);
}
