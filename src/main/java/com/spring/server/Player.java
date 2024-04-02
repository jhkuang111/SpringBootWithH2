package com.spring.server;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Date;

/*
The Spring Boot autoconfiguration triggers a schema update and creates a table by the same name as the class
marked with the @Entity annotation. When using JPA, we do not need to create a table.
We will comment out the table creation query in schema sql file as it is not needed anymore.
*/

// @Table(name="Player") if want the table name to be different
@Entity
public class Player {
    @Id // used to indicate the primary key
    @GeneratedValue // used to automatically generate Id values.
    private int id;
    private String name;
    private String nationality;
    private Date birthDate;
    private int titles;

    public Player() {}

    public Player(String name, String nationality, Date birthDate, int titles) {
        super();
        this.name = name;
        this.nationality = nationality;
        this.birthDate = birthDate;
        this.titles = titles;
    }

    public Player(int id, String name, String nationality, Date birthDate, int titles) {
        super();
        this.id = id;
        this.name = name;
        this.nationality = nationality;
        this.birthDate = birthDate;
        this.titles = titles;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public int getTitles() {
        return titles;
    }

    public void setTitles(int titles) {
        this.titles = titles;
    }

    @Override
    public String toString() {
        return "\nPlayer [id= " + id + ", name= " + name + ", nationality= " + nationality + ", birthDate= " + birthDate
                + ", titles= " + titles + "]";
    }
}
