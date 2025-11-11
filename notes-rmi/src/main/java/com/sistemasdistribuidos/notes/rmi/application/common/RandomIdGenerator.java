package com.sistemasdistribuidos.notes.rmi.application.common;

/**
 * Utility class for generating random alphanumeric IDs.
 * Generates IDs of a fixed length using a combination of numbers,
 * uppercase and lowercase letters.
 *
 * @author mariovillacortagarcia
 */
public class RandomIdGenerator {

    /**
     * The length of the generated ID.
     */
    private static final int LENGTH = 18;

    /**
     * Generates a random alphanumeric ID of fixed length.
     * The ID contains a random combination of numbers (0-9),
     * uppercase letters (A-Z), and lowercase letters (a-z).
     *
     * @return A randomly generated ID string
     */
    public static String generate() {
        String id = "";
        for (int i = 0; i < LENGTH; i++) {
            id = id + getRandomChar();
        }

        return id;
    }

    /**
     * Generates a random character from the set of numbers,
     * uppercase letters, or lowercase letters.
     * Each type has equal probability of being selected.
     *
     * @return A random character (number, uppercase letter, or lowercase letter)
     */
    private static char getRandomChar() {
        int characterType = (int) (Math.random() * 3);
        return switch (characterType) {
            case 0 -> generateRandomNumber();
            case 1 -> generateRandomUppercaseLetter();
            default -> generateRandomLowercaseLetter();
        };
    }

    private static char generateRandomNumber() {
        final int minNum = '0';
        final int maxNum = '9';
        return (char) (minNum + (int) (Math.random() * (maxNum - minNum + 1)));
    }

    private static char generateRandomUppercaseLetter() {
        final int minMayus = 'A';
        final int maxMayus = 'Z';
        return (char) (minMayus + (int) (Math.random() * (maxMayus - minMayus + 1)));
    }

    private static char generateRandomLowercaseLetter() {
        final int minMinus = 'a';
        final int maxMinus = 'z';
        return (char) (minMinus + (int) (Math.random() * (maxMinus - minMinus + 1)));
    }

}
