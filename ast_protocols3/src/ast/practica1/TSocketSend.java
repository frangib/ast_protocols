package ast.practica1;

import ast.protocols.tcp.TCPSegment;

public class TSocketSend {

    private final Channel c;

    public TSocketSend(Channel c) {
        this.c = c;
    }

    public void sendData(byte[] data, int offset, int length) {
        throw new UnsupportedOperationException("A completar.");
    }
}
