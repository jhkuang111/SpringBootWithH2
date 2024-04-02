package com.spring.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerService {

    @Autowired
    PlayerSpringDataRepo playerRepo;

    // Get all players
    public List<Player> getAllPlayers() {
        return playerRepo.findAll();
    }
}
