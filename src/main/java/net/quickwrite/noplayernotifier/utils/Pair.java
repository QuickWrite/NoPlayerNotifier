package net.quickwrite.noplayernotifier.utils;

public class Pair<T, E> {
    private final T value1;
    private final E value2;

    public Pair(T value1, E value2) {
        this.value1 = value1;
        this.value2 = value2;
    }

    public T getValue1() {
        return value1;
    }

    public E getValue2() {
        return value2;
    }
}
