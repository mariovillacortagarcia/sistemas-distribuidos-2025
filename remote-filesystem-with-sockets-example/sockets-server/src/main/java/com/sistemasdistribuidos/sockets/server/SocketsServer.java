/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.sistemasdistribuidos.sockets.server;

import com.sistemasdistribuidos.sockets.server.app.application.SaveFileUseCase;
import com.sistemasdistribuidos.sockets.server.app.domain.FilePort;
import com.sistemasdistribuidos.sockets.server.app.infrastructure.MockFileRepository;
import com.sistemasdistribuidos.sockets.server.middleware.domain.ServerPort;
import com.sistemasdistribuidos.sockets.server.middleware.domain.UseCase;
import com.sistemasdistribuidos.sockets.server.middleware.infrastructure.TCPServerAdapter;
import java.io.IOException;

/**
 * Main application for the socket server.
 *
 * This class configures and starts a socket-based server (TCP or UDP) that
 * receives data from clients and processes it through defined use cases.
 *
 * The architecture implements the Ports and Adapters pattern (Hexagonal
 * Architecture): - App Layer: Contains business logic (use cases and domain) -
 * Middleware Layer: Handles socket communication
 *
 * @author mariovillacortagarcia
 */
public class SocketsServer {

    /**
     * Port on which the server will listen for incoming connections.
     */
    private static final int PORT = 55001;

    /**
     * Main method that starts the socket server.
     *
     * Configures dependencies through manual injection: 1. Creates a repository
     * to persist files 2. Initializes the save file use case 3. Configures the
     * server adapter (TCP or UDP) 4. Starts the server on the specified port
     *
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        try {
            final FilePort filePort = new MockFileRepository();
            final UseCase saveFileUseCase = new SaveFileUseCase(filePort);

            final ServerPort server = new TCPServerAdapter(PORT, saveFileUseCase);
            // final ServerPort server = new UDPServerAdapter(PORT, saveFileUseCase);

            server.start();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
