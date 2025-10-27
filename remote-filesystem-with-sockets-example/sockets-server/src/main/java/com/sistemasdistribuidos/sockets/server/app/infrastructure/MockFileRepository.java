/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemasdistribuidos.sockets.server.app.infrastructure;

import com.sistemasdistribuidos.sockets.server.app.domain.File;
import com.sistemasdistribuidos.sockets.server.app.domain.FilePort;

/**
 * Mock implementation of FilePort for testing and demonstration purposes.
 * 
 * Instead of actually persisting files to disk, this adapter simply
 * prints the file content to the console. This is useful for development
 * and testing without requiring file system access.
 * 
 * @author mariovillacortagarcia
 */
public class MockFileRepository implements FilePort {

    /**
     * Simulates saving a file by printing its content to the console.
     * 
     * @param file The file to be saved
     * @param src The source path (ignored in this mock implementation)
     */
    @Override
    public void saveFileToSrc(File file, String src) {
        System.out.println(file.getContent());
    }

}
