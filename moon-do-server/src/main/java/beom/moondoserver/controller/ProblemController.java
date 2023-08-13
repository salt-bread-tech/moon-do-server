package beom.moondoserver.controller;

import beom.moondoserver.model.dto.request.CreateProblemRequest;
import beom.moondoserver.model.dto.response.CreateProblemResponse;
import beom.moondoserver.service.ProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/problems")
public class ProblemController {
    // 문제 만들기, 문제 저장
    final ProblemService problemService;

    @Autowired
    public ProblemController(ProblemService problemService) {
        this.problemService = problemService;
    }

    @PostMapping("/creation") //문제 생성 API
    public List<CreateProblemResponse> createProblem(@RequestBody CreateProblemRequest request){
        return problemService.createProblem(request);
    }
}
