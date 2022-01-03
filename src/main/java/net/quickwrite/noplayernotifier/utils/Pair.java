package net.quickwrite.noplayernotifier.utils;

/**
 * A simple Pair that holds two seperate values.
 *
 * @author QuickWrite
 * @param <T> The first value
 * @param <E> The second value
 */
public class Pair<T, E> {
    private final T value1;
    private final E value2;

    /**
     * Crates the Pair of two values.
     *
     * @param value1 The first value
     * @param value2 The second value
     */
    public Pair(T value1, E value2) {
        this.value1 = value1;
        this.value2 = value2;
    }

    /**
     * Gets the first value of the pair
     *
     * @return the first value
     */
    public T getValue1() {
        return value1;
    }

    /**
     * Gets the second value of the pair
     *
     * @return The second value
     */
    public E getValue2() {
        return value2;
    }
}
