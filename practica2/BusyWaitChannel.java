package practica2;

import ast.protocols.tcp.TCPSegment;
import practica1.Channel;

public class BusyWaitChannel implements Channel {

    public BusyWaitChannel(int N) {
        throw new UnsupportedOperationException("A completar.");
    }

    @Override
    public void send(TCPSegment seg) {
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
