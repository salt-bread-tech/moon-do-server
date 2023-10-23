package beom.moondoserver.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateProblemRequest {
    private Integer userId;
    private String title;
    private String field;
    private String detailedField;
    private String category;
    private Integer count;
    private Integer difficulty;
}
