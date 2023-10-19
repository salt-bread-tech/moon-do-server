package beom.moondoserver.service;

import beom.moondoserver.model.dto.request.BookmarkedRequest;
import beom.moondoserver.model.dto.request.GetInfoRequest;
import beom.moondoserver.model.dto.response.BookmarkedResponse;
import beom.moondoserver.model.dto.response.GetInfoResponse;

import java.util.List;

public interface ProblemPaperService {
    List<GetInfoResponse> getInfo(GetInfoRequest request);

    List<BookmarkedResponse> getBookmarkedProblemPaper(BookmarkedRequest request);
}
