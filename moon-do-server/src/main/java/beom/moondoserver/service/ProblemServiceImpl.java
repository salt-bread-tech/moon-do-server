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

import static java.lang.Math.max;

@Service
@RequiredArgsConstructor
public class ProblemServiceImpl implements ProblemService {

    GPTManager gptManager = new GPTManager();
    private final ProblemRepo problemRepo;
    private final UserRepo userRepo;
    private final ProblemPaperRepo problemPaperRepo;

    @Override
    public CreateProblemResponse createProblem(CreateProblemRequest request) {
//        String prompt = request.getField() + " 분야의 " + request.getDetailedField() + "에 대한 "
//                + request.getCategory() + " 문제를 " + request.getDifficulty() + "의 난이도로 "
//                + request.getCount() + "개 만큼 출제해줘."
//                + " 문제의 답과 풀이는 각 문제의 뒤에 '\\n'을 넣어서 함께 출력해줘." + " 문제를 작성할 때 절대 문제내용과 문제 번호 사이에 '\\n'을 넣지 말아줘."
//                + " (문제 번호와 문제): (문제를 작성) '\\n' 답: (답) '\\n' 풀이: (문제에 대한 풀이); 로 작성해줘."
//                + " 각 문제의 끝마다 ';'를 넣어 문제가 끝났음을 알려줘.";
        int count = request.getCount() + 1;
        int userId = request.getUserId();
        CreateProblemResponse createProblemResponse = new CreateProblemResponse();

        String prompt = "당신은 문제를 출제하는 AI로서, 유저가 보내는 양식에 따라 문제지를 출력해주는 역할을 수행합니다." +
                request.getField() + " 분야의 " + request.getDetailedField() + "에 대한 "
                + request.getCategory() + " 문제를 " + request.getDifficulty() + "의 난이도로 "
                + count + "개 만큼 출제해주세요.\n" +
                " 위 템플릿을 기반으로 당신은 문제를 출제합니다. 당신의 대답에 대한 템플릿도 존재합니다. 당신의 대답을 가지고 서버에서는 데이터 파싱이 이루어질 예정입니다." +
                " 따라서 반드시 템플릿에 맞게 문제를 출제해주어야 합니다. 문제 출제에 대한 템플릿은 다음과 같습니다.\n" +
                "문제 번호: 문제에 대한 내용 \\n" +
                "정답: 정답에 대한 내용 \\n" +
                "풀이: 풀이에 대한 내용; \\n" +
                "\n위와 같은 예시로 당신의 대답은 아래와 같이 이루어져야만 합니다. \n" +
                "문제 1: 스택과 큐 자료구조의 주요 차이점에 대해 설명하세요.;\\n" +
                "정답 1: 스택(Stack)은 후입선출(LIFO, Last-In-First-Out) 구조로 데이터를 저장하고 접근하는 자료구조입니다. 큐(Queue)는 선입선출(FIFO, First-In-First-Out) 구조로 데이터를 저장하고 접근하는 자료구조입니다.\\n" +
                "풀이 1: 스택은 가장 최근에 추가된 항목을 먼저 제거하는 구조로, 데이터를 쌓고 꺼낼 때 역순으로 처리됩니다. 큐는 가장 먼저 추가된 항목을 먼저 제거하는 구조로, 데이터를 삽입한 순서대로 처리됩니다.;\\n" +
                "의 템플릿을 바탕으로 문제를 출제해주세요.";

        ChatGPTResponse chatGPTResponse = gptManager.getProblem(prompt);
        insertProblemPaper(request);
        parseProblem(chatGPTResponse);

        if (insertProblem(chatGPTResponse)) {
            List<ProblemPaper> problemPapers = problemPaperRepo.findAllByUserUserId(userId);

            if (problemPapers.size() > 0) {
                int max = 0;

                for (ProblemPaper p : problemPapers) {
                    max = max(p.getProblemPaperId(), max);
                }
            }
            else {
                createProblemResponse.setProblemPaperId(0);
            }
        }

        return createProblemResponse;
    }

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
        Optional<User> optionalUser = userRepo.findById(request.getUserId());

        if (optionalUser.isPresent()){
            User user = optionalUser.get();
            problemPaperRepo.save(ProblemPaper.builder()
                    .user(user)
                    .title(request.getTitle())
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