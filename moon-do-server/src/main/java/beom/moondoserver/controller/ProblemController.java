package beom.moondoserver.controller;

import beom.moondoserver.model.dto.request.CreateProblemRequest;
import beom.moondoserver.model.dto.response.CreateProblemResponse;
import beom.moondoserver.service.ProblemService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/problems")
public class ProblemController {
    // 문제 만들기, 문제 저장
    final ProblemService problemService;

    public ProblemController(ProblemService problemService){
        this.problemService = problemService;
    }

    @PostMapping("/creation") //문제 생성 API
    public List<CreateProblemResponse> createProblem(@RequestBody CreateProblemRequest request){
        return problemService.createProblem(request);
    }
}
