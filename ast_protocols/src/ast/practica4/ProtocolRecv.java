package ast.practica4;

// declareu imports
import ast.practica3.Channel;
import ast.protocols.tcp.TCPSegment;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

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
            TSocketRecv tsr = new TSocketRecv(this, localPort, remotePort);
            sockets.add(tsr);
            return tsr;
        } finally {
            lk.unlock();
        }
    }

    protected void ipInput(TCPSegment segment) throws InterruptedException {
        lk.lock();
        try{
        TSocketRecv tsr;
        tsr = getMatchingTSocket(segment.getSourcePort(), segment.getDestinationPort());
        if (tsr != null) {
            tsr.processReceivedSegment(segment);
        }
        }finally{
            lk.unlock();
        }    
    }

    protected TSocketRecv getMatchingTSocket(int localPort, int remotePort) {
        lk.lock();
        try {
            TSocketRecv tsr = null;
            boolean flag = true;
            for (int i = 0; flag; i++) {
                if (localPort == sockets.get(i).localPort && remotePort == sockets.get(i).remotePort) {
                    tsr = sockets.get(i);
                    flag = true;
                }
            }
            return tsr;
        } finally {
            lk.unlock();
        }
    }

    class ReceiverTask implements Runnable {

        public void run() {
            while (true) {
                TCPSegment rseg = channel.receive();
                try {
                    ipInput(rseg);
                } catch (InterruptedException ex) {
                    Logger.getLogger(ProtocolRecv.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
