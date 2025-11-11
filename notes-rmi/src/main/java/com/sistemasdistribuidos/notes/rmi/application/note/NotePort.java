/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sistemasdistribuidos.notes.rmi.application.note;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Remote interface (Port) for Note operations using RMI.
 * This interface defines the contract for remote note management operations.
 *
 * @author mariovillacortagarcia
 */
public interface NotePort extends Remote {

    /**
     * Gets all stored notes
     *
     * @return Array of all notes
     * @throws RemoteException if a remote communication error occurs
     */
    Note[] getNotes() throws RemoteException;

    /**
     * Gets a note by its ID
     *
     * @param id The ID of the note to find
     * @return The found note or null if it doesn't exist
     * @throws RemoteException if a remote communication error occurs
     */
    Note getNoteById(String id) throws RemoteException;

    /**
     * Creates a new note with the specified text
     *
     * @param text The text content of the note
     * @return The created note
     * @throws RemoteException if a remote communication error occurs
     */
    Note createNote(String text) throws RemoteException;

    /**
     * Updates an existing note
     *
     * @param note The note with updated data
     * @return The updated note or null if it doesn't exist
     * @throws RemoteException if a remote communication error occurs
     */
    Note updateNote(Note note) throws RemoteException;

    /**
     * Deletes a note by its ID
     *
     * @param id The ID of the note to delete
     * @throws RemoteException if a remote communication error occurs
     */
    void deleteNoteById(String id) throws RemoteException;
}

