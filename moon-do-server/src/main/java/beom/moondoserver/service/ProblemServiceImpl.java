package beom.moondoserver.service;

import beom.moondoserver.model.dto.request.BookmarkRequest;
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
import beom.moondoserver.util.Difficulty;
import beom.moondoserver.util.DifficultyConverter;
import beom.moondoserver.util.GPTManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ProblemServiceImpl implements ProblemService {

    GPTManager gptManager = new GPTManager();
    private final ProblemRepo problemRepo;
    private final UserRepo userRepo;
    private final ProblemPaperRepo problemPaperRepo;

    @Override
    public CreateProblemResponse createProblem(CreateProblemRequest request) {
        Difficulty df = Difficulty.ofValue(request.getDifficulty());
        String prompt = request.getField() + " 분야의 " + request.getDetailedField() + "에 대한 "
                + request.getCategory() + " 문제를 " + df + "의 난이도로 "
                + request.getCount() + "개 만큼 출제하고 정답과 풀이를 함께 출력해주세요.\n";

        ChatGPTResponse chatGPTResponse = gptManager.getProblem(prompt);
        int problemPaperId = insertProblemPaper(request);
        insertProblem(chatGPTResponse, problemPaperId);

        return new CreateProblemResponse(problemPaperId);
    }

    @Override
    public List<String> getProblem(GetProblemRequest request) {
        List<String> getProblemResponses = new ArrayList<>();
        Optional<ProblemPaper> problemPaper = problemPaperRepo.findById(request.getProblemPaperId());

        if (problemPaper.isPresent()) {
            List<Problem> problems = problemRepo.findAllByProblemPaperId(problemPaper.get());

            for (Problem p: problems) {
                getProblemResponses.add(p.getQuestion());
            }
        }

        return getProblemResponses;
    }

    @Override
    public List<GetSolutionResponse> getSolution(GetSolutionRequest request) {
        List<GetSolutionResponse> getSolutionResponses = new ArrayList<>();
        Optional<ProblemPaper> problemPaper = problemPaperRepo.findById(request.getProblemPaperId());

        if (problemPaper.isPresent()) {
            List<Problem> problems = problemRepo.findAllByProblemPaperId(problemPaper.get());

            for (Problem p: problems) {
                getSolutionResponses.add(new GetSolutionResponse(p.getAnswer(), p.getExplanation()));
            }
        }

        return getSolutionResponses;
    }

    private boolean insertProblem(ChatGPTResponse chatGPTResponse, int problemPaperId) {
        Optional<ProblemPaper> optionalProblemPaper = problemPaperRepo.findById(problemPaperId);
        List<String> problemList = parseProblem(chatGPTResponse);

        if(optionalProblemPaper.isPresent()){
            System.out.println(problemList.size());
            ProblemPaper problemPaper = optionalProblemPaper.get();
            // 3의 배수
            for (int i = 0; (i+3) <= problemList.size(); i += 3) {
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

    private int insertProblemPaper(CreateProblemRequest request){
        Difficulty df = Difficulty.ofValue(request.getDifficulty());
        Optional<User> optionalUser = userRepo.findById(request.getUserId());
        long currentMillis = System.currentTimeMillis();
        Instant instant = Instant.ofEpochMilli(currentMillis);

        if (optionalUser.isPresent()){
            User user = optionalUser.get();
            ProblemPaper problemPaper = ProblemPaper.builder()
                    .user(user)
                    .title(request.getTitle())
                    .field(request.getField())
                    .detailedField(request.getDetailedField())
                    .category(request.getCategory())
                    .count(request.getCount())
                    .difficulty(df)
                    .bookmarked(false)
                    .date(instant.atZone(ZoneId.of("Asia/Seoul")).toLocalDateTime())
                    .build();

            problemPaperRepo.save(problemPaper);
            System.out.println("문제지 DB 저장 성공");

            return problemPaper.getProblemPaperId();
        }

        System.out.println("예외 발생, DB 저장 실패");
        return 0;
    }

/*    private List<String> parseProblem(ChatGPTResponse chatGPTResponse){
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
    }*/

    private List<String> parseProblem(ChatGPTResponse chatGPTResponse){
        List<String> problemResponseList = new ArrayList<>();
        String string = chatGPTResponse.getChoices().get(0).getMessage().getContent();
        String[] problems = string.split("\\n(?=문제 [0-9]+:)");

        System.out.println(string);
        for (String problem : problems) {
            String[] parts = problem.split("\\n");

            StringBuilder question = new StringBuilder();
            for (int i = 0; i < parts.length - 2; i++) {
                question.append(parts[i]).append("  ");
            }
            String answer = null;
            String solution = null;

            for (String part : parts) {
                if (part.startsWith("정답")) {
                    answer = part;
                } else if (part.startsWith("풀이")) {
                    solution = part;
                }
            }

            problemResponseList.add(question.toString());
            problemResponseList.add(answer);
            problemResponseList.add(solution);
        }
        return problemResponseList;
    }
}