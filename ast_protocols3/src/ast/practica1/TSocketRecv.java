package ast.practica1;

import ast.protocols.tcp.TCPSegment;

public class TSocketRecv {

    private final Channel c;

    public TSocketRecv(Channel c) {
        this.c = c;
    }

    public int receiveData(byte[] data, int offset, int length) {
        throw new UnsupportedOperationException("A completar.");
    }
}
