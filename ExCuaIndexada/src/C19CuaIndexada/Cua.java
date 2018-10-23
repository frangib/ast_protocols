package C19CuaIndexada;

public interface Cua<T> {

    public void put(T t);

    public int put(T[] t, int offset, int len);

    public T get();

    public int get(T[] t, int offset, int len);

    public boolean isFull();

    public boolean isEmpty();

    public int size();
}
