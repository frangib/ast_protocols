package ast.practica4;

// declareu imports
import ast.protocols.tcp.TCPSegment;
import ast.util.CircularQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Socket for receiving endpoint.
 *
 * @author upcnet
 */
public class TSocketRecv extends TSocketBase {

    protected CircularQueue<TCPSegment> rcvQueue;
    protected int rcvSegUnc;
    protected ReentrantLock lk;
    protected Condition CuaNoBuida;

    /**
     * Create an endpoint bound to the local IP address and the given TCP port.
     * The local IP address is determined by the networking system.
     *
     * @param ch
     */
    protected TSocketRecv(ProtocolRecv p, int localPort, int remotePort) {
        super(p, localPort, remotePort);
        rcvQueue = new CircularQueue<TCPSegment>(20);
        rcvSegUnc = 0;
        lk = new ReentrantLock();
        CuaNoBuida = lk.newCondition();
    }

    /**
     * Places received data in buf
     */
    public int receiveData(byte[] buf, int offset, int length) {
        lk.lock();
        try {
            int llegits = 0;
            while (rcvQueue.empty()) {
                try {
                    CuaNoBuida.await();
                } catch (InterruptedException ex) {
                    Logger.getLogger(ast.practica3.TSocketRecv.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            while (llegits < length) {
                llegits = consumeSegment(buf, offset, length);
                offset = offset + llegits;
            }
            return llegits;
        } finally {
            lk.unlock();
        }
    }

    protected int consumeSegment(byte[] buf, int offset, int length) {
        lk.lock();
        try {
            TCPSegment seg = rcvQueue.peekFirst();

            int n = seg.getDataLength() - rcvSegUnc;
            if (n > length) {
                n = length;
            }

            System.arraycopy(seg.getData(), seg.getDataOffset() + rcvSegUnc, buf, offset, n);
            rcvSegUnc += n;
            if (rcvSegUnc == seg.getDataLength()) {
                rcvQueue.get();
                rcvSegUnc = 0;
            }
            return n;
        }finally{
            lk.unlock(); 
        }
        
        
    }

    /**
     * Segment arrival.
     *
     * @param rseg segment of received packet
     */
    protected void processReceivedSegment(TCPSegment rseg) throws InterruptedException {
        lk.lock();
        try {
            if(!rcvQueue.full()){ 
                rcvQueue.put(rseg);
                CuaNoBuida.signalAll();
            }
        } finally {
            lk.lock();
        }
    }
}
