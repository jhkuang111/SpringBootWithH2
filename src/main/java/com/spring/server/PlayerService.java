package com.spring.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlayerService {

    @Autowired
    PlayerSpringDataRepo playerRepo;

    // Get all players
    public List<Player> getAllPlayers() {
        return playerRepo.findAll();
    }

    // Add new player
    public Player addPlayer(Player player) {
        return playerRepo.save(player);
    }

    // Get player by ID
    public Player getPlayerById(int id) {
        Optional<Player> player = playerRepo.findById(id);
        if (player.isEmpty()) {
            throw new RuntimeException("Player with id {"+ id +"} not found");
        }
        return player.get();
    }

}
