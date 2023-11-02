package beom.moondoserver.model.dto.request;

import beom.moondoserver.model.entity.ProblemPaper;
import beom.moondoserver.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeleteRequest {
    private int userId;
    private int problemPaperId;
}
