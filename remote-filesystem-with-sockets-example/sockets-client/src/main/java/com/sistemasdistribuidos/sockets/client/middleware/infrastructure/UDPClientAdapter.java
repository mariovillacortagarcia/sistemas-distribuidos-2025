package com.sistemasdistribuidos.sockets.client.middleware.infrastructure;

import com.sistemasdistribuidos.sockets.client.middleware.domain.ClientPort;
import com.sistemasdistribuidos.sockets.client.middleware.domain.UseCase;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;

/**
 * UDP client adapter that implements connectionless socket communication.
 * 
 * This adapter uses UDP protocol to send datagrams to the server without
 * establishing a dedicated connection. Each packet is independent and
 * self-contained.
 * 
 * Key characteristics:
 * - Connectionless: No dedicated connection with server
 * - Unreliable: No guarantee of delivery or ordering
 * - Datagram-based: Data is sent in discrete packets
 * - Lightweight: Lower overhead than TCP
 *
 * @author mariovillacortagarcia
 */
public class UDPClientAdapter extends ClientPort {

    /**
     * Constructs a UDP client adapter with the specified configuration.
     * 
     * @param host Hostname or IP address of the server
     * @param port Port number to send datagrams to
     * @param useCase Use case to execute to retrieve data
     */
    public UDPClientAdapter(String host, int port, UseCase useCase) {
        super(host, port, useCase);
    }

    /**
     * Starts the UDP client to send data to the server.
     * 
     * This method performs the following steps:
     * 1. Executes the use case to retrieve data
     * 2. Creates a datagram socket
     * 3. Converts data to bytes
     * 4. Sends the datagram packet to the server
     * 5. Closes the socket
     */
    @Override
    public void start() {
        System.out.printf("[UDP] Preparing to send to %s:%d%n", host, port);
        
        try (DatagramSocket socket = new DatagramSocket()) {
            
            String data = useCase.execute();
            byte[] buffer = data.getBytes(StandardCharsets.UTF_8);
            
            InetAddress address = InetAddress.getByName(host);
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, port);
            
            socket.send(packet);
            
            System.out.printf("[UDP] Sent %d bytes to %s:%d%n", buffer.length, host, port);
            System.out.printf("[UDP] Data: %s%n", data);
            
        } catch (SocketException e) {
            System.err.println("[UDP] Socket error: " + e.getMessage());
        } catch (UnknownHostException e) {
            System.err.println("[UDP] Unknown host: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("[UDP] IO error: " + e.getMessage());
        }
    }

}

