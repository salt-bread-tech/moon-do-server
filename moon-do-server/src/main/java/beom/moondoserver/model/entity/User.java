package beom.moondoserver.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "user")
@Builder
public class User {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer userId;

    @Column(name = "password")
    String password;

    @Column(name = "nickname")
    String nickname;

    @Column(name = "introduction")
    String introduction;
}
