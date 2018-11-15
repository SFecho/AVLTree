package com.util.tree;

public class Pair<T1,T2> {
    protected T1 first;
    protected T2 second;

    public T1 getFirst() {
        return first;
    }

    public void setFirst(T1 first) {
        this.first = first;
    }

    public T2 getSecond() {
        return second;
    }

    public void setSecond(T2 second) {
        this.second = second;
    }

    public Pair() {

    }

    public Pair(T1 first, T2 second) {

        this.first = first;
        this.second = second;
    }
}
