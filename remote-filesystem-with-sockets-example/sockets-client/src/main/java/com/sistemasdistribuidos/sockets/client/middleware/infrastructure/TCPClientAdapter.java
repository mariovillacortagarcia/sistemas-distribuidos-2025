package com.sistemasdistribuidos.sockets.client.middleware.infrastructure;

import com.sistemasdistribuidos.sockets.client.middleware.domain.ClientPort;
import com.sistemasdistribuidos.sockets.client.middleware.domain.UseCase;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 * TCP client adapter that implements connection-oriented socket communication.
 *
 * This adapter uses TCP/IP protocol to establish a reliable, stream-based
 * connection with the server and send data through it.
 *
 * Key characteristics: - Connection-oriented: Establishes dedicated connection
 * with server - Reliable: Guarantees data delivery and ordering - Stream-based:
 * Data is sent as a continuous stream - Blocking: Waits until connection is
 * established
 *
 * @author mariovillacortagarcia
 */
public class TCPClientAdapter extends ClientPort {

    /**
     * Constructs a TCP client adapter with the specified configuration.
     *
     * @param host Hostname or IP address of the server
     * @param port Port number to connect to
     * @param useCase Use case to execute to retrieve data
     */
    public TCPClientAdapter(String host, int port, UseCase useCase) {
        super(host, port, useCase);
    }

    /**
     * Starts the TCP client to connect to the server and send data.
     *
     * This method performs the following steps: 1. Executes the use case to
     * retrieve data 2. Establishes a TCP connection to the server 3. Sends the
     * data through the output stream 4. Closes the connection
     */
    @Override
    public void start() {
        String data = useCase.execute();

        System.out.printf("[TCP] Connecting to %s:%d%n", host, port);
        try (Socket socket = new Socket(host, port); BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {

            System.out.printf("[TCP] Connected to %s:%d%n",
                    socket.getInetAddress().getHostAddress(),
                    socket.getPort());

            out.write(data);
            out.newLine();
            out.flush();

            System.out.printf("[TCP] Sent: %s%n", data);
            System.out.println("[TCP] Connection closed");

        } catch (IOException e) {
            System.err.println("[TCP] Error: " + e.getMessage());
        }
    }

}
