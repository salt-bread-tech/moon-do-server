package beom.moondoserver.service;

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

    public UserInfoResponse getUserInfo(Integer userId) {
        Optional<User> optionalUser = userRepo.findById(userId);
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

}
