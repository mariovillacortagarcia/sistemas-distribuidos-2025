package com.sistemasdistribuidos.sockets.server.middleware.infrastructure;

import com.sistemasdistribuidos.sockets.server.middleware.domain.ServerPort;
import com.sistemasdistribuidos.sockets.server.middleware.domain.UseCase;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;

/**
 * UDP server adapter that implements connectionless socket communication.
 *
 * This adapter uses UDP protocol to receive datagrams from clients without
 * establishing a dedicated connection. Each packet is independent and
 * self-contained.
 *
 * Key characteristics: - Connectionless: No dedicated connection per client -
 * Unreliable: No guarantee of delivery or ordering - Datagram-based: Data is
 * received in discrete packets - Lightweight: Lower overhead than TCP
 *
 * @author mariovillacortagarcia
 */
public class UDPServerAdapter extends ServerPort {

    /**
     * Maximum size of the buffer for receiving UDP packets.
     */
    private static final int BUFFER_SIZE = 1024;

    /**
     * The datagram socket used to receive UDP packets.
     */
    private final DatagramSocket socket;

    /**
     * Constructs a UDP server adapter bound to the specified port.
     *
     * @param port Port number to bind the server
     * @param useCase Use case to execute when data is received
     * @throws SocketException If the socket cannot be created or bound
     */
    public UDPServerAdapter(int port, UseCase useCase) throws SocketException {
        super(port, useCase);
        socket = new DatagramSocket(port);
    }

    /**
     * Starts the UDP server in an infinite loop to receive and process
     * datagrams.
     *
     * This method blocks indefinitely and performs the following steps: 1.
     * Creates a buffer for receiving data 2. Waits for incoming UDP packets 3.
     * Converts packet data to string 4. Executes the use case with the received
     * data 5. Returns to step 2
     */
    @Override
    public void start() {
        System.out.println("[TCP] Server listening on port " + port);
        byte[] buffer = new byte[BUFFER_SIZE];
        while (true) {
            try {

                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);
                System.out.println("[UDP] Received packet: " + packet.getSocketAddress() + ":" + packet.getPort() + " " + packet.getLength());

                String content = new String(packet.getData(), 0, packet.getLength(), StandardCharsets.UTF_8);
                useCase.execute(content);

            } catch (IOException e) {
                System.out.println("[UDP] Error receiving packet: " + e.getMessage());
            }
        }

    }

}
