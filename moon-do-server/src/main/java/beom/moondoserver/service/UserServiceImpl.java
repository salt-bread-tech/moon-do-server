package beom.moondoserver.service;

import beom.moondoserver.model.dto.request.LoginRequest;
import beom.moondoserver.model.dto.request.RegisterRequest;
import beom.moondoserver.model.dto.response.UserInfoResponse;
import beom.moondoserver.model.entity.User;
import beom.moondoserver.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;

    @Override
    public String register(RegisterRequest request) {
        String result;

        if (!isExistingEmail(request.getEmail())) {
            userRepo.save(User.builder()
                    .email(request.getEmail())
                    .password(request.getPassword())
                    .nickname(request.getNickname())
                    .introduction("")
                    .build());

            System.out.println("회원 가입 성공");
            result = "1";
        }
        else {
            System.out.println("회원 가입 실패: 아이디 중복");
            result = "2";
        }

        return result;
    }

    @Override
    public String login(LoginRequest request) {
        String result;
        String email = request.getEmail();
        String password = request.getPassword();

        if (!isExistingEmail(email)) {
            System.out.println("아이디가 존재하지 않음");
            result = "3";
        }
        else if (!isValidPassword(email, password)) {
            System.out.println("비밀번호가 올바르지 않음");
            result = "2";
        }
        else {
            System.out.println("로그인 성공");
            result = "1";
        }

        return result;
    }

    public UserInfoResponse getUserInfo(String email) {
        Optional<User> optionalUser = userRepo.findByEmail(email);
        UserInfoResponse userInfoResponse = new UserInfoResponse();

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            userInfoResponse.setUserName(user.getNickname());
            userInfoResponse.setIntroduction(user.getIntroduction());
        }
        else {
            userInfoResponse.setUserName("닉네임");
            userInfoResponse.setIntroduction("소개");
        }

        return userInfoResponse;
    }

    private boolean isExistingEmail(String email) {   // 존재하는 email 인지 검사 (중복 여부)  true: 존재함  false: 존재하지 않음
        boolean result = false;
        Optional<User> user = userRepo.findByEmail(email);

        if (user.isPresent()) result = true;

        return result;
    }

    private boolean isValidPassword(String email, String password) { // 유효한 비밀번호인지 검사
        boolean result = false;
        Optional<User> user = userRepo.findByEmail(email);

        if (user.isPresent() && user.get().getPassword().equals(password)) {
            result = true;
        }
//
        return result;
    }

}