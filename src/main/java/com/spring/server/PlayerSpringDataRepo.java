package com.spring.server;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

/*
Spring Data identified this duplication of code when writing repositories and created some predefined repositories.
The developer provides the entity type and its primary key and Spring Data comes up with the CRUD methods for the entity.
Spring Data JPA adds a layer of abstraction over the JPA provider (Hibernate in this case).
 */
public interface PlayerSpringDataRepo extends JpaRepository<Player, Integer> {
    //no implementation required!
    /*
    Add other custom methods
    Structure of Derived Query Methods
    The first part — such as find — is the introducer, and the rest — such as ByName — is the criteria.
    */
    Player findByName(String name);
    // Use Distinct, First or Top to remove duplicates or limit our result set
    List<Player> findFist3ByOrderByTitles();
    // Add Is or Equals for readability
    Player findByNameIs(String name);
    Player findByNameIsNot(String name);
    // As null equality is a special case, we should not use the = operator.
    // Spring Data JPA handles null parameters by default. So, when we pass a null value for an equality condition,
    // Spring interprets the query as IS NULL in the generated SQL.
    // Use the IsNull keyword to add IS NULL criteria to the query
    /*
    List<Player> findByNameIsNull();
    List<Player> findByNameIsNotNotNull();
     */
    // Find names that start with a value using StartingWith, Roughly, this translates to WHERE name LIKE ‘value%’
    List<Player> findByNameStartingWith(String prefix);
    // Find which names contain a value with Containing
    List<Player> findByNameContaining(String infix);
    // Add our own LIKE with the Like keyword
    List<Player> findByNameLike(String likePattern);
    // Furthermore, use LessThan and LessThanEqual keywords to compare the records with the given value using the < and <= operators
    List<Player> findByTitlesLessThan(int titles);
    List<Player> findByTitlesLessThanEqual(int titles);
    List<Player> findByTitlesGreaterThan(int titles);
    List<Player> findByTitlesGreaterThanEqual(int titles);
    // Use Before and After
    List<Player> findByBirthDateAfter(ZonedDateTime birthDate);
    List<Player> findByBirthDateBefore(ZonedDateTime birthDate);
    // Multiple Condition Expressions by using And, & Or keywords:
    List<Player> findByNameOrNationality(String name, String nationality);
    List<Player> findByNameOrTitles(String name, int titles);
    List<Player> findByNameAndNationality(String name, String nationality);
    // Sorting by using OrderBy
    List<Player> findByNameOrderByName(String name);
    List<Player> findByNameOrderByNameAsc(String name);
    List<Player> findByNameOrderByNameDesc(String name);

    // Custom method using @Query
    @Query("SELECT p FROM Player p WHERE p.birthDate >= ?1 and p.birthDate <= ?2")
    List<Player> fetchPlayersBetweenDate(Date statDate, Date endDate);
}
