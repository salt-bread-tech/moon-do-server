package beom.moondoserver.service;

import beom.moondoserver.model.dto.request.CreateProblemRequest;
import beom.moondoserver.model.dto.request.GetProblemRequest;
import beom.moondoserver.model.dto.request.GetSolutionRequest;
import beom.moondoserver.model.dto.response.ChatGPTResponse;
import beom.moondoserver.model.dto.response.CreateProblemResponse;
import beom.moondoserver.model.dto.response.GetProblemResponse;
import beom.moondoserver.model.dto.response.GetSolutionResponse;
import beom.moondoserver.util.GPTManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProblemServiceImpl implements ProblemService {

    GPTManager gptManager = new GPTManager();

    @Override
    public boolean createProblem(CreateProblemRequest request) {
        String prompt = request.getField() + " 분야의 " + request.getDetailedField() + "에 대한 "
                + request.getCategory() + " 문제를 " + request.getDifficulty() + "의 난이도로 "
                + request.getCount() + "개 만큼 출제해줘.";

        ChatGPTResponse chatGPTResponse = gptManager.getProblem(prompt);
        System.out.println(chatGPTResponse);

        return insertProblem(chatGPTResponse);
    }

    public List<GetProblemResponse> getProblem(GetProblemRequest request) {
        return null;
    }

    public List<GetSolutionResponse> getSolution(GetSolutionRequest request) {
        return null;
    }

    private boolean insertProblem(ChatGPTResponse chatGPTResponse) {
        
        // response 를 파싱해서 DB에 넣는 과정 필요
        
        return true;
    }
}
