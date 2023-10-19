package beom.moondoserver.model.dto.request;

import beom.moondoserver.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetInfoRequest {
    private Integer userId;
}
