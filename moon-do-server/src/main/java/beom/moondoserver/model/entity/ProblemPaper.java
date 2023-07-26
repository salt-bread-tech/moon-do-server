package beom.moondoserver.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "problem_paper")
public class ProblemPaper {
    @Id
    @Column(nullable = false, name = "problem_paper_id")
    Integer problemPaperId;

    @ManyToOne
    @JoinColumn(name = "problem_id")
    Problem problem;

    @Column(name = "question")
    String question;

    @Column(name = "answer")
    String answer;

    @Column(name = "explanation")
    String explanation;
}
