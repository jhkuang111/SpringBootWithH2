package com.spring.server.user;

import com.spring.server.exception.AuthenticationFailException;
import com.spring.server.exception.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/user")
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/signup")
    public SignUpResponseDto signUp(@RequestBody SignupDto signupDto) throws CustomException {
        return userService.signUp(signupDto);
    }

    @PostMapping("/signin")
    public SignInResponseDto signIn(@RequestBody SignInDto signInDto) throws AuthenticationFailException, CustomException {
        return userService.signIn(signInDto);
    }
}
