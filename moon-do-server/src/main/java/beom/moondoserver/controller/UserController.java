package beom.moondoserver.controller;

import beom.moondoserver.model.dto.request.LoginRequest;
import beom.moondoserver.model.dto.request.RegisterRequest;
import beom.moondoserver.model.dto.response.LoginResponse;
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

    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest request) {
        return userService.register(request);
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        return userService.login(request);
    }

    @GetMapping("/info")
    public UserInfoResponse getUserInfo(@RequestParam("id") String userId) {
        return userService.getUserInfo(userId);
    }

}
