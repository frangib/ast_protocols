package practica4;
// declareu imports

import ast.protocols.tcp.TCPSegment;
import practica1.CircularQueue;

/**
 * Socket for receiving endpoint.
 *
 * @author upcnet
 */
public class TSocketRecv extends TSocketBase {

    protected CircularQueue<TCPSegment> rcvQueue;
    protected int rcvSegUnc;

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
    }

    /**
     * Places received data in buf
     */
    public int receiveData(byte[] buf, int offset, int length) {
        //...
        lk.lock();
        int consumed = 0;
        try {
            byte[] aux = null;

            while (rcvQueue.empty()) {
                appCV.await();
            }

            while (!rcvQueue.empty()) {
                consumed += consumeSegment(buf, offset, length);
            }

        } catch (Exception ex) {
        } finally {
            appCV.signalAll();
            lk.unlock();
        }
        return consumed;

        //treu aquesta sentencia en completar el codi:
        //return -1;
    }

    protected int consumeSegment(byte[] buf, int offset, int length) {
        //...
        //TODO: ¿Por qué este métdo no es concurrente (lk.lock() etc)?
        //TODO: Ver si está bien. Comparar con el que nos dan en la P3
        TCPSegment seg = rcvQueue.peekFirst();
        int rcvSegConsumedBytes = rcvSegUnc;
        int n = seg.getDataLength() - rcvSegConsumedBytes;
        if (n > length) {
            n = length;
        }
        System.arraycopy(seg.getData(), seg.getDataOffset() + rcvSegConsumedBytes, buf, offset, n);
        rcvSegConsumedBytes += n;
        if (rcvSegConsumedBytes == n) {
            rcvQueue.get();
            rcvSegConsumedBytes = 0;
        }
        //Devuelve cuantos bytes ha consumido, no cuantos lleva consumidos.
        return n;
        //treu aquesta sentencia en completar el codi:
        //return -1;
    }

    /**
     * Segment arrival.
     *
     * @param rseg segment of received packet
     */
    protected void processReceivedSegment(TCPSegment rseg) {
        //...
        lk.lock();
        try{
            while(rcvQueue.full()){
                appCV.await();
            }
        }catch(Exception ex){
            
        }finally{
            appCV.signalAll();
            lk.unlock();
        }
    }
}
