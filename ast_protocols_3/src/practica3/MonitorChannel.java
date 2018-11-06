package practica3;

import ast.protocols.tcp.TCPSegment;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import practica1.Channel;
import practica1.CircularQueue;

public class MonitorChannel implements Channel {

    public static final int MAX_MSG_SIZE = 1480; // Link MTU - IP header

    //. . .
    private ReentrantLock lk;
    private Condition c;
    private double lossRatio;
    private CircularQueue<TCPSegment> cua;
    private TCPSegment s;

    public MonitorChannel() {
        //. . .  
        lk = new ReentrantLock();
        c = lk.newCondition();
        this.lossRatio = 0;
        cua = new CircularQueue<>(500);
        s = new TCPSegment();
    }

    public MonitorChannel(double lossRatio) {
        //. . .
        this();
        this.lossRatio = lossRatio;
    }

    //Este constructor me lo he inventado yo (me lo ha dicho el profesor).

    public MonitorChannel(int N, double lossRatio) {
        //. . .
        this();//Ejecuta el constructor sin parámetros.
        this.lossRatio = lossRatio;
        this.cua = new CircularQueue<>(N);
    }

    public void send(TCPSegment seg) {
        //. . .
        lk.lock();
        try {
            while (cua.full()) {
                try {
                    c.await();
                } catch (InterruptedException ex) {
                    Logger.getLogger(MonitorChannel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } finally {
            //Canal amb pèrdues. TODO: Check si esta bien
            if (Math.random() > lossRatio) {
                s = seg;
                cua.put(s);
                c.signalAll();
            }else{
                c.signalAll();
            }
        }
    }

    public TCPSegment receive() {
        //. . .
        lk.lock();
        while (cua.empty()) {
            try {
                c.await();
            } catch (InterruptedException ex) {
                Logger.getLogger(MonitorChannel.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                s = cua.get();
                c.signalAll();
            }
        }
        return s;
    }

    public int getMMS() {
        return MAX_MSG_SIZE;
    }

    @Override
    public int getMSS() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
