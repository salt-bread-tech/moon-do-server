package beom.moondoserver.service;

import beom.moondoserver.model.dto.response.UserInfoResponse;

public interface UserService {
    UserInfoResponse getUserInfo(Integer userId);
}
