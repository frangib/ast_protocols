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
    private CircularQueue<TCPSegment> q;

    public MonitorChannel() {
        //. . .  
        lk = new ReentrantLock();
        c = lk.newCondition();
        this.lossRatio = 0;
        q = new CircularQueue<>(500);
    }

    public MonitorChannel(double lossRatio) {
        //. . .
        this();
        this.lossRatio = lossRatio;
    }
    //Este constructor me lo he inventado yo (me lo ha dicho el profesor).
    //
    public MonitorChannel(int N, double lossRatio) {
        //. . .
        this();//Ejecuta el constructor sin parámetros.
        this.lossRatio = lossRatio;
    }

    public void send(TCPSegment seg) {
        //. . .
        lk.lock();
        try {
            while () {
                try {
                    c.await();
                } catch (InterruptedException ex) {
                    Logger.getLogger(MonitorChannel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } finally {

        }
    }

    public TCPSegment receive() {
        //. . .
        return null;
    }

    public int getMMS() {
        return MAX_MSG_SIZE;
    }

    @Override
    public int getMSS() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
