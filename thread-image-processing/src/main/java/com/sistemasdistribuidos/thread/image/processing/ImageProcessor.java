package com.sistemasdistribuidos.thread.image.processing;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

/**
 * Image processor that converts a specific area of an image to grayscale.
 * Implements Runnable to allow execution in a separate thread. Optimized for
 * performance using direct pixel data access.
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
     * @param area the rectangular area of the image that will be processed
     */
    public ImageProcessor(BufferedImage image, Rectangle area) {
        this.image = image;
        this.area = area;
    }

    /**
     * Processes the assigned area of the image, converting each pixel to
     * grayscale. This method is executed when the thread is started. Optimized
     * using direct pixel data access for better performance.
     */
    @Override
    public void run() {
        WritableRaster raster = image.getRaster();
        final int numComponents = raster.getNumBands();
        final int[] pixel = new int[numComponents];
        final int maxY = area.y + area.height;
        final int maxX = area.x + area.width;

        if (numComponents >= 3) {
            processRGBPixels(raster, pixel, maxX, maxY);
        } else {
            processGenericPixels(raster, pixel, numComponents, maxX, maxY);
        }
    }

    /**
     * Processes pixels for RGB images (3 or more color components). Optimized
     * path for the most common case.
     *
     * @param raster the writable raster of the image
     * @param pixel the pixel array buffer
     * @param maxX the maximum X coordinate
     * @param maxY the maximum Y coordinate
     */
    private void processRGBPixels(WritableRaster raster, int[] pixel, int maxX, int maxY) {
        for (int y = area.y; y < maxY; y++) {
            for (int x = area.x; x < maxX; x++) {
                raster.getPixel(x, y, pixel);
                int gray = calculateGrayFromRGB(pixel);
                setPixelToGray(pixel, gray, 3);
                raster.setPixel(x, y, pixel);
            }
        }
    }

    /**
     * Processes pixels for images with fewer than 3 color components.
     *
     * @param raster the writable raster of the image
     * @param pixel the pixel array buffer
     * @param numComponents the number of color components
     * @param maxX the maximum X coordinate
     * @param maxY the maximum Y coordinate
     */
    private void processGenericPixels(WritableRaster raster, int[] pixel, int numComponents, int maxX, int maxY) {
        for (int y = area.y; y < maxY; y++) {
            for (int x = area.x; x < maxX; x++) {
                raster.getPixel(x, y, pixel);
                int gray = calculateGrayFromComponents(pixel, numComponents);
                setPixelToGray(pixel, gray, numComponents);
                raster.setPixel(x, y, pixel);
            }
        }
    }

    /**
     * Calculates the grayscale value from RGB components.
     *
     * @param pixel the pixel array containing RGB values
     * @return the grayscale value (average of R, G, B)
     */
    private int calculateGrayFromRGB(int[] pixel) {
        return (pixel[0] + pixel[1] + pixel[2]) / 3;
    }

    /**
     * Calculates the grayscale value from all color components.
     *
     * @param pixel the pixel array containing color component values
     * @param numComponents the number of color components
     * @return the grayscale value (average of all components)
     */
    private int calculateGrayFromComponents(int[] pixel, int numComponents) {
        int sum = 0;
        for (int i = 0; i < numComponents; i++) {
            sum += pixel[i];
        }
        return sum / numComponents;
    }

    /**
     * Sets all color components of a pixel to the specified grayscale value.
     *
     * @param pixel the pixel array to modify
     * @param gray the grayscale value to apply
     * @param numComponents the number of color components to set
     */
    private void setPixelToGray(int[] pixel, int gray, int numComponents) {
        for (int i = 0; i < numComponents; i++) {
            pixel[i] = gray;
        }
    }

}
