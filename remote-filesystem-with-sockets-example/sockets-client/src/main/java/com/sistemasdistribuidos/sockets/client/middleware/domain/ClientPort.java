package com.sistemasdistribuidos.sockets.client.middleware.domain;

/**
 * Abstract client port that connects to a server socket and sends data
 * retrieved from a use case.
 * 
 * This class defines the contract for client implementations (TCP, UDP, etc.)
 * and provides common functionality through the Template Method pattern.
 * Concrete implementations must define how to connect and send data.
 *
 * @author mariovillacortagarcia
 */
public abstract class ClientPort {

    /**
     * Hostname or IP address of the server to connect to.
     */
    protected final String host;
    
    /**
     * Port number on which the server is listening.
     */
    protected final int port;

    /**
     * Use case to be executed to retrieve data to send to the server.
     */
    protected final UseCase useCase;

    /**
     * Constructs a client port with the specified configuration.
     *
     * @param host Hostname or IP address of the server
     * @param port Port number to connect to
     * @param useCase Use case to execute to retrieve data
     */
    protected ClientPort(String host, int port, UseCase useCase) {
        this.host = host;
        this.port = port;
        this.useCase = useCase;
    }

    /**
     * Starts the client to connect to the server and send data.
     * 
     * This method executes the use case, connects to the server,
     * and sends the retrieved data.
     */
    public abstract void start();

}

