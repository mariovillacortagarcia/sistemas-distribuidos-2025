/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sistemasdistribuidos.sockets.server.app.domain;

/**
 * Output port for file persistence operations.
 * 
 * This interface defines the contract for file storage implementations,
 * following the Dependency Inversion Principle. The domain layer defines
 * what it needs, and infrastructure provides the implementation.
 * 
 * @author mariovillacortagarcia
 */
public interface FilePort {

    /**
     * Saves a file to the specified source location.
     * 
     * @param file The file entity to be saved
     * @param src The source or destination path for the file
     */
    public void saveFileToSrc(File file, String src);
}
