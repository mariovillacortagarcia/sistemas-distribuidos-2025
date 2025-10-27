/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemasdistribuidos.sockets.server.app.domain;

/**
 * Domain entity representing a file with its content.
 * 
 * This is a simple value object that encapsulates file content
 * and provides immutability through final fields.
 * 
 * @author mariovillacortagarcia
 */
public class File {

    /**
     * Content of the file as a string.
     */
    private final String content;

    /**
     * Constructs a new File with the given content.
     * 
     * @param content The content of the file
     */
    public File(String content) {
        this.content = content;
    }

    /**
     * Returns the content of the file.
     * 
     * @return The file content as a string
     */
    public String getContent() {
        return this.content;
    }
}
