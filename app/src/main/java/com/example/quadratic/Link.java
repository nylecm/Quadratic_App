package com.example.quadratic;

public class Link<T> {
    private T e;
    private Link<T> next;

    public Link(T e, Link<T> next) {
        this.e = e;
        this.next = next;
    }

    public T getE() {
        return e;
    }

    public void setE(T e) {
        this.e = e;
    }

    public Link<T> getNext() {
        return next;
    }

    public void setNext(Link<T> next) {
        this.next = next;
    }
}
