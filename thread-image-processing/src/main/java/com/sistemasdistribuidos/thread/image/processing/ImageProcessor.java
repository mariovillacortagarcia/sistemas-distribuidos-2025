package com.sistemasdistribuidos.thread.image.processing;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

/**
 * Image processor that converts a specific area of an image to grayscale.
 * Implements Runnable to allow execution in a separate thread. Optimized for
 * performance using direct pixels data access.
 *
 * @author mariovillacortagarcia
 */
public class ImageProcessor implements Runnable {

    private final BufferedImage image;
    private final Rectangle area;

    /**
     * Constructs a new image processor for the specified area.
     *
     * @param image the image to process
     * @param area  the rectangular area of the image that will be processed
     */
    public ImageProcessor(BufferedImage image, Rectangle area) {
        this.image = image;
        this.area = area;
    }

    /**
     * Processes the assigned area of the image, converting each pixels to
     * grayscale. This method is executed when the thread is started. Optimized
     * using direct pixels data access for better performance.
     */
    @Override
    public void run() {
        WritableRaster raster = image.getRaster();
        final int numComponents = raster.getNumBands();
        final int[] pixels = new int[numComponents];
        final int maxY = area.y + area.height;
        final int maxX = area.x + area.width;

        processPixels(raster, pixels, maxX, maxY);
    }

    /**
     * Processes pixels for images with fewer than 3 color components.
     *
     * @param raster the writable raster of the image
     * @param pixels the pixels array buffer
     * @param maxX   the maximum X coordinate
     * @param maxY   the maximum Y coordinate
     */
    private void processPixels(WritableRaster raster, int[] pixels, int maxX, int maxY) {
        for (int y = area.y; y < maxY; y++) {
            for (int x = area.x; x < maxX; x++) {
                raster.getPixel(x, y, pixels);
                int gray = calculateGray(pixels);
                setPixelToGray(pixels, gray);
                raster.setPixel(x, y, pixels);
            }
        }
    }

    /**
     * Calculates the grayscale value from all color components.
     *
     * @param pixels the pixels array containing color component values
     * @return the grayscale value (average of all components)
     */
    private int calculateGray(int[] pixels) {
        int sum = 0;
        for (int i = 0; i < pixels.length; i++) {
            sum += pixels[i];
        }
        return sum / pixels.length;
    }

    /**
     * Sets all color components of a pixels to the specified grayscale value.
     *
     * @param pixels the pixels array to modify
     * @param gray   the grayscale value to apply
     */
    private void setPixelToGray(int[] pixels, int gray) {
        for (int i = 0; i < pixels.length; i++) {
            pixels[i] = gray;
        }
    }

}
