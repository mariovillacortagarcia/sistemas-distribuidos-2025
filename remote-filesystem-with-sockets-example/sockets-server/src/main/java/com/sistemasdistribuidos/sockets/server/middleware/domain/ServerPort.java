package com.sistemasdistribuidos.sockets.server.middleware.domain;

/**
 * Abstract server port that listens on a socket and executes a generic use case
 * with the received data.
 * 
 * This class defines the contract for server implementations (TCP, UDP, etc.)
 * and provides common functionality through the Template Method pattern.
 * Concrete implementations must define how to start the server.
 *
 * @author mariovillacortagarcia
 */
public abstract class ServerPort {

    /**
     * Port number on which the server will listen for incoming connections.
     */
    protected final int port;

    /**
     * Use case to be executed when data is received from clients.
     */
    protected final UseCase useCase;

    /**
     * Constructs a server port with the specified configuration.
     *
     * @param port Port number to bind the server process
     * @param useCase Use case to execute when data is received from clients
     */
    protected ServerPort(int port, UseCase useCase) {
        this.port = port;
        this.useCase = useCase;
    }

    /**
     * Starts the server to listen for incoming connections and process requests.
     * 
     * This method is typically blocking and runs indefinitely until interrupted.
     */
    public abstract void start();

}
