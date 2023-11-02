package beom.moondoserver.model.dto.request;

import beom.moondoserver.model.entity.ProblemPaper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SelectDeleteRequest {
    private int userId;
    private List<Integer> problemPaperIds;
}
