package com.util.tree;

public class Node<T extends Comparable<T>> {

    public Node(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    protected T data;

}
