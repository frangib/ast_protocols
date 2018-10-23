package C19CuaIndexada;

public class CuaIndexada<T> implements Cua<T> {

    private final T[] buffer;
    private final int N;
    private int indexIni = 0;
    private int posGet = 0;
    private int numElems = 0;

    public CuaIndexada(int N) {
        this.N = N;
        buffer = (T[]) (new Object[N]);
    }

    public void put(T t, int index) {
        //...
        if (isFull()) {
            throw new IllegalStateException("Cua plena");
        }
        /*(1)Moure tots els elements que van després de la posició index una posició
         enrere.
         Exemple:
         |F|R|A|N|C|I|S|C|0|
         |0|1|2|3|4|5|6|7|8|
         numElems = 9
         index = 4
         */
        
        int i;
        for (i = numElems - 1; i >= index; i--) {
            buffer[i + 1] = buffer[i];
        }
        /*Continuació exemple:
         |F|R|A|N| |C|I|S|C|0|
         |0|1|2|3|4|5|6|7|8|9|
         numElems = 9
         index = 4*/
        //(2)put i incrementar el nombre d'elements.
        buffer[index] = t;
        numElems++;
        /*
         /*Continuació exemple:
         |F|R|A|N|X|C|I|S|C|0|
         |0|1|2|3|4|5|6|7|8|9|
         numElems = 10
         index = 4*/
    }

    @Override
    public void put(T t) {
        if (isFull()) {
            throw new IllegalStateException("Cua plena");
        }
        buffer[(posGet + numElems) % N] = t;
        numElems = numElems + 1;
    }

    @Override
    public int put(T[] t, int offset, int len) {
        if (isFull()) {
            throw new IllegalStateException("Cua plena");
        }
        int i;
        for (i = 0; i < len; i++) {
            try {
                put(t[offset + 1]);
            } catch (Exception e) {
                break;
            }
        }
        return i;
    }

    public int put(T[] t, int offset, int len, int index) {
        //...
        
        if (isFull()) {
            throw new IllegalStateException("Cua plena");
        }
        int i, j;
        //utils --> nombre posicions útils de t
        int utils = len - offset;
        /*
        "Alliberar" 'len' posicions de 'buffer' per copiar la informació de 't' 
         des de 'offset' a la posició 'index' del 'buffer'.
        |F|R|A|N|C|I|S|C|0|
        |0|1|2|3|4|5|6|7|8|
        
        |F |R |A |N |C |  |  |  |I |S |C |0 |
        |0 |1 |2 |3 |4 |5 |6 |7 |8 |9 |10|11|
        
        vector a posar:
        |X|X|A|B|C|-->offset = 2; len = 3
        |0|1|2|3|4|
        on X representa una posició "inútil"
         */
        
        for (i = numElems - 1, j = 0; i > index; i--, j++) {
            buffer[i + utils] = buffer[numElems - 1 - j];
            buffer[i] = t[offset + j];
        }
        /*
        |F |R |A |N |C |A |B |C |I |S |C |0 |
        |0 |1 |2 |3 |4 |5 |6 |7 |8 |9 |10|11|
        */
        
        //Incrementar el nombre d'elements
        numElems += len;
        return j;
    }

    @Override
    public T get() {
        if (isEmpty()) {
            throw new IllegalStateException("Cua buida");
        }
        T t = null;
        t = buffer[posGet];
        buffer[posGet] = null;
        posGet = (posGet + 1) % N;
        numElems = numElems - 1;
        indexIni = indexIni + 1;
        return t;
    }

    @Override
    public int get(T[] t, int offset, int len) {
        int i = 0;
        for (i = 0; i < len && !isEmpty(); i++) {
            t[offset + i] = get();
        }
        return i;
    }

    @Override
    public boolean isFull() {
        return numElems == N;
    }

    @Override
    public boolean isEmpty() {
        return buffer[posGet] == null;
    }

    @Override
    public int size() {
        return numElems;
    }

    @Override
    public String toString() {
        //...
        int i;
        String str = "[";
        int first;
        boolean check = true;
        for (i = 0; check; i++) {
            if (buffer[i] != null) {
                check = false;
            }
        }
        first = i;
        for (i = first; i < this.numElems; i++) {
            if (i != this.numElems - 1) {
                str += buffer[i].toString() + ",";
            } else {
                //Si es la última iteració, posar claudàtor, no  coma.
                str += buffer[i].toString() + "]";
            }
        }
        return str;
    }
}