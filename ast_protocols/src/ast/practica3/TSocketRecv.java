/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ast.practica3;

import ast.protocols.tcp.TCPSegment;
import ast.protocols.transportCO.test.ReceiverTask;
import ast.util.CircularQueue;
import java.util.concurrent.locks.Condition;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lastusr39
 */
public class TSocketRecv extends TSocketBase {

    protected Thread thread;
    protected CircularQueue<TCPSegment> rcvQueue;
    protected int rcvSegConsumedBytes;
    protected Condition CuaNoBuida;

    public TSocketRecv(Channel ch) {
        super(ch);
        rcvQueue = new CircularQueue<TCPSegment>(20);
        rcvSegConsumedBytes = 0;
        thread = new Thread(new ReceiverTask());
        thread.start();
    }

    public int receiveData(byte[] buf, int offset, int length) throws InterruptedException {
        lk.lock();
        try {
            int llegits = 0;
            while (rcvQueue.empty()) {
                try {
                    CuaNoBuida.await();
                } catch (InterruptedException ex) {
                    Logger.getLogger(TSocketRecv.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            while (llegits < length) {
                llegits = consumeSegment(buf, offset, length);

            }
            return llegits;
        } finally {
            lk.unlock();
        }
    }

    protected int consumeSegment(byte[] buf, int offset, int length) {
        TCPSegment seg = rcvQueue.peekFirst();

        int n = seg.getDataLength() - rcvSegConsumedBytes;
        if (n > length) {
            n = length;
        }

        System.arraycopy(seg.getData(), seg.getDataOffset() + rcvSegConsumedBytes, buf, offset, n);
        rcvSegConsumedBytes += n;
        if (rcvSegConsumedBytes == seg.getDataLength()) {
            rcvQueue.get();
            rcvSegConsumedBytes = 0;
        }
        return n;
    }
}
