package beom.moondoserver.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetInfoResponse {
    int problemPaperId;
    int count;
    String title;
    String field;
    String detailedField;
    LocalDateTime date;
}
