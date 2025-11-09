package com.sistemasdistribuidos.thread.image.processing;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

/**
 * Main application for processing images in grayscale using multiple threads.
 * Divides the image into rectangular areas and processes each area in parallel.
 *
 * @author mariovillacortagarcia
 */
public class ThreadImageProcessing {

    /**
     * The number of threads to use for processing the image.
     */
    private static final int NUM_THREADS = 4;
    /**
     * The path to the input image.
     */
    private static final String INPUT_PATH = "input/Crucifixion_with_a_Donor,_ca._1480-85.jpg";
    /**
     * The path to the output image.
     */
    private static final String OUTPUT_PATH = "output/grayscale_Crucifixion_with_a_Donor,_ca._1480-85.jpg";

    /**
     * Main entry point of the application.
     */
    public static void main(String[] args) {
        try {
            BufferedImage image = loadImage();
            System.out.println("Image loaded from " + INPUT_PATH);
            processImageWithThreads(image);
            saveImage(image);

        } catch (IOException e) {
            System.err.println("I/O Error: " + e.getMessage());
            e.printStackTrace();
        } catch (InterruptedException e) {
            System.err.println("Error: Thread interrupted");
            e.printStackTrace();
        }
    }

    /**
     * Loads an image from the file system.
     *
     * @return the loaded image
     * @throws IOException              if an error occurs while reading the file
     * @throws IllegalArgumentException if the image could not be loaded
     */
    private static BufferedImage loadImage() throws IOException {
        BufferedImage image = ImageIO.read(new File(INPUT_PATH));
        if (image == null) {
            throw new IllegalArgumentException("Could not load image from " + INPUT_PATH);
        }
        return image;
    }

    /**
     * Processes the image using multiple threads, dividing the image into
     * areas.
     *
     * @param image the image to process
     * @throws InterruptedException if a thread is interrupted during execution
     */
    private static void processImageWithThreads(BufferedImage image) throws InterruptedException {
        System.out.println("Processing image with " + NUM_THREADS + " threads");
        List<Rectangle> areas = divideImage(image);
        List<Thread> threads = createAndStartThreads(image, areas);

        final long startTime = System.currentTimeMillis();
        waitForThreads(threads);
        final long endTime = System.currentTimeMillis();

        System.out.println("Processing completed in " + (endTime - startTime) + " ms");
    }

    /**
     * Creates and starts threads to process each area of the image.
     *
     * @param image the image to process
     * @param areas the list of rectangular areas to process
     * @return the list of created and started threads
     */
    private static List<Thread> createAndStartThreads(BufferedImage image, List<Rectangle> areas) {
        List<Thread> threads = new ArrayList<>();
        for (Rectangle area : areas) {
            ImageProcessor processor = new ImageProcessor(image, area);
            Thread thread = new Thread(processor);
            threads.add(thread);
            thread.start();
        }
        return threads;
    }

    /**
     * Waits for all threads to finish their execution.
     *
     * @param threads the list of threads to wait for
     * @throws InterruptedException if a thread is interrupted during the wait
     */
    private static void waitForThreads(List<Thread> threads) throws InterruptedException {
        for (Thread thread : threads) {
            thread.join();
        }
    }

    /**
     * Saves the processed image to the file system.
     *
     * @param image the image to save
     * @throws IOException if an error occurs while writing the file
     */
    private static void saveImage(BufferedImage image) throws IOException {
        ImageIO.write(image, getFileExtension(OUTPUT_PATH), new File(OUTPUT_PATH));
        System.out.println("Image saved to: " + OUTPUT_PATH);
    }

    /**
     * Divides the image into rectangular areas for parallel processing. Creates
     * a grid of approximately square areas based on the number of threads.
     *
     * @param image the image to divide
     * @return a list of rectangles representing the processing areas
     */
    private static List<Rectangle> divideImage(BufferedImage image) {
        List<Rectangle> areas = new ArrayList<>();
        int width = image.getWidth();
        int height = image.getHeight();

        int cols = (int) Math.sqrt(NUM_THREADS);
        int rows = (int) Math.ceil((double) NUM_THREADS / cols);

        int tileWidth = width / cols;
        int tileHeight = height / rows;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                int x = col * tileWidth;
                int y = row * tileHeight;

                int w = (col == cols - 1) ? width - x : tileWidth;
                int h = (row == rows - 1) ? height - y : tileHeight;

                areas.add(new Rectangle(x, y, w, h));

                if (areas.size() >= NUM_THREADS) {
                    break;
                }
            }
            if (areas.size() >= NUM_THREADS) {
                break;
            }
        }

        return areas;
    }

    /**
     * Gets the file extension to determine the image format.
     *
     * @param filename the file name
     * @return the file extension in lowercase, or "jpg" by default if not found
     */
    private static String getFileExtension(String filename) {
        int lastDot = filename.lastIndexOf('.');
        if (lastDot > 0 && lastDot < filename.length() - 1) {
            return filename.substring(lastDot + 1).toLowerCase();
        }
        return "jpg";
    }
}
