package com.spring.server;

import com.spring.server.authentication.AuthenticationService;
import com.spring.server.exception.AuthenticationFailException;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PlayerController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    PlayerService playerService;

    @Autowired
    AuthenticationService authenticationService;

    @GetMapping("/players")
    public List<Player> getAllPlayers() {
        return playerService.getAllPlayers();
    }

    // Should change Player to PlayerDto
    // Now POST request contains request param "token" /players?token=VALID_TOKENS_STRING
    @PostMapping("/players")
    public Player addPlayer(@RequestBody @Valid Player newPlayer, @RequestParam("token") String token) throws AuthenticationFailException {
        // User need to sign up and then sign in to get token before allowing to add new player
        authenticationService.authenticate(token);
        logger.info("User is authenticated to add new player");
        newPlayer.setId(0);
        return playerService.addPlayer(newPlayer);
    }

    @GetMapping("/players/{id}")
    public Player getPlayerById(@PathVariable int id) {
        Player player = playerService.getPlayerById(id);
        if (player == null) {
            logger.warn("Player with id {} is not found", id);
        }
        return player;
    }

    @GetMapping("/players/{pageNumber}/{pageSize}/{sortByAttribute}")
    public List<Player> getPlayersWithPagination(@PathVariable int pageNumber, @PathVariable int pageSize, @PathVariable String sortByAttribute) {
        logger.info("PageNumber: {}, PageSize: {}, sort: {}", pageNumber, pageSize, sortByAttribute);
        Page<Player> pagePlayers = playerService.getPlayersWithPagination(pageNumber, pageSize, sortByAttribute);
        return pagePlayers.getContent();
    }
}
