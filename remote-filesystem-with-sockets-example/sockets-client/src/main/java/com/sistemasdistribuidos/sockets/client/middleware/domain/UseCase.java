package com.sistemasdistribuidos.sockets.client.middleware.domain;

/**
 * Represents a use case that can be executed by the client before sending data
 * over the socket middleware.
 * 
 * Each implementation encapsulates a unit of application logic that retrieves
 * or generates data to be sent to the server.
 *
 * @author mariovillacortagarcia
 */
public interface UseCase {

    /**
     * Executes this use case and returns the data to be sent.
     *
     * @return The data to be sent to the server via socket
     */
    public String execute();
}

