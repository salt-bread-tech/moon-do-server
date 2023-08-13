package beom.moondoserver.service;

import beom.moondoserver.model.dto.request.CreateProblemRequest;
import beom.moondoserver.model.dto.response.CreateProblemResponse;

import java.util.List;

public interface ProblemService {
    List<CreateProblemResponse> createProblem(CreateProblemRequest request);
}
