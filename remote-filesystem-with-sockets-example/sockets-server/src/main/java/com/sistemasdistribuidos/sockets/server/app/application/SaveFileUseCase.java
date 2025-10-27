package com.sistemasdistribuidos.sockets.server.app.application;

import com.sistemasdistribuidos.sockets.server.app.domain.File;
import com.sistemasdistribuidos.sockets.server.app.domain.FilePort;
import com.sistemasdistribuidos.sockets.server.middleware.domain.UseCase;

/**
 * Use case for saving files received through the server.
 * 
 * This use case implements the UseCase interface from the middleware
 * and coordinates the business logic to persist files through the FilePort.
 * 
 * Execution flow:
 * 1. Receives content as String from the middleware
 * 2. Creates a File entity with the content
 * 3. Delegates persistence to the infrastructure port
 * 
 * @author mariovillacortagarcia
 */
public class SaveFileUseCase implements UseCase {

    /**
     * Output port for persisting files.
     */
    private final FilePort filePort;

    /**
     * Constructor that injects the necessary dependencies.
     * 
     * @param filePort Infrastructure port for saving files
     */
    public SaveFileUseCase(FilePort filePort) {
        this.filePort = filePort;
    }

    /**
     * Executes the file saving use case.
     * 
     * Converts the received content into a domain entity
     * and persists it in the "data" directory through the port.
     * 
     * @param content Content of the file received from the client
     */
    @Override
    public void execute(String content) {
        final File file = new File(content);
        filePort.saveFileToSrc(file, "data");
    }

}
