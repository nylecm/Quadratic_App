package com.example.quadratic;

import java.util.NoSuchElementException;

public class Stack<T> {
    private Link<T> head;
    private Link<T> tail;

    public Stack() {
        this.head = null;
        this.tail = null;
    }

    public void push(T e) {
        Link<T> newHead = new Link<T>(e, head);
        head = newHead;
        if (tail == null) {
            tail = head;
        }
    }

    public void pop() {
        if (isEmpty()) {
            throw new NoSuchElementException("Stack is empty, nothing to remove");
        } else {
            head = head.getNext();
            if (head == null) { //Prevents is empty bug.
                tail = null;
            }
        }
    }

    public T peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("Stack is empty, peek failed.");
        }
        return head.getE();
    }

    public boolean isEmpty() {
        return ((head == null) && (tail == null));
    }

    public static void main(String[] args) {
        Stack<String> stack = new Stack<>();

        System.out.println(stack.isEmpty());

        stack.push("Hello");
        stack.push("World!");
        stack.push("Last One... Should be First!");

        System.out.println(stack.peek());
        stack.pop();
        System.out.println(stack.peek());
        stack.pop();
        System.out.println(stack.peek());
        stack.pop();
        System.out.println(stack.isEmpty());
    }
}
