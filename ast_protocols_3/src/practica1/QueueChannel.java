package practica1;

import ast.protocols.tcp.TCPSegment;

public class QueueChannel implements Channel {
    private CircularQueue<TCPSegment> cua;

    public QueueChannel(int N) {
        //throw new UnsupportedOperationException("A completar.");
        cua = new CircularQueue<TCPSegment>(N);
    }

    @Override
    public void send(TCPSegment s) {
        //throw new UnsupportedOperationException("A completar.");
        cua.put(s);
    }

    @Override
    public TCPSegment receive() {
        //throw new UnsupportedOperationException("A completar.");
        TCPSegment seg = cua.get();
        return seg;
    }

    @Override
    public int getMSS() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int getMMS() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
