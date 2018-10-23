package ast.practica1;

import ast.util.Queue;
import java.util.Iterator;

public class CircularQueue<E> implements Queue<E> {

    private final E[] cua;
    private final int N;

    public CircularQueue(int N) {
        this.N = N;
        cua = (E[]) (new Object[N]);
    }

    @Override
    public int size() {
        throw new UnsupportedOperationException("A completar.");
    }

    @Override
    public boolean hasFree(int n) {
        throw new UnsupportedOperationException("A completar.");
    }

    @Override
    public boolean empty() {
        throw new UnsupportedOperationException("A completar.");
    }

    @Override
    public boolean full() {
        throw new UnsupportedOperationException("A completar.");
    }

    @Override
    public E peekFirst() {
        throw new UnsupportedOperationException("A completar.");
    }

    @Override
    public E peekLast() {
        throw new UnsupportedOperationException("A completar.");
    }

    @Override
    public E get() {
        throw new UnsupportedOperationException("A completar.");
    }

    @Override
    public void put(E e) {
        throw new UnsupportedOperationException("A completar.");
    }

    @Override
    public Iterator<E> iterator() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
