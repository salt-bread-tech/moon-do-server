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
    Integer problemId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

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
