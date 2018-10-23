package practica3;

import ast.protocols.tcp.TCPSegment;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import practica1.Channel;

public class MonitorChannel implements Channel {

    public static final int MAX_MSG_SIZE = 1480; // Link MTU - IP header

    //. . .
    private ReentrantLock lk;
    private Condition c;
    private double lossRatio;
    //TODO: El canal necesita una CircularQueue, ¿no? Sin embargo, nadie me
    //da una dimensión para la CircularQueue

    public MonitorChannel() {
        //. . .  
        lk = new ReentrantLock();
        c = lk.newCondition();
    }

    public MonitorChannel(double lossRatio) {
        //. . .
        lk = new ReentrantLock();
        c = lk.newCondition();
        this.lossRatio = lossRatio;
    }

    public void send(TCPSegment seg) {
        //. . .
        lk.lock();
        try{
            while(){
                try {
                    c.await();
                } catch (InterruptedException ex) {
                    Logger.getLogger(MonitorChannel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }finally{
            
        }
    }

    public TCPSegment receive() {
        //. . .
        return null;
    }

    public int getMMS() {
        return MAX_MSG_SIZE;
    }


}
