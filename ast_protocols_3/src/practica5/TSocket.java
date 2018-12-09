package practica5;

import ast.logging.Log;
import practica5.Protocol;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import ast.protocols.tcp.TCPSegment;
import java.util.logging.Level;
import java.util.logging.Logger;
import practica1.CircularQueue;

/**
 * @author AST's professors
 */
public class TSocket {

    public static Log log = Protocol.log;
    protected static final int RCV_QUEUE_SIZE = 3;

    protected Protocol proto;
    protected Lock lk;
    protected Condition appCV;

    protected int localPort;
    protected int remotePort;

    // Sender variables:
    protected int sndMSS;       // Send maximum segment size
    protected boolean segmentAcknowledged; // segment not yet acknowledged ?
    protected int rcvWindow;

    // Receiver variables:
    protected CircularQueue<TCPSegment> rcvQueue;
    protected int rcvSegConsumedBytes;

    //Other atributes (sender or receiver)
    //...
    protected String sndIsUna;
    TCPSegment rcvSegment;
    /**
     * Create an endpoint bound to the given TCP ports.
     */
    protected TSocket(Protocol p, int localPort, int remotePort) {
        lk = new ReentrantLock();
        appCV = lk.newCondition();
        proto = p;
        this.localPort = localPort;
        this.remotePort = remotePort;
        // init sender variables
        sndMSS = p.net.getMMS() - TCPSegment.HEADER_SIZE; // IP maximum message size - TCP header size
        segmentAcknowledged = false;
        // init receiver variables
        rcvQueue = new CircularQueue<TCPSegment>(RCV_QUEUE_SIZE);
        rcvSegConsumedBytes = 0;
        rcvWindow = RCV_QUEUE_SIZE;
        //Other necessary initializations
        //...
        sndIsUna = "";
        rcvSegment = new TCPSegment();
    }

    // -------------  SENDER PART  ---------------
    public void sendData(byte[] data, int offset, int length) {
        lk.lock();
        try {
            log.debug("%s->sendData(length=%d)", this, length);
            // A completar per l'estudiant:
            /*
            El m`etode sendData, que ´es el que envia els segments (tal com
            es feia a la pr`actica 4), haur`a d’esperar a 
            segmentAcknowledged == true abans d’enviar el seg¨uent segment de
            dades. Observeu que, complement`ariament, no es podr`a enviar un 
            nou segment de dades si no hi ha espai lliure a la cua del receptor.
             */
            while (segmentAcknowledged == false || rcvQueue.full()) {
                try {
                    appCV.await();
                } catch (InterruptedException ex) {
                }
            }
            //El resto del método es el mismo que en la P4
            TCPSegment s;
            int i;
            //Habra dos casos: si length es menor o igual que sndMSS o si es mayor
            System.out.println("Llega con length/sndMSS ==> " + length + "/" + sndMSS);
            if (length <= sndMSS) {
                this.segmentize(data, offset, length);
            } else {
                for (i = 0; i < Math.floor((double) length / sndMSS); i++) {
                    //TODO: CHECK length - i*sndMSS. No me cuadra
                    s = this.segmentize(data, offset + i * sndMSS, length - i * sndMSS);
                    //TODO: Este print lo pongo yo para comprobar.
                    System.out.println("Llega3 con length/por enviar ==> " + length + "/" + (length - ((i + 1) * sndMSS)));
                }
                s = this.segmentize(data, offset + i * sndMSS, length - i * sndMSS);
            }
            // for each segment to send
            // wait until the sender is not expecting an acknowledgement
            // create a data segment and send it
        } finally {
            appCV.signalAll();//Esta linea no está en la P4.
            lk.unlock();
        }
    }

    protected TCPSegment segmentize(byte[] data, int offset, int length) {
        // A completar per l'estudiant (veieu practica 4):
        //El método es el mismo que en la P4
        TCPSegment seg = new TCPSegment();
        //...
        byte[] aux = new byte[length];

        System.arraycopy(data, offset, aux, 0, length);
        seg.setData(aux, 0, length);
        return seg;
    }

    protected void sendSegment(TCPSegment segment) {
        log.debug("%s->sendSegment(%s)", this, segment);
        proto.net.send(segment);
    }

    // -------------  RECEIVER PART  ---------------
    /**
     * Places received data in buf
     */
    public int receiveData(byte[] buf, int offset, int maxlen) {
        lk.lock();
        try {
            byte[] aux = null;
            int consumed = 0;
            log.debug("%s->receiveData(maxlen=%d)", this, maxlen);
            // A completar per l'estudiant:
            while (rcvQueue.empty()) {
                try {
                    appCV.await();
                } catch (InterruptedException ex) {
                }
            }

            while (rcvQueue.empty()) {
                try {
                    appCV.await();
                } catch (InterruptedException ex) {
                }
            }

            while (!rcvQueue.empty()) {
                consumed += consumeSegment(buf, offset, maxlen);
            }
            // wait until there is a received segment
            // get data from the received segment
            return consumed;
        } finally {
            appCV.signalAll();
            lk.unlock();
        }
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

    protected void sendAck() {
        // A completar per l'estudiant:
        TCPSegment ack = new TCPSegment();
        ack.setAck(true);
        proto.net.send(ack);
    }

    // -------------  SEGMENT ARRIVAL  -------------
    /**
     * Segment arrival.
     *
     * @param rseg segment of received packet
     */
    protected void processReceivedSegment(TCPSegment rseg) {
        lk.lock();
        try {
            // Check ACK
            if (rseg.isAck()) {
                // A completar per l'estudiant:
                segmentAcknowledged = true;
                logDebugState();
            } else if (rseg.getDataLength() > 0) {
                // Process segment data
                if (rcvQueue.full()) {
                    log.warn("%s->processReceivedSegment: no free space: %d lost bytes",
                            this, rseg.getDataLength());
                    return;
                }
                // A completar per l'estudiant:
                while (rcvQueue.empty()) {
                    try {
                        appCV.await();
                    } catch (InterruptedException ex) {
                    }
                }
                while (!rcvQueue.empty()) {
                    rcvQueue.put(rseg);
                }
                logDebugState();
            }
        } finally {
            appCV.signalAll();
            lk.unlock();
        }
    }

    // -------------  LOG SUPPORT  ---------------
    protected void logDebugState() {
        if (log.debugEnabled()) {
            log.debug("%s=> state: %s", this, stateToString());
        }
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append(proto.net.getAddr()).append("/{local=").append(localPort);
        buf.append(",remote=").append(remotePort).append("}");
        return buf.toString();
    }

    public String stateToString() {
        StringBuilder buf = new StringBuilder();
        buf.append("{sndIsUna=").append(sndIsUna);
        if (rcvSegment == null) {
            buf.append(",rcvSegment=null");
        } else {
            buf.append(",rcvSegment.dataLength=").append(rcvSegment.getDataLength());
            buf.append(",rcvSegConsumedBytes=").append(rcvSegConsumedBytes);
        }
        return buf.append("}").toString();
    }

}
