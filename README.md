# Spring Boot with H2 Database and JPA

### Download template project code from Spring Initializer
1. Go to [spring initializr](https://start.spring.io/)
2. Choose **Maven**, **Java**, **3.2.4** (by default), **Jar**, and **17**
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