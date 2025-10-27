package com.sistemasdistribuidos.sockets.client.app.domain;

/**
 * Input port for file retrieval operations.
 * 
 * This interface defines the contract for file reading implementations,
 * following the Dependency Inversion Principle. The domain layer defines
 * what it needs, and infrastructure provides the implementation.
 * 
 * @author mariovillacortagarcia
 */
public interface FilePort {

    /**
     * Retrieves a file from the specified source location.
     * 
     * @param src The source path or identifier for the file
     * @return The file entity with its content
     */
    public File getFileFromSrc(String src);
}

