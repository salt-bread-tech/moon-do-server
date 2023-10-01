package beom.moondoserver.repository;

import beom.moondoserver.model.entity.ProblemPaper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProblemPaperRepo extends JpaRepository<ProblemPaper, Integer> {
}
