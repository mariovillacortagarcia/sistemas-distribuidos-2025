/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemasdistribuidos.notes.rmi.application.note;

import com.sistemasdistribuidos.notes.rmi.application.common.JsonSerializable;
import com.sistemasdistribuidos.notes.rmi.application.common.JsonUtils;
import com.sistemasdistribuidos.notes.rmi.application.common.RandomIdGenerator;

/**
 * Represents a note in the notes system.
 * Each note has a unique ID (automatically generated) and text content.
 *
 * @author mariovillacortagarcia
 */
public class Note implements JsonSerializable {

    private final String id;
    private String text;

    /**
     * Constructs a new Note with the specified text.
     * A unique ID is automatically generated for the note.
     *
     * @param text The text content of the note
     */
    public Note(String text) {
        id = RandomIdGenerator.generate();
        this.text = text;
    }

    /**
     * Sets the text of the note.
     *
     * @param text The new text for the note
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Gets the text of the note.
     *
     * @return The note's text
     */
    public String getText() {
        return text;
    }

    /**
     * Gets the unique ID of the note.
     *
     * @return The note's ID
     */
    public String getId() {
        return id;
    }

    /**
     * Converts this Note object to JSON format
     *
     * @return JSON string representation of this note
     */
    public String toJson() {
        return "{\"id\":\"" + JsonUtils.escapeJson(this.id) + "\",\"text\":\"" + JsonUtils.escapeJson(this.text)
                + "\"}";
    }
}

