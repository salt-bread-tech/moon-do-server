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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer problemPaperId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    @Column(name = "title")
    String title;

    @Column(name = "field")
    String field;

    @Column(name = "detailed_field")
    String detailedField;

    @Column(name = "category")
    String category;

    @Column(name = "count")
    Integer count;

    @Column(name = "difficulty")
    Integer difficulty;

    @Column(name = "bookmark", columnDefinition = "boolean default false")
    Boolean bookmark;
}
