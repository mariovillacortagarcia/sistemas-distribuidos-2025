package com.sistemasdistribuidos.sockets.server.middleware.domain;

/**
 * Represents a use case that can be executed by the server retrieved request
 * over the socket middleware.
 *
 * Each implementation encapsulates a unit of application logic.
 *
 * @author mariovillacortagarcia
 */
public interface UseCase {

    /**
     * Executes this use case.
     *
     * @param data Received data by the socket API
     */
    public void execute(String data);
}
