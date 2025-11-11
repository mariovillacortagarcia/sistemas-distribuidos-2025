/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemasdistribuidos.notes.rmi.client;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import com.sistemasdistribuidos.notes.rmi.application.common.JsonUtils;
import com.sistemasdistribuidos.notes.rmi.application.note.Note;
import com.sistemasdistribuidos.notes.rmi.application.note.NotePort;

/**
 * RMI Client that connects to the NoteRepository and performs operations.
 * This client demonstrates how to use the remote NotePort.
 *
 * @author mariovillacortagarcia
 */
public class NoteRmiClient {

    private static final String SERVICE_NAME = "NotePort";
    private static final String HOST = "localhost";
    private static final int REGISTRY_PORT = 1099;

    public static void main(String[] args) {
        try {
            Registry registry = connectToRegistry();
            NotePort noteRepository = lookupNoteRepository(registry);
            demonstrateNoteOperations(noteRepository);
        } catch (Exception e) {
            handleClientException(e);
        }
    }

    private static Registry connectToRegistry() throws Exception {
        Registry registry = LocateRegistry.getRegistry(HOST, REGISTRY_PORT);
        System.out.println("Connected to RMI registry at " + HOST + ":" + REGISTRY_PORT);
        return registry;
    }

    private static NotePort lookupNoteRepository(Registry registry) throws Exception {
        NotePort noteRepository = (NotePort) registry.lookup(SERVICE_NAME);
        System.out.println("NoteRepository found in registry");
        return noteRepository;
    }

    private static void demonstrateNoteOperations(NotePort noteRepository) throws Exception {
        Note[] createdNotes = createMultipleNotes(noteRepository);
        Note note1 = createdNotes[0];
        Note note2 = createdNotes[1];

        printAllNotes(noteRepository);
        printNoteById(noteRepository, note1.getId());
        updateAndPrintNote(noteRepository, note1, "This is my updated first note");
        printAllNotes(noteRepository);
        deleteAndPrintNote(noteRepository, note2);
        printAllNotes(noteRepository);

        System.out.println("\n=== Client operations completed successfully ===");
    }

    private static Note[] createMultipleNotes(NotePort noteRepository) throws Exception {
        System.out.println("\n=== Creating notes ===");
        Note note1 = createAndPrintNote(noteRepository, "This is my first note");
        Note note2 = createAndPrintNote(noteRepository, "This is my second note");
        Note note3 = createAndPrintNote(noteRepository, "This is my third note");
        return new Note[] { note1, note2, note3 };
    }

    private static Note createAndPrintNote(NotePort noteRepository, String text) throws Exception {
        Note note = noteRepository.createNote(text);
        System.out.println("Created: " + JsonUtils.toJson(note));
        return note;
    }

    private static void printAllNotes(NotePort noteRepository) throws Exception {
        System.out.println("\n=== Getting all notes ===");
        Note[] allNotes = noteRepository.getNotes();
        System.out.println("All notes: " + JsonUtils.toJsonArray(allNotes));
    }

    private static void printNoteById(NotePort noteRepository, String id) throws Exception {
        System.out.println("\n=== Getting note by ID ===");
        Note foundNote = noteRepository.getNoteById(id);
        System.out.println("Found note: " + JsonUtils.toJson(foundNote));
    }

    private static void updateAndPrintNote(NotePort noteRepository, Note note, String newText) throws Exception {
        System.out.println("\n=== Updating note ===");
        note.setText(newText);
        Note updatedNote = noteRepository.updateNote(note);
        System.out.println("Updated: " + JsonUtils.toJson(updatedNote));
    }

    private static void deleteAndPrintNote(NotePort noteRepository, Note note) throws Exception {
        System.out.println("\n=== Deleting note ===");
        noteRepository.deleteNoteById(note.getId());
        System.out.println("Deleted note with ID: " + note.getId());
    }

    private static void handleClientException(Exception e) {
        System.err.println("Client exception: " + e.toString());
        e.printStackTrace();
    }
}

