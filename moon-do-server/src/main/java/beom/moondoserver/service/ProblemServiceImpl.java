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
                + request.getCount() + "개 만큼 출제해줘."
                + " 문제의 답과 풀이는 각 문제의 뒤에 '\\n'을 넣어서 함께 출력해줘." + " 문제를 작성할 때 절대 문제내용과 문제 번호 사이에 '\\n'을 넣지 말아줘."
                + " (문제 번호와 문제): (문제를 작성) '\\n' 답: (답) '\\n' 풀이: (문제에 대한 풀이); 로 작성해줘."
                + " 각 문제의 끝마다 ';'를 넣어 문제가 끝났음을 알려줘.";

        ChatGPTResponse chatGPTResponse = gptManager.getProblem(prompt);
        insertProblemPaper(request);
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
            System.out.println(problemList.size());
            ProblemPaper problemPaper = optionalProblemPaper.get();
            // 3의 배수
            for (int i = 0; (i+3) < problemList.size(); i += 3) {
                problemRepo.save(Problem.builder()
                        .problemPaperId(problemPaper)
                        .question(problemList.get(i))
                        .answer(problemList.get(i+1))
                        .explanation(problemList.get(i+2)).build());
            }
            System.out.println("DB 저장이 성공적으로 완료되었음.");
            return true;
        }
        System.out.println("예외 발생, DB 저장 실패");
        return false;
    }

    private boolean insertProblemPaper(CreateProblemRequest request){
        Optional<User> optionalUser = userRepo.findById(1);
        User user = optionalUser.get();

        if (optionalUser.isPresent()){
            problemPaperRepo.save(ProblemPaper.builder()
                    .user(user)
                    .title("dd")
                    .field(request.getField())
                    .detailedField(request.getDetailedField())
                    .category(request.getCategory())
                    .count(request.getCount())
                    .difficulty(request.getDifficulty())
                    .bookmarked(false)
                    .build());
            System.out.println("문제지 DB 저장 성공");
            return true;
        }
        System.out.println("예외 발생, DB 저장 실패");
        return false;
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