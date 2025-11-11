/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemasdistribuidos.notes.rmi.middleware;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import com.sistemasdistribuidos.notes.rmi.application.note.Note;
import com.sistemasdistribuidos.notes.rmi.application.note.NotePort;

/**
 * Implementation of NotePort using RMI.
 * This class manages Note entities and exposes them as a remote service.
 *
 * @author mariovillacortagarcia
 */
public class NoteRepository extends UnicastRemoteObject implements NotePort {

    private List<Note> notes;

    /**
     * Constructs a new NoteRepository with an empty list of notes.
     *
     * @throws RemoteException if a remote communication error occurs
     */
    public NoteRepository() throws RemoteException {
        super();
        this.notes = new ArrayList<Note>();
    }

    /**
     * Gets all stored notes
     *
     * @return Array of all notes
     * @throws RemoteException if a remote communication error occurs
     */
    @Override
    public Note[] getNotes() throws RemoteException {
        return notes.toArray(new Note[0]);
    }

    /**
     * Gets a note by its ID
     *
     * @param id The ID of the note to find
     * @return The found note or null if it doesn't exist
     * @throws RemoteException if a remote communication error occurs
     */
    @Override
    public Note getNoteById(String id) throws RemoteException {
        for (Note note : notes) {
            if (note.getId().equals(id)) {
                return note;
            }
        }
        return null;
    }

    /**
     * Creates a new note with the specified text
     *
     * @param text The text content of the note
     * @return The created note
     * @throws RemoteException if a remote communication error occurs
     */
    @Override
    public Note createNote(String text) throws RemoteException {
        Note note = new Note(text);
        notes.add(note);
        return note;
    }

    /**
     * Updates an existing note
     *
     * @param note The note with updated data
     * @return The updated note or null if it doesn't exist
     * @throws RemoteException if a remote communication error occurs
     */
    @Override
    public Note updateNote(Note note) throws RemoteException {
        for (int i = 0; i < notes.size(); i++) {
            if (notes.get(i).getId().equals(note.getId())) {
                notes.set(i, note);
                return note;
            }
        }
        return null;
    }

    /**
     * Deletes a note by its ID
     *
     * @param id The ID of the note to delete
     * @throws RemoteException if a remote communication error occurs
     */
    @Override
    public void deleteNoteById(String id) throws RemoteException {
        for (int i = notes.size() - 1; i >= 0; i--) {
            if (notes.get(i).getId().equals(id)) {
                notes.remove(i);
                break;
            }
        }
    }
}

