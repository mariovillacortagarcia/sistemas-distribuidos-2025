/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemasdistribuidos.servlet.shopping.application.cart;

import java.util.ArrayList;
import java.util.List;

import com.sistemasdistribuidos.servlet.shopping.application.common.JsonSerializable;
import com.sistemasdistribuidos.servlet.shopping.application.common.JsonUtils;
import com.sistemasdistribuidos.servlet.shopping.application.product.Product;

/**
 * Represents a shopping cart associated with a specific user.
 * A shopping cart contains a collection of products that the user wants to purchase.
 * Uses ArrayList internally for dynamic size management.
 *
 * @author mariovillacortagarcia
 */
public class ShoppingCart implements JsonSerializable {

    private String userId;
    private List<Product> products;

    /**
     * Constructs a new ShoppingCart for the specified user.
     * The cart is initially empty.
     *
     * @param userId The ID of the user who owns this cart
     */
    public ShoppingCart(String userId) {
        this.userId = userId;
        this.products = new ArrayList<Product>();
    }

    /**
     * Gets the ID of the user who owns this shopping cart.
     *
     * @return The user ID
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Gets all products in the shopping cart.
     *
     * @return An array containing all products in the cart
     */
    public Product[] getProducts() {
        return products.toArray(new Product[0]);
    }

    /**
     * Adds a product to the shopping cart.
     *
     * @param product The product to add to the cart
     */
    public void addProduct(Product product) {
        products.add(product);
    }

    /**
     * Removes a product from the shopping cart.
     * The product is identified by matching both name and brand.
     * If multiple products with the same name and brand exist, only the first one is removed.
     *
     * @param product The product to remove from the cart
     */
    public void removeProduct(Product product) {
        for (int i = products.size() - 1; i >= 0; i--) {
            Product p = products.get(i);
            if (p.getName().equals(product.getName()) 
                    && p.getBrand().equals(product.getBrand())) {
                products.remove(i);
                break;
            }
        }
    }

    /**
     * Converts this ShoppingCart object to JSON format
     *
     * @return JSON string representation of this shopping cart
     */
    @Override
    public String toJson() {
        StringBuilder json = new StringBuilder("{\"userId\":\"" + JsonUtils.escapeJson(this.userId) + "\",\"products\":[");
        Product[] productsArray = getProducts();
        for (int i = 0; i < productsArray.length; i++) {
            if (i > 0) {
                json.append(",");
            }
            json.append(productsArray[i].toJson());
        }
        json.append("]}");
        return json.toString();
    }
}

