/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ast.util;
import java.util.Iterator;


/**
 *
 * @author lastusr39
 * @param <E>
 */
public class CircularQueue<E> implements Queue<E> {

    private int numElements, front, rear;
    private final int capacitat;
    private final E[] cua;
    //testing code
//    public static void main(String[] args) {
//          CircularQueue<Integer> q = new CircularQueue<Integer>(5);        
//          q.put(3);
//          q.put(8);
//          q.put(7);
//          q.put(5);
//          System.out.println(q.hasFree(1));
//          System.out.println("size:" + q.size());
//          System.out.println("empty:" + q.empty());
//          System.out.println("full:" + q.full());
//          System.out.println("first:" + q.peekFirst());
//          System.out.println("last:" + q.peekLast());
//          q.get();
//          q.get();
//           System.out.println(q.hasFree(2));
//          System.out.println("size:" + q.size());
//          System.out.println("empty" + q.empty());
//          System.out.println("full:" + q.full());
//          System.out.println("first:" + q.peekFirst());
//          System.out.println("last:" + q.peekLast());
//   }

    public CircularQueue(int N) {
        capacitat=N;
        cua = (E[]) (new Object[N]);
    }
    
    @Override
    public int size() {
        return numElements;
    }
    
    @Override
    public boolean hasFree(int n) {
        if(n <= (capacitat-numElements)) {
            return true;
        }
        else {
            throw new IllegalArgumentException();
        }
    }
    
    @Override
    public boolean empty () {
       return numElements==0;
    }
    
    @Override
    public boolean full() {
        return numElements==capacitat;
        
    }
    
    @Override
    public E peekFirst() {
        if(numElements==0) {
            return null;
        }
        else {
            return cua[front];
        }
    }
    
    @Override
    public E peekLast() {
        if(numElements==0) {
           return null;
        }
        else{ 
            if(rear==0) {
                rear=5;
            }
           
            return cua[rear-1];
        }
        
    }
    
    @Override
    public E get() {
        E k;
        if(numElements==0) {
            throw new IllegalStateException();
          
        }
        else {
            k=cua[front];
            front=(front+1)%capacitat;
            numElements=numElements-1;
            return k;
        }
    }
    
    @Override
    public void put (E e){
        if(numElements == capacitat){
            throw new IllegalStateException();
        }else{
            cua[rear] = e;
            rear=(rear+1)%capacitat;
            numElements=numElements+1;
        }
    }
    
    @Override
    public Iterator<E> iterator() {
        throw new UnsupportedOperationException("Unsupported method iterator()");
    }
    
    
}
