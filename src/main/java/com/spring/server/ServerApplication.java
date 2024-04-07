package com.spring.server;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.spring.server.REST.ApiCallDto;
import com.spring.server.REST.RestClient;
import com.spring.server.redis.RedisClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;

@SpringBootApplication
@EnableConfigurationProperties(ApiCallDto.class) // This registers the class with the Spring container
public class ServerApplication implements CommandLineRunner {

	Logger logger = LoggerFactory.getLogger(ServerApplication.class);

	@Autowired
	PlayerService playerService;

	@Autowired
	RestClient restClient;

	@Autowired
	ApiCallDto apiCallDto;

	@Autowired
	RedisTemplate<String, String> redisTemplate;

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

		RestTemplate restTemplate = restClient.getRestClient();
		logger.info("Calling REST API for Forbes 400");
		ResponseEntity<String> forbes400Entity;
		try {
			String url = apiCallDto.buildEndPoint();
			if (Boolean.TRUE.equals(redisTemplate.hasKey(url))) {
				logger.info("Request data found in Redis Cache, no need to call external API");
				String playerListStr = redisTemplate.opsForValue().get(url);
				fillDataBaseDuringStartUp(playerListStr);
			} else {
				logger.info("Calling external API for data");
				forbes400Entity = restTemplate.getForEntity(url, String.class);
				logger.info("Retrieved result from API");
				if (Objects.isNull(forbes400Entity)) {
					logger.info("Result is null");
				} else {
					logger.info("Saving data into H2 database");
					fillDataBaseDuringStartUp(forbes400Entity.getBody());
					logger.info("Also saving data into Redis Cache");
					redisTemplate.opsForValue().set(url, forbes400Entity.getBody());
					logger.info("All saving is done...");
				}
			}
		} catch (Exception ex) {
			logger.error("Error calling API", ex);
		}
	}

	private void fillDataBaseDuringStartUp(String input) throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		final JsonNode jsonNode = objectMapper.readTree(input);
		if (jsonNode.isArray()) {
			for (JsonNode eachInput : jsonNode) {
				Player player = new Player();
				final String playerString = eachInput.toString();
				String personName = JsonPath.read(playerString, "$.personName");
				player.setName(personName);
				String country = JsonPath.read(playerString, "$.countryOfCitizenship");
				player.setNationality(country);
				long birthDate = JsonPath.read(playerString, "$.birthDate");
				Instant birthDateInstant = Instant.ofEpochMilli(birthDate);
				player.setBirthDate(Date.from(birthDateInstant));
				double wealth = JsonPath.read(playerString, "$.finalWorth");
				player.setTitles((int) wealth / 1000);
				playerService.addPlayer(player);
			}
		}
	}
}
