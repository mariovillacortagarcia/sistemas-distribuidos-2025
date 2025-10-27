/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemasdistribuidos.sockets.server.middleware.infrastructure;

import com.sistemasdistribuidos.sockets.server.middleware.domain.ServerPort;
import com.sistemasdistribuidos.sockets.server.middleware.domain.UseCase;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * TCP server adapter that implements connection-oriented socket communication.
 *
 * This adapter uses TCP/IP protocol to establish reliable, stream-based
 * connections with clients. It implements an iterative server model where each
 * client is handled sequentially.
 *
 * Key characteristics: - Connection-oriented: Establishes dedicated connection
 * per client - Reliable: Guarantees data delivery and ordering - Stream-based:
 * Data is read as a continuous stream - Iterative: Handles one client at a time
 * (not concurrent)
 *
 * @author mariovillacortagarcia
 */
public class TCPServerAdapter extends ServerPort {

    /**
     * The server socket that listens for incoming TCP connections.
     */
    private final ServerSocket serverSocket;

    /**
     * Constructs a TCP server adapter bound to the specified port.
     *
     * @param port Port number to bind the server
     * @param useCase Use case to execute when data is received
     * @throws IOException If the server socket cannot be created or bound
     */
    public TCPServerAdapter(int port, UseCase useCase) throws IOException {
        super(port, useCase);
        serverSocket = new ServerSocket(this.port);
        System.out.println(serverSocket);
    }

    /**
     * Starts the TCP server in an infinite loop to accept and handle client
     * connections.
     *
     * This method blocks indefinitely and performs the following steps: 1.
     * Waits for incoming client connections 2. Accepts the connection and
     * creates a data socket 3. Reads data from the client 4. Executes the use
     * case with the received data 5. Returns to step 1
     */
    @Override
    public void start() {
        System.out.println("[TCP] Server listening on port " + port);
        while (true) {

            // Iterative server
            try {
                // 1. Connection socket acceptance
                Socket dataSocket = serverSocket.accept();
                System.out.println(
                        "[TCP] Connected data socket from "
                        + dataSocket.getInetAddress().getHostAddress() + ":"
                        + dataSocket.getPort()
                );

                // 2. Data socket stream
                receiveDataAndExecuteUseCase(dataSocket);

            } catch (IOException e) {
                System.err.println("[TCP] Error accepting/handling client: " + e.getMessage());
            }
        }
    }

    /**
     * Receives data from the connected client and executes the use case for
     * each line.
     *
     * This method reads the input stream line by line until the client closes
     * the connection (indicated by null). Each line is logged and processed
     * through the configured use case.
     *
     * @param dataSocket The socket connected to the client
     */
    private void receiveDataAndExecuteUseCase(Socket dataSocket) {
        try (Socket socket = dataSocket; BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            String data;
            while ((data = in.readLine()) != null) {
                System.out.printf(
                        "[TCP] %s:%d -> %s%n",
                        socket.getInetAddress().getHostAddress(),
                        socket.getPort(),
                        data
                );

                useCase.execute(data);
            }

            System.out.println(
                    "[TCP] Client "
                    + socket.getInetAddress().getHostAddress() + ":"
                    + socket.getPort()
                    + " disconnected"
            );

        } catch (IOException e) {
            System.err.println("[TCP] Error reading from client: " + e.getMessage());
        }
    }

}
