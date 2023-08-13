package beom.moondoserver.controller;

import beom.moondoserver.model.dto.response.UserInfoResponse;
import beom.moondoserver.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/info")
    public UserInfoResponse getUserInfo(@RequestParam("id") String userId) {
        return userService.getUserInfo(userId);
    }

}
