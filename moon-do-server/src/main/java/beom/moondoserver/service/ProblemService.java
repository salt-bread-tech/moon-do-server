package beom.moondoserver.service;

import beom.moondoserver.model.dto.request.CreateProblemRequest;
import beom.moondoserver.model.dto.request.GetProblemRequest;
import beom.moondoserver.model.dto.request.GetSolutionRequest;
import beom.moondoserver.model.dto.response.CreateProblemResponse;
import beom.moondoserver.model.dto.response.GetProblemResponse;
import beom.moondoserver.model.dto.response.GetSolutionResponse;

import java.util.List;

public interface ProblemService {
    boolean createProblem(CreateProblemRequest request);
    List<GetProblemResponse> getProblem(GetProblemRequest request);
    List<GetSolutionResponse> getSolution(GetSolutionRequest request);
}
