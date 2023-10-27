package beom.moondoserver.service;

import beom.moondoserver.model.dto.request.LoginRequest;
import beom.moondoserver.model.dto.request.RegisterRequest;
import beom.moondoserver.model.dto.request.UserInfoRequest;
import beom.moondoserver.model.dto.response.LoginResponse;
import beom.moondoserver.model.dto.response.UserInfoResponse;
import beom.moondoserver.model.entity.User;
import beom.moondoserver.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;

    @Override
    public String register(RegisterRequest request) {
        String result;

        if (!isExistingEmail(request.getEmail())) {
            String salt = getSalt();

            try {
                userRepo.save(User.builder()
                        .email(request.getEmail())
                        .password(hashing(request.getPassword(), salt))
                        .nickname(request.getNickname())
                        .salt(salt)
                        .introduction("")
                        .build());

                System.out.println("회원 가입 성공");
                result = "1";
            } catch (NoSuchAlgorithmException e) {
                System.out.println("해싱 오류");
                throw new RuntimeException(e);
            }
        }
        else {
            System.out.println("회원 가입 실패: 아이디 중복");
            result = "2";
        }

        return result;
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        LoginResponse result = new LoginResponse();
        String email = request.getEmail();
        String password = request.getPassword();

        if (!isExistingEmail(email)) {
            System.out.println("아이디가 존재하지 않음");
            result.setCode("3");
            result.setUserId(0);
        }
        else if (!isValidPassword(email, password)) {
            System.out.println("비밀번호가 올바르지 않음");
            result.setCode("2");
            result.setUserId(0);
        }
        else {
            Optional<User> user = userRepo.findByEmail(email);
            result.setCode("1");
            user.ifPresent(value -> result.setUserId(value.getUserId()));

            System.out.println("로그인 성공");
        }

        return result;
    }

    public UserInfoResponse getUserInfo(UserInfoRequest request) {
        Optional<User> optionalUser = userRepo.findByUserId(request.getUserId());
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

    private String hashing(String password, String salt) throws NoSuchAlgorithmException {
        byte[] passwordBytes = password.getBytes();
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");

        for (int i = 0; i < 100; i++) {
            String str = byteToString(passwordBytes) + salt;
            messageDigest.update(str.getBytes());
            passwordBytes = messageDigest.digest();
        }

        return byteToString(passwordBytes);
    }

    private String getSalt() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] bytes = new byte[16];
        secureRandom.nextBytes(bytes);

        return byteToString(bytes);
    }

    private String byteToString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();

        for (byte a : bytes) {
            sb.append(String.format("%02x", a));
        }

        return sb.toString();
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

        if (user.isPresent()) {
            String salt = user.get().getSalt();
            try {
                if (hashing(password, salt).equals(user.get().getPassword())) {
                    result = true;
                }
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
        }

        return result;
    }

}