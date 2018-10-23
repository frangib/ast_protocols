package practica1;

import ast.protocols.tcp.TCPSegment;

public class TSocketRecv {

    private final Channel c;

    public TSocketRecv(Channel c) {
        this.c = c;
    }

    public int receiveData(byte[] data, int offset, int length) {
        //throw new UnsupportedOperationException("A completar.");
        /*
         En el canal puede haber muchos más bytes de los que podemos recibir. El
         length que tiene receiveData como parámetro es la cantidad de espacio
         disponible que hay. Entonces, de los bytes que haya en el canal tendre-
         mos que copiar length en data[]. Pero claro, length puede ser mayor o
         menor que la longitud de los datos que hay en el segmento. Por consi-
         guiente, hay que hacer casos.
         */

        TCPSegment seg = c.receive();
        int off = seg.getDataOffset();
        int len = seg.getDataLength();
        byte[] aux = seg.getData();
        int read = 0;

        if (data.length < len) {
            System.arraycopy(aux, off, data, offset, data.length);
            read = data.length;
        } else {
            System.arraycopy(aux, off, data, offset, len);
            read = len;
        }

        return read;
    }
}
