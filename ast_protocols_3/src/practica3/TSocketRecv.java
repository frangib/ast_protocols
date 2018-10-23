package practica3;

import ast.protocols.tcp.TCPSegment;
import java.util.logging.Level;
import java.util.logging.Logger;
import practica1.Channel;
import practica1.CircularQueue;

public class TSocketRecv extends TSocketBase {

    protected Thread thread;
    protected CircularQueue<TCPSegment> rcvQueue;
    protected int rcvSegConsumedBytes;
    // invariant: rcvQueue.empty() || rcvQueue.peekFirst().getDataLength() > rcvSegConsumedBytes

    public TSocketRecv(Channel ch) {
        super(ch);
        rcvQueue = new CircularQueue<TCPSegment>(20);
        rcvSegConsumedBytes = 0;
        thread = new Thread(new ReceiverTask());
        thread.start();

    }

    /**
     * Places received data in buf Veure descripciÃ³ detallada en Exercici 3!!
     */
    public int receiveData(byte[] buf, int offset, int length) {
        //TODO: return, bucle, check
        lk.lock(); 
        try {
            // wait until receive queue is not empty
            // fill buf with bytes from segments in rcvQueue
            // Hint: use consumeSegment!
            //...
            while(rcvQueue.empty()){
                appCV.await();
            }
            //Hay que hacer un bucle para los segmentos que sean. De aquí
            //while(){
                TCPSegment s;
                consumeSegment(buf,offset,length);
            //}
            //hasta aqui
            appCV.signal();

        } catch (InterruptedException ex) {
            Logger.getLogger(TSocketRecv.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            lk.unlock();
        }
        return 0;//TODO: return
    }

    protected int consumeSegment(byte[] buf, int offset, int length) {
        TCPSegment seg = rcvQueue.peekFirst();
        // get data from seg and copy to receiveData's buffer
        int n = seg.getDataLength() - rcvSegConsumedBytes;
        if (n > length) {
            // receiveData's buffer is small. Consume a fragment of the received segment
            n = length;
        }
        // n == min(length, seg.getDataLength() - rcvSegConsumedBytes)
        System.arraycopy(seg.getData(), seg.getDataOffset() + rcvSegConsumedBytes, buf, offset, n);
        rcvSegConsumedBytes += n;
        if (rcvSegConsumedBytes == seg.getDataLength()) {
            // seg is totally consumed. Remove from rcvQueue
            rcvQueue.get();
            rcvSegConsumedBytes = 0;
        }
        return n;
    }

    /**
     * TCPSegment arrival.
     *
     * @param rseg segment of received packet
     */
    protected void processReceivedSegment(TCPSegment rseg) {
        lk.lock();
        try {
            while(rcvQueue.full()){
                try {
                    appCV.await();
                } catch (InterruptedException ex) {
                    Logger.getLogger(TSocketRecv.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            TCPSegment seg = new TCPSegment();
            seg = rseg;
            rcvQueue.put(seg);
            appCV.signal();
        } finally {
            lk.unlock();
        }
    }

    class ReceiverTask implements Runnable {

        public void run() {
            while (true) {
                TCPSegment rseg = channel.receive();
                processReceivedSegment(rseg);
            }
        }
    }
}
//TODO: ¿Por qué no funcionan ni lk ni appCV?