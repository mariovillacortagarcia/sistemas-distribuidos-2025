package com.sistemasdistribuidos.sockets.client.app.infrastructure;

import com.sistemasdistribuidos.sockets.client.app.domain.File;
import com.sistemasdistribuidos.sockets.client.app.domain.FilePort;
import java.util.Scanner;

/**
 * Console-based implementation of FilePort for reading user input.
 * 
 * This adapter reads file content from the console (standard input)
 * instead of reading from the file system. This is useful for demonstration
 * and interactive testing where the user provides data directly.
 * 
 * @author mariovillacortagarcia
 */
public class ConsoleFileRepository implements FilePort {

    /**
     * Scanner for reading input from the console.
     */
    private final Scanner scanner;

    /**
     * Constructs a new ConsoleFileRepository with a scanner for standard input.
     */
    public ConsoleFileRepository() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Retrieves a file by prompting the user to enter content via console.
     * 
     * The method displays a prompt with the source name and reads a line
     * of text from the console as the file content.
     * 
     * @param src The source name to display in the prompt
     * @return A File entity containing the user-provided content
     */
    @Override
    public File getFileFromSrc(String src) {
        System.out.printf("Enter content for '%s': ", src);
        String content = scanner.nextLine();
        return new File(content);
    }

}

