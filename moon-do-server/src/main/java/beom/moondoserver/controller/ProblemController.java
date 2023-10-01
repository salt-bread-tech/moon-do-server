package beom.moondoserver.controller;

import beom.moondoserver.model.dto.request.CreateProblemRequest;
import beom.moondoserver.model.dto.request.GetProblemRequest;
import beom.moondoserver.model.dto.request.GetSolutionRequest;
import beom.moondoserver.model.dto.response.CreateProblemResponse;
import beom.moondoserver.model.dto.response.GetProblemResponse;
import beom.moondoserver.model.dto.response.GetSolutionResponse;
import beom.moondoserver.service.ProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/problem")
public class ProblemController {
    final ProblemService problemService;

    @Autowired
    public ProblemController(ProblemService problemService) {
        this.problemService = problemService;
    }

    @PostMapping("/creation")
    public CreateProblemResponse createProblem(@RequestBody CreateProblemRequest request){
        return problemService.createProblem(request);
    }

    @PostMapping("/all")
    public List<GetProblemResponse> getProblem(GetProblemRequest request) {
        return problemService.getProblem(request);
    }

    @PostMapping("/solution")
    public List<GetSolutionResponse> getSolution(GetSolutionRequest request) {
        return problemService.getSolution(request);
    }
}
