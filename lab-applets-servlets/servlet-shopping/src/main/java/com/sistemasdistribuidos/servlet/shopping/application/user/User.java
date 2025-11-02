/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemasdistribuidos.servlet.shopping.application.user;

import com.sistemasdistribuidos.servlet.shopping.application.common.JsonSerializable;
import com.sistemasdistribuidos.servlet.shopping.application.common.JsonUtils;
import com.sistemasdistribuidos.servlet.shopping.application.common.RandomIdGenerator;

/**
 * Represents a user in the shopping system.
 * Each user has a unique ID (automatically generated) and a name.
 *
 * @author mariovillacortagarcia
 */
public class User implements JsonSerializable {

    private final String id;
    private String name;

    /**
     * Constructs a new User with the specified name.
     * A unique ID is automatically generated for the user.
     *
     * @param name The name of the user
     */
    public User(String name) {
        id = RandomIdGenerator.generate();
        this.name = name;
    }

    /**
     * Sets the name of the user.
     *
     * @param name The new name for the user
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the name of the user.
     *
     * @return The user's name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the unique ID of the user.
     *
     * @return The user's ID
     */
    public String getId() {
        return id;
    }

    /**
     * Converts this User object to JSON format
     *
     * @return JSON string representation of this user
     */
    public String toJson() {
        return "{\"id\":\"" + JsonUtils.escapeJson(this.id) + "\",\"name\":\"" + JsonUtils.escapeJson(this.name) + "\"}";
    }
}
