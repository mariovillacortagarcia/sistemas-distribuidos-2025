/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemasdistribuidos.thread.chrono;

/**
 * A thread-based chronometer that measures elapsed time.
 *
 * This class demonstrates the use of threads in Java by extending the Thread
 * class. Each instance of Chrono runs in its own separate thread, allowing
 * multiple chronometers to run concurrently and independently.
 *
 * Key concepts demonstrated: - Thread inheritance (extends Thread) - Thread
 * lifecycle (start, run, stop) - Concurrent execution (multiple threads running
 * simultaneously) - Thread interruption for graceful shutdown
 *
 * @author mariovillacortagarcia
 */
public class Chrono extends Thread {

    /**
     * Sleep interval in milliseconds to allow other threads to run.
     */
    private static final long SLEEP_INTERVAL_MS = 100;

    /**
     * Milliseconds per second conversion constant.
     */
    private static final long MILLISECONDS_PER_SECOND = 1000;

    /**
     * Seconds per minute conversion constant.
     */
    private static final long SECONDS_PER_MINUTE = 60;

    /**
     * Minutes per hour conversion constant.
     */
    private static final long MINUTES_PER_HOUR = 60;

    /**
     * Initial start time value indicating chronometer has not started.
     */
    private static final long INITIAL_START_TIME = 0;

    /**
     * The time when the chronometer started, in milliseconds.
     */
    private long startTime;

    /**
     * Flag indicating whether the chronometer is currently running. This flag
     * is used to control the while loop in the run() method.
     */
    private boolean running;

    /**
     * The name identifier for this chronometer.
     */
    private String name;

    /**
     * Constructs a new Chrono with the specified name.
     *
     * @param name the name identifier for this chronometer
     */
    public Chrono(String name) {
        this.name = name;
        this.running = false;
    }

    /**
     * Runs the chronometer thread.
     *
     * This method is automatically called when start() is invoked on this
     * thread. It records the start time and then enters a loop that continues
     * until the chronometer is stopped. The thread sleeps for 100ms in each
     * iteration to avoid consuming too much CPU.
     *
     * When interrupted (via interrupt() call), the thread exits gracefully.
     */
    @Override
    public void run() {
        initializeStartTime();
        running = true;

        while (running) {
            try {
                sleepToAllowOtherThreads();
            } catch (InterruptedException e) {
                handleInterruption();
                break;
            }
        }
    }

    /**
     * Initializes the start time when the thread begins execution.
     */
    private void initializeStartTime() {
        startTime = System.currentTimeMillis();
    }

    /**
     * Sleeps for a predefined interval to allow other threads to run and
     * prevent this thread from consuming all CPU time.
     *
     * @throws InterruptedException if the thread is interrupted while sleeping
     */
    private void sleepToAllowOtherThreads() throws InterruptedException {
        Thread.sleep(SLEEP_INTERVAL_MS);
    }

    /**
     * Handles thread interruption by stopping the chronometer gracefully.
     */
    private void handleInterruption() {
        running = false;
    }

    /**
     * Stops the chronometer and interrupts the thread.
     *
     * This method demonstrates proper thread termination: 1. Sets the running
     * flag to false (stops the while loop) 2. Interrupts the thread (wakes it
     * up if it's sleeping)
     *
     * This ensures the thread stops promptly and gracefully.
     */
    public void stopChrono() {
        running = false;
        interrupt();
    }

    /**
     * Gets the elapsed time formatted as HH:MM:SS.mmm.
     *
     * This method converts milliseconds to a human-readable format: - Hours:
     * minutes: seconds.milliseconds
     *
     * Example: 1234567 ms = 00:20:34.567
     *
     * @return a formatted string representing the elapsed time
     */
    public String getFormattedTime() {
        long elapsed = getElapsedTime();

        long totalSeconds = elapsed / MILLISECONDS_PER_SECOND;
        long totalMinutes = totalSeconds / SECONDS_PER_MINUTE;
        long hours = totalMinutes / MINUTES_PER_HOUR;

        long seconds = totalSeconds % SECONDS_PER_MINUTE;
        long minutes = totalMinutes % MINUTES_PER_HOUR;
        long milliseconds = elapsed % MILLISECONDS_PER_SECOND;

        return String.format("%02d:%02d:%02d.%03d", hours, minutes, seconds, milliseconds);
    }

    /**
     * Gets the name identifier of this chronometer.
     *
     * @return the chronometer's name
     */
    public String getChronoName() {
        return name;
    }

    /**
     * Checks if the chronometer is currently running.
     *
     * @return true if the chronometer is running, false otherwise
     */
    public boolean isRunning() {
        return running;
    }

    /**
     * Gets the elapsed time since the chronometer started.
     *
     * @return the elapsed time in milliseconds, or 0 if not started
     */
    private long getElapsedTime() {
        if (!running && startTime == INITIAL_START_TIME) {
            return INITIAL_START_TIME;
        }
        return System.currentTimeMillis() - startTime;
    }
}
