package ast.practica1;

import ast.protocols.tcp.TCPSegment;

public class QueueChannel implements Channel {

    public QueueChannel(int N) {
        throw new UnsupportedOperationException("A completar.");
    }

    @Override
    public void send(TCPSegment s) {
        throw new UnsupportedOperationException("A completar.");
    }

    @Override
    public TCPSegment receive() {
        throw new UnsupportedOperationException("A completar.");
    }

    @Override
    public int getMSS() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
