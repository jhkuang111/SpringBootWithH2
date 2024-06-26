# Spring Boot with H2 Database and JPA

### Download template project code from Spring Initializer
1. Go to [spring initializr](https://start.spring.io/)
2. Choose **Maven**, **Java**, **3.2.4** (by default), **Jar**, and **17**(JDK version)
3. Go to Dependencies, add **Spring Web**, **Spring Data JPA**, **H2 Database**
4. Click **Generate**, unzip and then the load the project from Intellij and run to make sure server is running

### Set up the H2 Database
1. Add the following to **application.properties**
```
spring.datasource.url=jdbc:h2:mem:testdb
spring.h2.console.enabled=true
spring.h2.console.settings.web-allow-others=true
```
2. Create **schema.sql** under **resources** folder (create a table for this during startup)
```
CREATE TABLE Player (
  ID INTEGER NOT NULL,
  Name VARCHAR(255) NOT NULL,
  Nationality VARCHAR(255) NOT NULL,
  Birth_date TIMESTAMP,
  Titles INTEGER,
  PRIMARY KEY (ID)
);
```
3. Insert data into the table (Example)
```
INSERT INTO Player (ID, Name, 
Nationality, Birth_date, Titles)
VALUES(1, 'Djokovic', 'Serbia', '1987-05-22', 81);
```
4. Go to http://localhost:8080/h2-console and make sure **JDBC URL** has the name of `spring.datasource.url` from above
5. Run `Select * from Player;` in the console to see that data has been inserted into the table

### Instead of schema SQL file, use JPA to create table (Hibernate under the hood)
1. Different annotations like **@Entity**, **@Column**, **@Table**, etc.
```
@Entity
@Table(name="Player")
Public class Player {

}
```
### Add service and controller layer for handling different APIs
1. Go to http://localhost:8080/players with POST request, using Postman, and input the following data in request body in JSON format
```
{
  "name": "Federer",
  "nationality": "Switzerland",
  "birthDate": "22-11-1984",
  "titles": 151
}
```
2. Call GET request to http://localhost:8080/players to retrieve all players to verify that data has been inserted
```
[
    {
        "id": 1,
        "name": "Federer",
        "nationality": "Switzerland",
        "birthDate": "22-11-1984",
        "titles": 151
    }
]
```
Above is run in Intellij, other option is to use Docker

### Dockerize the Spring boot app
1. cd into the project directory, build the jar file by running `./mvnw install` with Maven
2. Create a `Dockerfile` with following cmd
```
FROM openjdk:17-oracle
VOLUME /tmp
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
```
3. Build an image using following Docker cmd
```
docker build -t CONTAINER_IMAGE_NAME .
```
4. Check the built image under Local Images in Docker Desktop
5. Run the image in a container using following cmd
```
docker run -p 8080:8080 --rm --name CONTAINER_NAME CONTAINER_IMAGE_NAME
```
Reference: [Spring Boot Docker](https://spring.io/guides/topicals/spring-boot-docker)

### Using RestTemplate to call external API for data 
This calls external API and parse data and store data into H2 database

### Using Redis to cache data
Get Redis server running
```
redis-server
```
Introduce Spring Data Redis framework into `pom.xml`
```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>
```
Instantiate RedisTemplate Bean and add some properties in the `application.properties`
```
@Bean
public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory redisFactory) {
    logger.info("Initiating Redis Template");
    RedisTemplate<String, String> template = new RedisTemplate<>();
    template.setConnectionFactory(redisFactory);
    // Add some specific configuration here. Key serializers, etc.
    return template;
}
```
```
spring.data.redis.host=localhost
spring.data.redis.port=6379
```
Once this is done, running the service up for the first time, it will call external API
```
INFO 42953 --- [server] [           main] com.spring.server.ServerApplication      : Calling external API for data
INFO 42953 --- [server] [           main] com.spring.server.ServerApplication      : Retrieved result from API
INFO 42953 --- [server] [           main] com.spring.server.ServerApplication      : Saving data into H2 database
INFO 42953 --- [server] [           main] com.spring.server.ServerApplication      : Also saving data into Redis Cache
INFO 42953 --- [server] [           main] com.spring.server.ServerApplication      : All saving is done...
```
Stop the service and restart the service again, without stopping the Redis server
```
INFO 42967 --- [server] [           main] com.spring.server.ServerApplication      : Calling REST API for Forbes 400
INFO 42967 --- [server] [           main] com.spring.server.ServerApplication      : Request data found in Redis Cache, no need to call external API
```
Note: this only works from running in Intellij. Running the service in Docker would lead to `Unable to connect Redis`. Need to figure out later
