package beom.moondoserver.service;

import beom.moondoserver.model.dto.request.BookmarkRequest;
import beom.moondoserver.model.dto.request.BookmarkedPaperRequest;
import beom.moondoserver.model.dto.request.GetInfoRequest;
import beom.moondoserver.model.dto.response.BookmarkResponse;
import beom.moondoserver.model.dto.response.BookmarkedPaperResponse;
import beom.moondoserver.model.dto.response.GetInfoResponse;
import beom.moondoserver.model.entity.ProblemPaper;
import beom.moondoserver.model.entity.User;
import beom.moondoserver.repository.ProblemPaperRepo;
import beom.moondoserver.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProblemPaperServiceImpl implements ProblemPaperService{
    private final ProblemPaperRepo problemPaperRepo;
    private final UserRepo userRepo;
    @Override
    public List<GetInfoResponse> getInfo(GetInfoRequest request) {
        List<GetInfoResponse> getInfoResponses = new ArrayList<>();
        Optional<User> optionalUser = userRepo.findById(request.getUserId());

        if (optionalUser.isPresent()) {
            List<ProblemPaper> problemPapers = problemPaperRepo.findAllByUser_UserId(request.getUserId());

            for(ProblemPaper p : problemPapers){
                getInfoResponses.add(new GetInfoResponse(p.getProblemPaperId()
                        , p.getCount(), p.getTitle()
                        , p.getField(), p.getDetailedField()
                ,p.getDate()));
            }
            System.out.println("Data Access Success");
            for (GetInfoResponse g : getInfoResponses) {
                System.out.println("title: " + g.getTitle() + "\n개수: " + g.getCount());
            }
        }else {
            System.out.println("Data Access Denied");
        }
        return getInfoResponses;
    }

    @Override
    public List<BookmarkedPaperResponse> getBookmarkedProblemPaper(BookmarkedPaperRequest request) {
        List<BookmarkedPaperResponse> bookmarkedPaperRespons = new ArrayList<>();
        Optional<User> optionalUser = userRepo.findById(request.getUserId());

        if (optionalUser.isPresent()) {
            List<ProblemPaper> problemPapers = problemPaperRepo.findAllByUser_UserIdAndBookmarkedIsTrue(request.getUserId());

            for (ProblemPaper p : problemPapers) {
                bookmarkedPaperRespons.add(BookmarkedPaperResponse.builder()
                                .problemPaperId(p.getProblemPaperId())
                                .count(p.getCount())
                                .title(p.getTitle())
                                .field(p.getField())
                                .detailedField(p.getDetailedField()).build());
            }
        }
        else {
            System.out.println("유저가 존재하지 않습니다.");
        }

        return bookmarkedPaperRespons;
    }

    @Override
    public BookmarkResponse bookmark(BookmarkRequest request) {
        BookmarkResponse response = new BookmarkResponse();
        Optional<ProblemPaper> problemPaperOptional = problemPaperRepo.findById(request.getProblemPaperId());

        if (problemPaperOptional.isPresent()) {
            ProblemPaper problemPaper = problemPaperOptional.get();
            response.setMessage("Successful processing");

            if (problemPaper.getBookmarked()) {
                System.out.println("북마크 해제");
                problemPaper.setBookmarked(false);
            }
            else {
                System.out.println("북마크 설정");
                problemPaper.setBookmarked(true);
            }

            problemPaperRepo.save(problemPaper);
            response.setBookmarked(problemPaper.getBookmarked());
        }
        else {
            System.out.println("북마크 실패");
            response.setMessage("Fail to bookmark");
            response.setBookmarked(false);
        }

        return response;
    }
}
