package beom.moondoserver.model.entity;

import beom.moondoserver.util.Difficulty;
import beom.moondoserver.util.DifficultyConverter;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

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
    @Convert(converter = DifficultyConverter.class)
    Difficulty difficulty;

    @Column(name = "bookmarked", columnDefinition = "boolean default false")
    Boolean bookmarked;

    @Column(name = "date")
    @JsonFormat(timezone = "Asia/Seoul")
    LocalDateTime date;
}
