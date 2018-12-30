package ast.practica5;

// define imports
import ast.logging.Log;
import ast.logging.LogFactory;
import ast.practica4.TSocketRecv;
import ast.protocols.tcp.TCPSegment;
import ast.protocols.transportCO.test.ReceiverTask;
import ast.util.FDuplexChannel;
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author upcnet
 */
public class Protocol {

    public static Log log = LogFactory.getLog(Protocol.class);

    protected ArrayList<TSocket> sockets;
    protected Thread task;
    protected Lock lk;
    protected FDuplexChannel.Peer net;

    public Protocol(FDuplexChannel.Peer ch) {
        sockets = new ArrayList<TSocket>();
        task = new Thread(new ReceiverTask());
        task.start();
        lk = new ReentrantLock();
        net = ch;
    }

    public TSocket openWith(int localPort, int remotePort) {
        lk.lock();
        try {
            // A completar per l'estudiant (veieu practica 4):
            TSocket ts = new TSocket(this, localPort, remotePort);
            sockets.add(ts);
            return ts;

        } finally {
            lk.unlock();
        }
    }

    protected void ipInput(TCPSegment segment) {
        // A completar per l'estudiant (veieu practica 4):
        lk.lock();
        try {
            TSocket ts;
            ts = getMatchingTSocket(segment.getSourcePort(), segment.getDestinationPort());
            if (ts != null) {
                ts.processReceivedSegment(segment);
            }
        } finally {
            lk.unlock();
        }
    }

    protected TSocket getMatchingTSocket(int localPort, int remotePort) {
        lk.lock();
        try {
            // A completar per l'estudiant (veieu practica 4):
            TSocket ts = null;
            boolean flag = true;
            for (int i = 0; flag; i++) {
                if (localPort == sockets.get(i).localPort && remotePort == sockets.get(i).remotePort) {
                    ts = sockets.get(i);
                    flag = true;
                }
            }
            return ts;
        } finally {
            lk.unlock();
        }
        
    }

    class ReceiverTask implements Runnable {

        public void run() {
            while (true) {
                TCPSegment rseg = net.receive();
                ipInput(rseg);
            }
        }
    }

}
