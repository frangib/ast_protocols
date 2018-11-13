package practica4;
// declareu imports

import ast.protocols.tcp.TCPSegment;
import java.util.ArrayList;
import practica1.Channel;

/**
 *
 * @author upcnet
 */
public class ProtocolRecv extends ProtocolBase {

    protected Thread task;
    protected ArrayList<TSocketRecv> sockets;

    public ProtocolRecv(Channel ch) {
        super(ch);
        sockets = new ArrayList<TSocketRecv>();
        task = new Thread(new ReceiverTask());
        task.start();
    }

    public TSocketRecv openForInput(int localPort, int remotePort) {
        lk.lock();
        try {
            //... 
            return new TSocketRecv(this, localPort, remotePort);
        } finally {
            lk.unlock();
        }
    }

    protected void ipInput(TCPSegment segment) {
        //...
        lk.lock();
        try {
            TSocketRecv tsr = this.getMatchingTSocket(segment.getSourcePort(), segment.getDestinationPort());
            tsr.processReceivedSegment(segment);
        } catch (Exception ex) {

        } finally {
            lk.unlock();
        }

    }

    protected TSocketRecv getMatchingTSocket(int localPort, int remotePort) {
        lk.lock();
        try {
            //...
            boolean noMatch = true;
            int i = 0;
            TSocketRecv tsr = new TSocketRecv(this, localPort, remotePort);
            while (noMatch) {
                noMatch = !(sockets.get(i) == tsr);
                i++;
            }
            return sockets.get(i - 1);
        } finally {
            lk.unlock();
        }
    }

    class ReceiverTask implements Runnable {
        /*
         la classe ReceiverTask dins de ProtocolRecv es l’encarregada d’agafar
         els segments que arriben de la xarxa per passar-los al socket corresponent.
         */

        public void run() {
            while (true) {
                TCPSegment rseg = channel.receive();
                ipInput(rseg);
            }
        }
    }

}
