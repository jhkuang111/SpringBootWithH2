package com.spring.server;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PlayerServiceTest {

    @Mock
    PlayerSpringDataRepo playerRepo;

    @InjectMocks
    PlayerService playerService;

    @Test
    void testGetAllPlayers() {
        Player one = new Player("playerOne", "NationOne", new Date(), 1);
        Player two = new Player("playerTwo", "NationTwo", new Date(), 2);
        when(playerRepo.findAll()).thenReturn(Arrays.asList(one, two));
        int expectedAllPlayers = 2;
        assertEquals(expectedAllPlayers,  playerService.getAllPlayers().size());
        assertEquals("playerOne", playerService.getAllPlayers().get(0).getName());
        assertEquals("playerTwo", playerService.getAllPlayers().get(1).getName());
    }
}
