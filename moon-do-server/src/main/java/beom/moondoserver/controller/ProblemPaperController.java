package beom.moondoserver.controller;

import beom.moondoserver.model.dto.request.BookmarkRequest;
import beom.moondoserver.model.dto.request.BookmarkedPaperRequest;
import beom.moondoserver.model.dto.request.DeleteRequest;
import beom.moondoserver.model.dto.request.GetInfoRequest;
import beom.moondoserver.model.dto.response.BookmarkResponse;
import beom.moondoserver.model.dto.response.BookmarkedPaperResponse;
import beom.moondoserver.model.dto.response.GetInfoResponse;
import beom.moondoserver.model.entity.ProblemPaper;
import beom.moondoserver.service.ProblemPaperService;
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

    @PostMapping("/bookmarked/info")
    public List<BookmarkedPaperResponse> getBookmarkedProblemPaper(@RequestBody BookmarkedPaperRequest request) {
        return problemPaperService.getBookmarkedProblemPaper(request);
    }

    @PostMapping("/bookmark")
    public BookmarkResponse bookmark(@RequestBody BookmarkRequest request) {
        return problemPaperService.bookmark(request);
    }

    @PostMapping("/deletion")
    public boolean deleteProblemPaper(@RequestBody DeleteRequest request){
        return problemPaperService.deleteProblemPaper(request);
    }
}
