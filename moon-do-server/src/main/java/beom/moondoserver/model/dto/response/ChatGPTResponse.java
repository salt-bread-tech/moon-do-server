package beom.moondoserver.model.dto.response;

import beom.moondoserver.model.Choice;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatGPTResponse {
    private String id;
    private String object;
    private LocalDate created;
    private String model;
    private List<Choice> choices;
}