package com.sistemasdistribuidos.thread.chrono;

/**
 * Enum representing the available menu options.
 * This enum makes the code more readable and maintainable by
 * associating numeric values with meaningful names.
 *
 * @author mariovillacortagarcia
 */
public enum MenuOption {
    CREATE_CHRONO(1, "Create a new chronometer"),
    VIEW_RUNNING(2, "View running chronometers"),
    STOP_CHRONO(3, "Stop a chronometer"),
    EXIT(4, "Exit");

    private final int value;
    private final String description;

    /**
     * Constructs a MenuOption with the specified value and description.
     *
     * @param value       the numeric value for this option
     * @param description the description text for this option
     */
    MenuOption(int value, String description) {
        this.value = value;
        this.description = description;
    }

    /**
     * Gets the numeric value of this menu option.
     *
     * @return the numeric value
     */
    public int getValue() {
        return value;
    }

    /**
     * Gets the description of this menu option.
     *
     * @return the description text
     */
    public String getDescription() {
        return description;
    }

    /**
     * Finds a MenuOption by its numeric value.
     *
     * @param value the numeric value to search for
     * @return the MenuOption with the matching value, or null if not found
     */
    public static MenuOption fromValue(int value) {
        for (MenuOption option : MenuOption.values()) {
            if (option.value == value) {
                return option;
            }
        }
        return null;
    }
}

