package practica2;

import ast.protocols.tcp.TCPSegment;
import practica1.Channel;

public class BusyWaitChannel implements Channel {

    private volatile boolean cuaplena;
    private volatile boolean cuabuida;
    private CircularQueue<TCPSegment> cua;

    public BusyWaitChannel(int N) {
        //throw new UnsupportedOperationException("A completar.");
        cua = new CircularQueue<TCPSegment>(N);
        cuaplena = cua.full();
        cuabuida = cua.empty();
    }

    @Override
    public void send(TCPSegment seg) {
        //throw new UnsupportedOperationException("A completar.");
        cuaplena = cua.full();
        while (cuaplena);
        cua.put(seg);
        cuabuida = cua.empty();
//evitar cuaplena_buida        
    }

    @Override
    public TCPSegment receive() {
        //throw new UnsupportedOperationException("A completar.");
        TCPSegment seg;
        cuabuida = cua.empty();

        while (cuabuida);
        seg = cua.get();
        cuaplena = cua.full();
        

        
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

//FIXME: send() y receive() no funcionan bien.
