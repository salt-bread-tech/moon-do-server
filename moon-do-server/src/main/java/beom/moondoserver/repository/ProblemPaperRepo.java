package beom.moondoserver.repository;

import beom.moondoserver.model.entity.ProblemPaper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProblemPaperRepo extends JpaRepository<ProblemPaper, Integer> {
    List<ProblemPaper> findAllByUser_UserId(Integer userId);
    List<ProblemPaper> findAllByUser_UserIdAndBookmarkedIsTrue(Integer userId);
}
