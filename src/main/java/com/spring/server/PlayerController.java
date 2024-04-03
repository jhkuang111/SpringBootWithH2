package com.spring.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@RestController
public class PlayerController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    PlayerService playerService;

    @GetMapping("/players")
    public List<Player> getAllPlayers() {
        return playerService.getAllPlayers();
    }

    @PostMapping("/players")
    public Player addPlayer(@RequestBody Player newPlayer) {
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
