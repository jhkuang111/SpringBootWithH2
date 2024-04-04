package com.spring.server.user;

import com.spring.server.authentication.AuthenticationService;
import com.spring.server.authentication.AuthenticationToken;
import com.spring.server.authentication.MessageStrings;
import com.spring.server.exception.AuthenticationFailException;
import com.spring.server.exception.CustomException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

@Service
public class UserService {

    Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthenticationService authenticationService;

    public SignUpResponseDto signUp(SignupDto signupDto) throws CustomException {
        if (Objects.nonNull(userRepository.findByEmail(signupDto.getEmail()))) {
            throw new CustomException("User already exists");
        }
        String encryptedPassword = signupDto.getPassword();
        try {
            encryptedPassword = hashPassword(signupDto.getPassword());
        } catch (NoSuchAlgorithmException ex) {
            logger.error("hashing password failed: ", ex);
            throw new CustomException(ex.getMessage());
        }
        User user = new User(signupDto.getFirstName(), signupDto.getLastName(), signupDto.getEmail(), encryptedPassword);
        try {
            userRepository.save(user);
            final AuthenticationToken authenticationToken = new AuthenticationToken(user);
            authenticationService.saveConfirmationToken(authenticationToken);
            return new SignUpResponseDto("success", "user created successfully");
        } catch (Exception ex) {
            throw new CustomException(ex.getMessage());
        }
    }

    public SignInResponseDto signIn(SignInDto signInDto) throws AuthenticationFailException, CustomException {
        User user = userRepository.findByEmail(signInDto.getEmail());
        if (Objects.isNull(user)) {
            throw new AuthenticationFailException("user not present");
        }
        try {
            if (!user.getPassword().equals(hashPassword(signInDto.getPassword()))) {
                throw new AuthenticationFailException(MessageStrings.WRONG_PASSWORD);
            }
        } catch (NoSuchAlgorithmException ex) {
            logger.error("hashing password failed: ", ex);
            throw new CustomException(ex.getMessage());
        }
        AuthenticationToken token = authenticationService.getToken(user);
        if (Objects.isNull(token)) {
            throw new CustomException(MessageStrings.AUTH_TOEKN_NOT_PRESENT);
        }
        return new SignInResponseDto("success", token.getToken());
    }

    private String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());
        byte[] digest = md.digest();
        return DatatypeConverter.printHexBinary(digest).toUpperCase();
    }
}
