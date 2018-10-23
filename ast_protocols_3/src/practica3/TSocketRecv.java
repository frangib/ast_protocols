
package practica3;

import ast.protocols.tcp.TCPSegment;
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
     * Places received data in buf
     * Veure descripciÃ³ detallada en Exercici 3!!
     */
    public int receiveData(byte[] buf, int offset, int length) {
        lk.lock(); //TODO: Preguntar si no se han olvidado de declarar lk.
        try {
            // wait until receive queue is not empty

              . . .

            // fill buf with bytes from segments in rcvQueue
            // Hint: use consumeSegment!

              . . .

        } finally {
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

    /**
     * TCPSegment arrival.
     * @param rseg segment of received packet
     */
    protected void processReceivedSegment(TCPSegment rseg) {
        lk.lock();
        try {

           . . .

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

