package practica1;

import ast.protocols.tcp.TCPSegment;

public class TSocketSend {

    private final Channel c;

    public TSocketSend(Channel c) {
        this.c = c;
    }

    public void sendData(byte[] data, int offset, int length) {
        //throw new UnsupportedOperationException("A completar.");
        TCPSegment seg = new TCPSegment();
        byte[] aux = new byte[length];
        
        /*Copy length bytes from data (from position offset onwards) to aux 
        (from position 0 onwards)*/
        System.arraycopy(data, offset, aux, 0, length);
        /*When copying to aux, should we copy to
        position 0 or to position offset? Answer: we copy to position 0 because
        if the amount data to copy has more positions than there are free from 
        offset to the end of de destination array, nullPointerException*/
        seg.setData(aux, 0, length);
        c.send(seg);
    }
}
