package beom.moondoserver.controller;

import beom.moondoserver.model.dto.request.BookmarkedRequest;
import beom.moondoserver.model.dto.request.GetInfoRequest;
import beom.moondoserver.model.dto.response.BookmarkedResponse;
import beom.moondoserver.model.dto.response.GetInfoResponse;
import beom.moondoserver.service.ProblemPaperService;
import beom.moondoserver.service.ProblemPaperServiceImpl;
import beom.moondoserver.service.ProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/paper")
public class ProblemPaperController {
    final ProblemPaperService problemPaperService;

    @Autowired
    public ProblemPaperController(ProblemPaperService problemPaperService) {
        this.problemPaperService = problemPaperService;
    }

    @PostMapping("/info")
    public List<GetInfoResponse> getProblemPaper(@RequestBody GetInfoRequest request){
        return problemPaperService.getInfo(request);
    }

    @PostMapping("/bookmark")
    public List<BookmarkedResponse> getBookmarkedProblemPaper(@RequestBody BookmarkedRequest request) {
        return problemPaperService.getBookmarkedProblemPaper(request);
    }
}
