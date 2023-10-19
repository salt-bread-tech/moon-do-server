package beom.moondoserver.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookmarkedResponse {
    int problemPaperId;
    int count;
    String title;
    String field;
    String detailedField;
}
