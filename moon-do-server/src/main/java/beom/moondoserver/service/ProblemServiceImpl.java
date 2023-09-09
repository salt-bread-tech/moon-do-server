package beom.moondoserver.service;

import beom.moondoserver.model.dto.request.CreateProblemRequest;
import beom.moondoserver.model.dto.request.GetProblemRequest;
import beom.moondoserver.model.dto.request.GetSolutionRequest;
import beom.moondoserver.model.dto.response.*;
import beom.moondoserver.model.entity.Problem;
import beom.moondoserver.model.entity.ProblemPaper;
import beom.moondoserver.model.entity.User;
import beom.moondoserver.repository.ProblemPaperRepo;
import beom.moondoserver.repository.ProblemRepo;
import beom.moondoserver.repository.UserRepo;
import beom.moondoserver.util.GPTManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.StringTokenizer;

@Service
@RequiredArgsConstructor
public class ProblemServiceImpl implements ProblemService {

    GPTManager gptManager = new GPTManager();
    private final ProblemRepo problemRepo;
    private final UserRepo userRepo;
    private final ProblemPaperRepo problemPaperRepo;

    @Override
    public boolean createProblem(CreateProblemRequest request) {
        String prompt = request.getField() + " 분야의 " + request.getDetailedField() + "에 대한 "
                + request.getCategory() + " 문제를 " + request.getDifficulty() + "의 난이도로 "
                + request.getCount() + "개 만큼 출제해줘.";

        ChatGPTResponse chatGPTResponse = gptManager.getProblem(prompt);
        parseProblem(chatGPTResponse);

        return insertProblem(chatGPTResponse);
    }

    public List<GetProblemResponse> getProblem(GetProblemRequest request) {
        return null;
    }

    public List<GetSolutionResponse> getSolution(GetSolutionRequest request) {
        return null;
    }

    private boolean insertProblem(ChatGPTResponse chatGPTResponse) {
        Optional<ProblemPaper> optionalProblemPaper = problemPaperRepo.findById(1);
        List<String> problemList = parseProblem(chatGPTResponse);

        if(optionalProblemPaper.isPresent()){
            ProblemPaper problemPaper = optionalProblemPaper.get();
            for (String problem:problemList) {
                problemRepo.save(Problem.builder()
                        .problemPaperId(problemPaper)
                        .question(problem)
                        .answer("abcd")
                        .explanation("ancd").build());
                System.out.println("성공");
            }
            return true;
        }
        return false;
    }

    private boolean insertProblemPaper(ChatGPTResponse chatGPTResponse){

        return true;
    }

    private List<String> parseProblem(ChatGPTResponse chatGPTResponse){
        String string = chatGPTResponse.getChoices().get(0).getMessage().getContent();
        StringTokenizer stringTokenizer = new StringTokenizer(string, "\n");
        List<String> problemResponseList = new ArrayList<>();

        while(stringTokenizer.hasMoreTokens()){
            if(stringTokenizer.countTokens() % 2 == 1){
                problemResponseList.add(stringTokenizer.nextToken());
            }
            else {
                stringTokenizer.nextToken();
            }
        }

        // 데이터가 잘 파싱되었는지 확인하는 부분, 작업 이후 삭제 예정
        for (String problem:problemResponseList) {
            System.out.println(problem);
        }

        return problemResponseList;
    }
}