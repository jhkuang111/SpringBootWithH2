package com.spring.server;

import org.springframework.data.jpa.repository.JpaRepository;

/*
Spring Data identified this duplication of code when writing repositories and created some predefined repositories.
The developer provides the entity type and its primary key and Spring Data comes up with the CRUD methods for the entity.
Spring Data JPA adds a layer of abstraction over the JPA provider (Hibernate in this case).
 */
public interface PlayerSpringDataRepo extends JpaRepository<Player, Integer> {
    //no implementation required!
}
