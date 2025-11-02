/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemasdistribuidos.servlet.shopping.application.cart;

import java.util.ArrayList;
import java.util.List;

/**
 * Repository for managing ShoppingCart entities.
 * Uses ArrayList internally for dynamic size management.
 *
 * @author mariovillacortagarcia
 */
public class ShoppingCartRepository {

    private List<ShoppingCart> shoppingCart;

    /**
     * Constructs a new ShoppingCartRepository with an empty list of shopping carts.
     */
    public ShoppingCartRepository() {
        this.shoppingCart = new ArrayList<ShoppingCart>();
    }

    /**
     * Gets all stored shopping carts
     *
     * @return Array of all shopping carts
     */
    public ShoppingCart[] getShoppingCarts() {
        return shoppingCart.toArray(new ShoppingCart[0]);
    }

    /**
     * Gets a shopping cart by user ID
     *
     * @param userId The ID of the user
     * @return The found shopping cart or null if it doesn't exist
     */
    public ShoppingCart getShoppingCartByUserId(String userId) {
        for (ShoppingCart cart : shoppingCart) {
            if (cart.getUserId().equals(userId)) {
                return cart;
            }
        }
        return null;
    }

    /**
     * Creates a new shopping cart for a user
     *
     * @param userId The ID of the user
     * @return The created shopping cart
     */
    public ShoppingCart createUserShoppingCart(String userId) {
        ShoppingCart cart = new ShoppingCart(userId);
        shoppingCart.add(cart);
        return cart;
    }

    /**
     * Updates an existing shopping cart
     *
     * @param shoppingCart The shopping cart with updated data
     * @return The updated shopping cart or null if it doesn't exist
     */
    public ShoppingCart updateShoppingCart(ShoppingCart shoppingCart) {
        for (int i = 0; i < this.shoppingCart.size(); i++) {
            if (this.shoppingCart.get(i).getUserId().equals(shoppingCart.getUserId())) {
                this.shoppingCart.set(i, shoppingCart);
                return shoppingCart;
            }
        }
        return null;
    }

    /**
     * Deletes a shopping cart by user ID
     *
     * @param userId The ID of the user
     */
    public void deleteShoppingCartByUserId(String userId) {
        for (int i = shoppingCart.size() - 1; i >= 0; i--) {
            if (shoppingCart.get(i).getUserId().equals(userId)) {
                shoppingCart.remove(i);
                break;
            }
        }
    }
}

