package com.sistemasdistribuidos.sockets.client.app.application;

import com.sistemasdistribuidos.sockets.client.app.domain.File;
import com.sistemasdistribuidos.sockets.client.app.domain.FilePort;
import com.sistemasdistribuidos.sockets.client.middleware.domain.UseCase;

/**
 * Use case for retrieving and sending files to the server.
 * 
 * This use case implements the UseCase interface from the middleware
 * and coordinates the business logic to retrieve files through the FilePort
 * for transmission to the server.
 * 
 * Execution flow:
 * 1. Retrieves a file from the specified source through the port
 * 2. Extracts the file content
 * 3. Returns the content to be sent by the middleware
 * 
 * @author mariovillacortagarcia
 */
public class SendFileUseCase implements UseCase {

    /**
     * Input port for retrieving files.
     */
    private final FilePort filePort;
    
    /**
     * Source identifier for the file to retrieve.
     */
    private final String src;

    /**
     * Constructor that injects the necessary dependencies.
     * 
     * @param filePort Infrastructure port for retrieving files
     * @param src Source identifier for the file
     */
    public SendFileUseCase(FilePort filePort, String src) {
        this.filePort = filePort;
        this.src = src;
    }

    /**
     * Executes the file sending use case.
     * 
     * Retrieves the file from the specified source through the port
     * and returns its content to be sent to the server.
     * 
     * @return The content of the file to be sent
     */
    @Override
    public String execute() {
        File file = filePort.getFileFromSrc(src);
        return file.getContent();
    }

}

