package beom.moondoserver.service;

import beom.moondoserver.model.dto.request.CreateProblemRequest;
import beom.moondoserver.model.dto.response.ChatGPTResponse;
import beom.moondoserver.model.dto.response.CreateProblemResponse;
import beom.moondoserver.util.GPTManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProblemServiceImpl implements ProblemService {

    GPTManager gptManager = new GPTManager();

    @Override
    public List<CreateProblemResponse> createProblem(CreateProblemRequest request) {
        String prompt = request.getField() + "분야의 " + request.getDetailedField() + "에 대한 "
                + request.getCategory() + "문제를 " + request.getDifficulty() + "의 난이도로 "
                + request.getCount() + "개 만큼 출제해줘.";

        ChatGPTResponse chatGPTResponse = gptManager.getProblem(prompt);

        System.out.println(chatGPTResponse);

        return null;
    }
}
