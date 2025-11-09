/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.sistemasdistribuidos.thread.chrono;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Main class for the multiple chronometer application.
 * This application demonstrates the use of threads by allowing users to
 * create, view, and stop multiple chronometers running concurrently.
 * 
 * Each chronometer runs in its own thread, allowing multiple timers
 * to run simultaneously and independently.
 *
 * @author mariovillacortagarcia
 */
public class ThreadChronoMain {

    private static List<Chrono> chronos = new ArrayList<>();
    private static int chronoCounter = 1;

    /**
     * Main entry point for the application.
     * 
     * This method:
     * 1. Creates an InputMenu for user input
     * 2. Displays a menu in a loop
     * 3. Processes user selections
     * 4. Manages chronometer lifecycle
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        InputMenu inputMenu = new InputMenu();
        boolean running = true;

        printWelcomeMessage();

        try {
            while (running) {
                inputMenu.showMenu();
                MenuOption option = inputMenu.getMenuOption();

                if (option != null) {
                    running = processMenuOption(option, inputMenu, running);
                } else {
                    printInvalidOptionMessage();
                }
            }
        } catch (IllegalStateException e) {
            System.err.println("Error: Input is closed. Exiting application.");
        } catch (NoSuchElementException e) {
            System.err.println("Error: No input available. Exiting application.");
        } finally {
            inputMenu.close();
            stopAllChronos();
        }
    }

    /**
     * Prints the welcome message when the application starts.
     */
    private static void printWelcomeMessage() {
        System.out.println("=== Multiple Chronometer Application ===");
        System.out.println("This application demonstrates concurrent threads.\n");
    }

    /**
     * Prints a message indicating that an invalid option was selected.
     */
    private static void printInvalidOptionMessage() {
        System.out.println("Invalid option. Please select an option from 1 to 4.");
    }

    /**
     * Processes the selected menu option and returns whether the application should
     * continue running.
     *
     * @param option           the selected menu option
     * @param inputMenu        the InputMenu instance for reading user input
     * @param currentlyRunning the current running state
     * @return false if the application should exit, true otherwise
     */
    private static boolean processMenuOption(MenuOption option, InputMenu inputMenu, boolean currentlyRunning) {
        switch (option) {
            case CREATE_CHRONO:
                createNewChrono();
                return true;
            case VIEW_RUNNING:
                viewRunningChronos();
                return true;
            case STOP_CHRONO:
                stopChrono(inputMenu);
                return true;
            case EXIT:
                handleExit();
                return false;
            default:
                return currentlyRunning;
        }
    }

    /**
     * Handles the exit option by stopping all chronometers and printing a message.
     */
    private static void handleExit() {
        stopAllChronos();
        System.out.println("Exiting the program...");
    }

    /**
     * Creates a new chronometer and adds it to the list.
     */
    private static void createNewChrono() {
        final String name = generateDefaultChronometerName();
        final Chrono chrono = new Chrono(name);
        chrono.start();
        addChronometerToList(chrono);
        printChronometerCreatedMessage(chrono);
    }

    /**
     * Generates a default chronometer name using the current counter.
     *
     * @return a default chronometer name
     */
    private static String generateDefaultChronometerName() {
        return "Chronometer " + chronoCounter;
    }

    /**
     * Adds a chronometer to the list and increments the counter.
     *
     * @param chrono the chronometer to add
     */
    private static void addChronometerToList(Chrono chrono) {
        chronos.add(chrono);
        chronoCounter++;
    }

    /**
     * Prints a message indicating that a chronometer was created and started.
     *
     * @param chrono the chronometer instance
     */
    private static void printChronometerCreatedMessage(final Chrono chrono) {
        System.out.println("✓ Chronometer '" + chrono.getChronoName() + "' created and started.");
        System.out.println("  (Running in thread: " + chrono.getName() + ")");
    }

    /**
     * Displays all currently running chronometers with their elapsed times.
     * 
     * This method filters the list of chronometers to show only those
     * that are currently running, and displays their current time.
     */
    private static void viewRunningChronos() {
        final List<Chrono> runningChronos = getRunningChronometers();

        if (runningChronos.isEmpty()) {
            printNoRunningChronometersMessage();
        } else {
            printRunningChronometersList(runningChronos);
        }
    }

    /**
     * Filters and returns only the chronometers that are currently running.
     *
     * @return a list of running chronometers
     */
    private static List<Chrono> getRunningChronometers() {
        final List<Chrono> runningChronos = new ArrayList<>();
        for (Chrono chrono : chronos) {
            if (chrono.isRunning()) {
                runningChronos.add(chrono);
            }
        }
        return runningChronos;
    }

    /**
     * Prints a message indicating that no chronometers are running.
     */
    private static void printNoRunningChronometersMessage() {
        System.out.println("No chronometers are currently running.");
    }

    /**
     * Prints a formatted list of running chronometers with their elapsed times.
     *
     * @param runningChronos the list of running chronometers to display
     */
    private static void printRunningChronometersList(List<Chrono> runningChronos) {
        System.out.println("\n--- Running Chronometers ---");
        for (int i = 0; i < runningChronos.size(); i++) {
            final Chrono chrono = runningChronos.get(i);
            printChronometerInfo(i + 1, chrono);
        }
        System.out.println("----------------------------\n");
    }

    /**
     * Prints information about a single chronometer.
     *
     * @param index  the display index (1-based)
     * @param chrono the chronometer to display
     */
    private static void printChronometerInfo(int index, Chrono chrono) {
        System.out.printf("%d. %s: %s (Thread: %s)\n",
                index,
                chrono.getChronoName(),
                chrono.getFormattedTime(),
                chrono.getName());
    }

    /**
     * Stops a specific chronometer selected by the user.
     * 
     * This method:
     * 1. Shows all running chronometers
     * 2. Asks the user to select one
     * 3. Stops the selected chronometer
     * 4. Displays the final elapsed time
     * 
     * @param inputMenu the InputMenu instance for reading user input
     */
    private static void stopChrono(InputMenu inputMenu) {
        final List<Chrono> runningChronos = getRunningChronometers();

        if (runningChronos.isEmpty()) {
            printNoChronometersToStopMessage();
        } else {
            printRunningChronometersForSelection(runningChronos);
            int index = inputMenu.getIntInput("Select the number of the chronometer to stop: ");

            if (isValidChronometerIndex(index, runningChronos.size())) {
                stopSelectedChronometer(runningChronos, index);
            } else {
                printInvalidNumberMessage();
            }
        }
    }

    /**
     * Prints a message indicating that there are no chronometers to stop.
     */
    private static void printNoChronometersToStopMessage() {
        System.out.println("No chronometers are currently running to stop.");
    }

    /**
     * Prints the list of running chronometers for user selection.
     *
     * @param runningChronos the list of running chronometers
     */
    private static void printRunningChronometersForSelection(List<Chrono> runningChronos) {
        System.out.println("\n--- Running Chronometers ---");
        for (int i = 0; i < runningChronos.size(); i++) {
            Chrono chrono = runningChronos.get(i);
            System.out.printf("%d. %s: %s\n",
                    i + 1,
                    chrono.getChronoName(),
                    chrono.getFormattedTime());
        }
        System.out.println("----------------------------\n");
    }

    /**
     * Validates if the given index is within the valid range for chronometer
     * selection.
     *
     * @param index the index to validate
     * @param size  the total number of running chronometers
     * @return true if the index is valid, false otherwise
     */
    private static boolean isValidChronometerIndex(int index, int size) {
        return index >= 1 && index <= size;
    }

    /**
     * Stops the selected chronometer and displays the final time.
     *
     * @param runningChronos the list of running chronometers
     * @param index          the 1-based index of the chronometer to stop
     */
    private static void stopSelectedChronometer(List<Chrono> runningChronos, int index) {
        Chrono chrono = runningChronos.get(index - 1);
        chrono.stopChrono();
        printChronometerStoppedMessage(chrono);
    }

    /**
     * Prints a message indicating that a chronometer was stopped, along with its
     * final time.
     *
     * @param chrono the stopped chronometer
     */
    private static void printChronometerStoppedMessage(Chrono chrono) {
        System.out.println("✓ Chronometer '" + chrono.getChronoName() + "' stopped.");
        System.out.println("  Final time: " + chrono.getFormattedTime());
    }

    /**
     * Prints a message indicating that an invalid number was entered.
     */
    private static void printInvalidNumberMessage() {
        System.out.println("Invalid number.");
    }

    /**
     * Stops all running chronometers.
     * This is called when the application exits to ensure
     * all threads are properly terminated.
     */
    private static void stopAllChronos() {
        for (Chrono chrono : chronos) {
            if (chrono.isRunning()) {
                chrono.stopChrono();
            }
        }
    }

}
