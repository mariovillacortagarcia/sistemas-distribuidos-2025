/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemasdistribuidos.notes.rmi.application.common;

/**
 * Utility class for JSON transformation operations.
 * Provides static methods to convert domain objects to JSON format.
 *
 * @author mariovillacortagarcia
 */
public class JsonUtils {

    /**
     * Converts a JsonSerializable object to JSON format
     *
     * @param obj The object to convert
     * @return JSON string representation of the object
     */
    public static String toJson(JsonSerializable obj) {
        return obj.toJson();
    }

    /**
     * Converts an array of JsonSerializable objects to JSON format
     *
     * @param array The array to convert
     * @return JSON string representation of the array
     */
    public static String toJsonArray(JsonSerializable[] array) {
        StringBuilder json = new StringBuilder("[");
        for (int i = 0; i < array.length; i++) {
            if (i > 0) {
                json.append(",");
            }
            json.append(toJson(array[i]));
        }
        json.append("]");
        return json.toString();
    }

    /**
     * Escapes special characters in a string for JSON
     *
     * @param str The string to escape
     * @return Escaped string safe for JSON
     */
    public static String escapeJson(String str) {
        if (str == null) {
            return "";
        }
        return str.replace("\\", "\\\\")
                  .replace("\"", "\\\"")
                  .replace("\b", "\\b")
                  .replace("\f", "\\f")
                  .replace("\n", "\\n")
                  .replace("\r", "\\r")
                  .replace("\t", "\\t");
    }
}

