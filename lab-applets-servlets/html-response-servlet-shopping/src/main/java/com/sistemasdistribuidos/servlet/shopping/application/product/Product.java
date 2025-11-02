/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemasdistribuidos.servlet.shopping.application.product;

import com.sistemasdistribuidos.servlet.shopping.application.common.JsonSerializable;
import com.sistemasdistribuidos.servlet.shopping.application.common.JsonUtils;

/**
 * Represents a product in the shopping system.
 * A product has a name and a brand identifier.
 *
 * @author mariovillacortagarcia
 */
public class Product implements JsonSerializable {

    private String name;
    private String brand;

    /**
     * Constructs a new Product with the specified name and brand.
     *
     * @param name The name of the product
     * @param brand The brand of the product
     */
    public Product(String name, String brand) {
        this.name = name;
        this.brand = brand;
    }

    /**
     * Gets the name of the product.
     *
     * @return The product name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the brand of the product.
     *
     * @return The product brand
     */
    public String getBrand() {
        return brand;
    }

    /**
     * Converts this Product object to JSON format
     *
     * @return JSON string representation of this product
     */
    public String toJson() {
        return "{\"name\":\"" + JsonUtils.escapeJson(this.name) + "\",\"brand\":\"" + JsonUtils.escapeJson(this.brand) + "\"}";
    }
}

