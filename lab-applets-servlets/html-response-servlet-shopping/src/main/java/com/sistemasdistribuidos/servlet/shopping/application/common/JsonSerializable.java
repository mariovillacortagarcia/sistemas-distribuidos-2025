/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sistemasdistribuidos.servlet.shopping.application.common;

/**
 * Interface for objects that can be serialized to JSON format.
 * Classes implementing this interface must provide a method to convert
 * themselves to a JSON string representation.
 *
 * @author mariovillacortagarcia
 */
public interface JsonSerializable {

    /**
     * Converts this object to JSON format
     *
     * @return JSON string representation of this object
     */
    String toJson();
}

