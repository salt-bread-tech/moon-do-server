package beom.moondoserver.model.dto.response;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {

    String code;
    Integer userId;

}
