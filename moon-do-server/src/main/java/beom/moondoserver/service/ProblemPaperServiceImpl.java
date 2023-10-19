package beom.moondoserver.service;

import beom.moondoserver.model.dto.request.GetInfoRequest;
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
                getInfoResponses.add(new GetInfoResponse(p.getCount(), p.getTitle(), p.getField(), p.getDetailedField()));
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
}
