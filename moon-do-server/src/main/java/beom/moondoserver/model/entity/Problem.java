package beom.moondoserver.model.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "problem")
public class Problem {
    @Id
    @Column(name = "problem_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer problemId;

    @ManyToOne
    @JoinColumn(name = "problem_paper_id")
    ProblemPaper problemPaperId;

    @Column(name = "question")
    String question;

    @Column(name = "answer")
    String answer;

    @Column(name = "explanation")
    String explanation;
}
