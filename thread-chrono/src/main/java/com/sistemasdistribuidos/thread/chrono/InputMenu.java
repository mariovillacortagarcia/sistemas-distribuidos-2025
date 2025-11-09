package com.sistemasdistribuidos.thread.chrono;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Handles all Scanner operations and exception management for menu input.
 * This class encapsulates all Scanner-related operations and provides
 * a safe interface for reading user input with proper exception handling.
 *
 * @author mariovillacortagarcia
 */
public class InputMenu {

    private final Scanner scanner;

    /**
     * Constructs a new InputMenu with a Scanner reading from System.in.
     */
    public InputMenu() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Displays the main menu with all available options.
     * Uses the enum descriptions to show menu items dynamically.
     */
    public void showMenu() {
        System.out.println("\n=== MULTIPLE CHRONOMETER ===");
        for (MenuOption option : MenuOption.values()) {
            System.out.println(option.getValue() + ". " + option.getDescription());
        }
        System.out.println("============================\n");
    }

    /**
     * Gets a menu option from user input.
     * Handles all Scanner exceptions internally and returns null if an error occurs.
     *
     * @return the selected MenuOption, or null if invalid or an error occurs
     */
    public MenuOption getMenuOption() {
        try {
            int optionValue = getIntInput("Select an option: ");
            return MenuOption.fromValue(optionValue);
        } catch (IllegalStateException e) {
            System.err.println("Error: Scanner is in an invalid state.");
            return null;
        } catch (NoSuchElementException e) {
            System.err.println("Error: No input available.");
            return null;
        }
    }

    /**
     * Prompts the user for an integer input and validates it.
     * 
     * This method ensures that the user enters a valid integer,
     * repeatedly prompting until valid input is received.
     * All Scanner exceptions are handled internally.
     *
     * @param message the prompt message to display
     * @return the validated integer input
     * @throws IllegalStateException if the scanner is closed
     * @throws NoSuchElementException if no input is available
     */
    public int getIntInput(String message) throws IllegalStateException, NoSuchElementException {
        System.out.print(message);

        while (!scanner.hasNextInt()) {
            try {
                System.out.print("Please enter a valid number: ");
                discardInvalidInput();
            } catch (IllegalStateException e) {
                System.err.println("Error: Scanner is in an invalid state.");
                throw e;
            } catch (NoSuchElementException e) {
                System.err.println("Error: No input available.");
                throw e;
            }
        }

        try {
            int value = scanner.nextInt();
            consumeNewlineCharacter();
            return value;
        } catch (InputMismatchException e) {
            discardInvalidInput();
            consumeNewlineCharacter();
            return getIntInput(message);
        } catch (IllegalStateException e) {
            System.err.println("Error: Scanner is in an invalid state.");
            throw e;
        } catch (NoSuchElementException e) {
            System.err.println("Error: No input available.");
            throw e;
        }
    }

    /**
     * Gets a string input from the user.
     * Handles all Scanner exceptions internally and returns an empty string if an error occurs.
     *
     * @param prompt the prompt message to display
     * @return the user input string, or empty string if an error occurs
     */
    public String getStringInput(String prompt) {
        try {
            System.out.print(prompt);
            return scanner.nextLine().trim();
        } catch (IllegalStateException e) {
            System.err.println("Error: Scanner is in an invalid state.");
            return "";
        } catch (NoSuchElementException e) {
            System.err.println("Error: No input available.");
            return "";
        }
    }

    /**
     * Closes the Scanner instance.
     * This method should be called when the InputMenu is no longer needed.
     */
    public void close() {
        if (scanner != null) {
            scanner.close();
        }
    }

    /**
     * Discards invalid input from the scanner.
     *
     * @throws IllegalStateException if the scanner is closed
     * @throws NoSuchElementException if no input is available
     */
    private void discardInvalidInput() throws IllegalStateException, NoSuchElementException {
        try {
            scanner.next();
        } catch (IllegalStateException e) {
            System.err.println("Error: Scanner is in an invalid state.");
            throw e;
        } catch (NoSuchElementException e) {
            System.err.println("Error: No input available.");
            throw e;
        }
    }

    /**
     * Consumes the newline character left by nextInt().
     *
     * @throws IllegalStateException if the scanner is closed
     * @throws NoSuchElementException if no input is available
     */
    private void consumeNewlineCharacter() throws IllegalStateException, NoSuchElementException {
        try {
            scanner.nextLine();
        } catch (IllegalStateException e) {
            System.err.println("Error: Scanner is in an invalid state.");
            throw e;
        } catch (NoSuchElementException e) {
            System.err.println("Error: No input available.");
            throw e;
        }
    }
}

