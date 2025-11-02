/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemasdistribuidos.servlet.shopping.application.user;

import java.util.ArrayList;
import java.util.List;

/**
 * Repository for managing User entities.
 * Uses ArrayList internally for dynamic size management.
 *
 * @author mariovillacortagarcia
 */
public class UserRepository {

    private List<User> users;

    /**
     * Constructs a new UserRepository with an empty list of users.
     */
    public UserRepository() {
        this.users = new ArrayList<User>();
    }

    /**
     * Gets all stored users
     *
     * @return Array of all users
     */
    public User[] getUsers() {
        return users.toArray(new User[0]);
    }

    /**
     * Gets a user by their ID
     *
     * @param id The ID of the user to find
     * @return The found user or null if it doesn't exist
     */
    public User getUserById(String id) {
        for (User user : users) {
            if (user.getId().equals(id)) {
                return user;
            }
        }
        return null;
    }

    /**
     * Creates a new user with the specified name
     *
     * @param name The name of the user
     * @return The created user
     */
    public User createUser(String name) {
        User user = new User(name);
        users.add(user);
        return user;
    }

    /**
     * Updates an existing user
     *
     * @param user The user with updated data
     * @return The updated user or null if it doesn't exist
     */
    public User updateUser(User user) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equals(user.getId())) {
                users.set(i, user);
                return user;
            }
        }
        return null;
    }

    /**
     * Deletes a user by their ID
     *
     * @param id The ID of the user to delete
     */
    public void deleteUserById(String id) {
        for (int i = users.size() - 1; i >= 0; i--) {
            if (users.get(i).getId().equals(id)) {
                users.remove(i);
                break;
            }
        }
    }

}
