
public class CuaCirc<E> implements Cua<E> {

    private int capacitat;//N
    private volatile int numElem = 0;
    private int posP = 0;
    private int posG = 0;

    private E[] cua;

    public CuaCirc(int capacitat) {
        this.capacitat = capacitat;
        cua = (E[]) (new Object[capacitat]);
    }

    @Override
    public void put(E e) {
        if (numElem == capacitat) {
            throw new IllegalStateException("cua plena");
        }
        cua[posP] = e;
        numElem = numElem + 1;
        posP = (posP + 1) % capacitat;
    }

    public E get() {
        if (numElem == 0) {
            throw new IllegalStateException("cua buida");
        }
        E e = cua[posG];
        numElem = numElem - 1;
        posG = (posG + 1) % capacitat;
        return e;
    }

    @Override
    public int put(E[] e, int offset, int length) {
        int len = Math.min(free(), length);
        for (int i = 0; i < len; i++) {
            put(e[offset + i]);
        }
        return len;
    }

      @Override
    public int get(E[] e, int offset, int length) {
        int len = Math.min(size(), length);
        for (int i = 0; i < len; i++) {
            e[offset + i] = get();
        }
        return len;
    }

    @Override
    public int size() {
        int ne = numElem;
        return ne;
    }

    @Override
    public boolean full() {
        return numElem == capacitat;
    }

    @Override
    public boolean empty() {
        return numElem == 0;
    }

    @Override
    public int free() {
        return capacitat - numElem;
    }

    @Override
    public String toString() {
        Object[] str = new Object[capacitat];

        for (int i = 0; i < capacitat; i++) {
            str[i] = "_";
        }

        for (int i = 0; i < numElem; i++) {
            str[(posG + i) % capacitat] = cua[(posG + i) % capacitat];
        }
        String strt = "[";
        for (int i = 0; i < capacitat; i++) {
            strt = strt + str[i].toString() + ",";
        }
        strt = strt + "]";
        return strt;
    }

}
