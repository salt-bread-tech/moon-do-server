package beom.moondoserver.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
    String userId;

    @Column(name = "password")
    String password;

    @Column(name = "nickname")
    String nickname;
}
