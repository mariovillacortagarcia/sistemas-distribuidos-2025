/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.sistemasdistribuidos.sockets.client;

import com.sistemasdistribuidos.sockets.client.app.application.SendFileUseCase;
import com.sistemasdistribuidos.sockets.client.app.domain.FilePort;
import com.sistemasdistribuidos.sockets.client.app.infrastructure.ConsoleFileRepository;
import com.sistemasdistribuidos.sockets.client.middleware.domain.ClientPort;
import com.sistemasdistribuidos.sockets.client.middleware.domain.UseCase;
import com.sistemasdistribuidos.sockets.client.middleware.infrastructure.TCPClientAdapter;

/**
 * Main application for the socket client.
 *
 * This class configures and starts a socket-based client (TCP or UDP) that
 * sends data to a server through use cases that retrieve the data.
 *
 * The architecture implements the Ports and Adapters pattern (Hexagonal
 * Architecture): - App Layer: Contains business logic (use cases and domain) -
 * Middleware Layer: Handles socket communication
 *
 * @author mariovillacortagarcia
 */
public class SocketsClient {

    /**
     * Server hostname to connect to.
     */
    private static final String HOST = "localhost";

    /**
     * Server port to connect to.
     */
    private static final int PORT = 1234;

    /**
     * Main method that starts the socket client.
     *
     * Configures dependencies through manual injection: 1. Creates a repository
     * to retrieve file content 2. Initializes the send file use case 3.
     * Configures the client adapter (TCP or UDP) 4. Starts the client to
     * connect and send data
     *
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        final FilePort filePort = new ConsoleFileRepository();
        final UseCase sendFileUseCase = new SendFileUseCase(filePort, "message");

        final ClientPort client = new TCPClientAdapter(HOST, PORT, sendFileUseCase);
        // final ClientPort client = new UDPClientAdapter(HOST, PORT, sendFileUseCase);

        client.start();
    }
}
