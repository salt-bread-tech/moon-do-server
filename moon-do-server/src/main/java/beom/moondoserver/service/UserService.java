package beom.moondoserver.service;

import beom.moondoserver.model.dto.request.LoginRequest;
import beom.moondoserver.model.dto.request.RegisterRequest;
import beom.moondoserver.model.dto.response.UserInfoResponse;

public interface UserService {
    UserInfoResponse getUserInfo(Integer userId);

    UserInfoResponse getUserInfo(String email);

    String register(RegisterRequest request);

    String login(LoginRequest request);
}
