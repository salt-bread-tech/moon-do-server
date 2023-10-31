package beom.moondoserver.service;

import beom.moondoserver.model.dto.request.BookmarkRequest;
import beom.moondoserver.model.dto.request.BookmarkedPaperRequest;
import beom.moondoserver.model.dto.request.GetInfoRequest;
import beom.moondoserver.model.dto.response.BookmarkResponse;
import beom.moondoserver.model.dto.response.BookmarkedPaperResponse;
import beom.moondoserver.model.dto.response.GetInfoResponse;

import java.util.List;

public interface ProblemPaperService {
    List<GetInfoResponse> getInfo(GetInfoRequest request);

    List<BookmarkedPaperResponse> getBookmarkedProblemPaper(BookmarkedPaperRequest request);
    BookmarkResponse bookmark(BookmarkRequest request);
}
