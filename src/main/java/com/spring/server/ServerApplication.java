package com.spring.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.text.SimpleDateFormat;
import java.util.Locale;

@SpringBootApplication
public class ServerApplication implements CommandLineRunner {

	@Autowired
	PlayerService playerService;

	public static void main(String[] args) {
		SpringApplication.run(ServerApplication.class, args);
	}

	// Run the cmd to inject data into database after spring app is up and running
	@Override
	public void run(String... args) throws Exception {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
		playerService.addPlayer(new Player("Federer", "Switzerland", formatter.parse("22-11-1984"), 151));
		playerService.addPlayer(new Player("Djokovic", "Serbia", formatter.parse("22-05-1987"), 87));
		playerService.addPlayer(new Player("Tim", "USA", formatter.parse("23-07-1990"), 100));
	}
}
