package beom.moondoserver.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateProblemRequest {
    private String field;
    private String detailed_field;
    private String category;
    private Integer count;
    private Integer difficulty;
}
