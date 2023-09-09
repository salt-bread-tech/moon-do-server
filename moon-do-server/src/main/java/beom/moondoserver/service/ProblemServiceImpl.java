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

import java.util.*;

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
                + request.getCount() + "개 만큼 출제해줘." + " 문제 번호와 문제 사이에는 '\n'이 없어야 하고,"
                + " 문제의 답은 각 문제의 뒤에 '\n'을 넣어서 함께 출력하며, 각 문제의 끝마다 ';'를 넣어 문제가 끝났음을 알려줘.";

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
        String problemString = null;
        String answerString = null;
        Optional<ProblemPaper> optionalProblemPaper = problemPaperRepo.findById(1);
        List<String> problemList = parseProblem(chatGPTResponse);
        HashMap<String, String> problemMap = new HashMap<>();

        if(optionalProblemPaper.isPresent()){
            ProblemPaper problemPaper = optionalProblemPaper.get();
            for (int i = 0; i < problemList.size(); i++) {
                if(i % 2 == 0){
                    problemString = problemList.get(i);
                }
                else {
                    answerString = problemList.get(i);
                }
                problemMap.put(problemString, answerString);
            }
            for(Map.Entry<String, String> elem : problemMap.entrySet()){
                problemRepo.save(Problem.builder()
                        .problemPaperId(problemPaper)
                        .question(elem.getKey())
                        .answer(elem.getValue())
                        .explanation("ancd").build());
            }
            System.out.println("DB 저장이 성공적으로 완료되었음.");
            return true;
        }
        return false;
    }

    private boolean insertProblemPaper(ChatGPTResponse chatGPTResponse){

        return true;
    }

    private List<String> parseProblem(ChatGPTResponse chatGPTResponse){
        String string = chatGPTResponse.getChoices().get(0).getMessage().getContent();
        StringTokenizer stringTokenizer = new StringTokenizer(string, ";");
        List<String> problemResponseList = new ArrayList<>();

        while(stringTokenizer.hasMoreTokens()){
            StringTokenizer answerTokenizer = new StringTokenizer(stringTokenizer.nextToken(), "\n");
            while(answerTokenizer.hasMoreTokens()){
                String problemString = answerTokenizer.nextToken().replace("\n", "");
                problemResponseList.add(problemString);
            }
        }

        return problemResponseList;
    }
}