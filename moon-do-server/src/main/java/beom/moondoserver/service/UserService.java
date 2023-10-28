package beom.moondoserver.service;

import beom.moondoserver.model.dto.request.LoginRequest;
import beom.moondoserver.model.dto.request.RegisterRequest;
import beom.moondoserver.model.dto.request.UserInfoRequest;
import beom.moondoserver.model.dto.response.LoginResponse;
import beom.moondoserver.model.dto.response.UserInfoResponse;

public interface UserService {
    UserInfoResponse getUserInfo(UserInfoRequest request);

    String register(RegisterRequest request);

    LoginResponse login(LoginRequest request);
}
