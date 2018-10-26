

public interface Cua<E> {
    public void put(E e);
    public int put(E[] e, int offset, int length);
    
    public E get();
    public int get(E[] e, int offset, int length);
    
    
    public int size();
    public boolean full();
    public boolean empty();
    public int free();
}
