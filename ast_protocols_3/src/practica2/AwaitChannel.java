package practica2;

import ast.protocols.tcp.TCPSegment;
import java.util.concurrent.atomic.AtomicBoolean;
import practica1.Channel;

public class AwaitChannel implements Channel {

    private CircularQueue<TCPSegment> cua;
    private TestAndSet m;

    public AwaitChannel(int N) {
        //throw new UnsupportedOperationException("A completar.");
        cua = new CircularQueue<>(N);
        m = new TestAndSet();
    }

    @Override
    public void send(TCPSegment seg) {
        //throw new UnsupportedOperationException("A completar.");
        //Await structure
        System.out.println("LLEGA PROCESS AL SEND");
        m.entrarZC();//Entrar ZC
        while (cua.full()) {
            m.sortirZC();
            m.entrarZC();
        }
        
        cua.put(seg);
        System.out.println("PUT PUT PUT PUT");
        System.out.println(cua.size());
        m.sortirZC();//Sortir ZC
    }

    @Override
    public TCPSegment receive() {
        //throw new UnsupportedOperationException("A completar.");
        TCPSegment seg;
        //Await structure
        System.out.println("LLEGA PROCESS AL RECEIVE");
        m.entrarZC();//Entrar ZC
        while (cua.empty()) {
            m.sortirZC();
            m.entrarZC();
        }
        seg = cua.get();
        System.out.println("GET GET GET GET");
        System.out.println(cua.size());
        m.sortirZC();//Sortir ZC

        return seg;
    }

    @Override
    public int getMSS() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}

//FIXME: PREGUNTAR PROFESOR. No puedo seguir encallado aquí
//Quacircular funciona bien. He hecho estructura await. ¿Qué pasa?
