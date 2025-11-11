/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemasdistribuidos.notes.rmi.server;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import com.sistemasdistribuidos.notes.rmi.application.note.NotePort;
import com.sistemasdistribuidos.notes.rmi.middleware.NoteRepository;

/**
 * RMI Server that registers and exposes the NoteRepository.
 * This server creates a NoteRepository instance and registers it in the RMI
 * registry.
 *
 * @author mariovillacortagarcia
 */
public class NoteRmiServer {

    private static final String SERVICE_NAME = "NotePort";
    private static final int REGISTRY_PORT = 1099;

    public static void main(String[] args) {
        try {
            Registry registry = getOrCreateRegistry();
            NotePort noteRepository = createNoteRepository();
            bindRepositoryToRegistry(registry, noteRepository);
            printServerReadyMessage();
        } catch (Exception e) {
            handleServerException(e);
        }
    }

    private static Registry getOrCreateRegistry() throws Exception {
        try {
            Registry registry = LocateRegistry.createRegistry(REGISTRY_PORT);
            System.out.println("RMI Registry created on port " + REGISTRY_PORT);
            return registry;
        } catch (Exception e) {
            Registry registry = LocateRegistry.getRegistry(REGISTRY_PORT);
            System.out.println("RMI Registry found on port " + REGISTRY_PORT);
            return registry;
        }
    }

    private static NotePort createNoteRepository() throws Exception {
        NotePort noteRepository = new NoteRepository();
        System.out.println("NoteRepository implementation created");
        return noteRepository;
    }

    private static void bindRepositoryToRegistry(Registry registry, NotePort noteRepository) throws Exception {
        registry.rebind(SERVICE_NAME, noteRepository);
        System.out.println("NoteRepository bound to registry with name: " + SERVICE_NAME);
    }

    private static void printServerReadyMessage() {
        System.out.println("Server is ready and waiting for client requests...");
        System.out.println("Press Ctrl+C to stop the server");
    }

    private static void handleServerException(Exception e) {
        System.err.println("Server exception: " + e.toString());
        e.printStackTrace();
    }
}
