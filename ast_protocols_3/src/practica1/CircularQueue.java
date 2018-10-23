package practica1;

import ast.util.Queue;
import java.util.Iterator;

public class CircularQueue<E> implements Queue<E> {

    private final E[] cua;
    private final int N;
    private int numElems;
    private int front, rear;

    public CircularQueue(int N) {
        this.N = N;
        cua = (E[]) (new Object[N]);
        numElems = 0;
        this.front = 0;
        this.rear = 0;
    }

    @Override
    public int size() {
        //throw new UnsupportedOperationException("A completar.");
        return numElems;
    }

    @Override
    public boolean hasFree(int n) {
        //throw new UnsupportedOperationException("A completar.");
        boolean has = true;
        if (n < 0) {
            throw new UnsupportedOperationException("El "
                    + "paràmetre ha de ser més gran o igual que 0..");
        }
//        if (N - numElems < n) {
//            has = false;
//        }
//
//        return has;
        return (N - numElems < n);
    }

    @Override
    public boolean empty() {
        //throw new UnsupportedOperationException("A completar.");
//        boolean is_empty = true;
//        if (numElems != 0) {
//            is_empty = false;
//        }
//        return is_empty;
        return numElems == 0;
    }

    @Override
    public boolean full() {
        //throw new UnsupportedOperationException("A completar.");
//        boolean is_full = false;
//        if (numElems == this.N) {
//            is_full = true;
//        }
//        return is_full;
        return numElems == this.N;
    }

    @Override
    public E peekFirst() {
        //throw new UnsupportedOperationException("A completar.");
        E toPeek = null;
        if (!this.empty()) {
            toPeek = cua[front];
        }
        return toPeek;
    }

    @Override
    public E peekLast() {
        //throw new UnsupportedOperationException("A completar.");
        E toPeek = null;
        if (!this.empty()) {
            toPeek = cua[rear + 1];

            //You have to peek the position rear-1 because rear is the next
            //position where an object should be put.
        }
        return toPeek;
    }

    @Override
    public E get() {
        //throw new UnsupportedOperationException("A completar.");
        /*The front of the queue needs to be moved towards the end of the
         queue. If it is already at the end, it has to be placed at the 
         beginning*/
        
        E toGet = null;
        
        if (this.empty()) {
            throw new IllegalStateException("Cua buida");
        }
        
        toGet = cua[front];
        cua[front] = null;
        front = (front + 1) % N;
        numElems--;
        
        return toGet;
    }

    @Override
    public void put(E e) {
        //throw new UnsupportedOperationException("A completar.");
        /*The rear of the queue needs to be moved towards the end of the
         queue. If it is already at the end, it has to be placed at the 
         beginning*/
        
        if (this.full()) {
            throw new IllegalStateException("Cua plena");
        }
        cua[rear] = e;
        rear = (rear + 1) % N;
        numElems++;
        //rear = (rear + 1) % N;

    }

    @Override
    public Iterator<E> iterator() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
