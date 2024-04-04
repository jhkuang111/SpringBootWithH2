package com.spring.server.authentication;

import com.spring.server.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<AuthenticationToken, Integer> {

    AuthenticationToken findTokenByUser(User user);
    AuthenticationToken findTokenByToken(String token);
}
