
package ast.practica5;

// define imports

import ast.protocols.tcp.TCPSegment;

import ast.logging.Log;
import ast.logging.LogFactory;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.ArrayList;


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
        /*
        s’havia anomenat diferent en cadascuna de les classes corresponents a la
        part emissora i part receptora: openForOutput en ProtocolSend i 
        openForInput en ProtocolRecv.
        */
        lk.lock();
        try {
            // A completar per l'estudiant (veieu practica 4):
            return new TSocket(this,localPort, remotePort);
        } finally {
            lk.unlock();
        }
    }

    protected void ipInput(TCPSegment segment) {
        // A completar per l'estudiant (veieu practica 4):
        lk.lock();
        try {
            TSocket ts = this.getMatchingTSocket(segment.getSourcePort(), segment.getDestinationPort());
            ts.processReceivedSegment(segment);
        } catch (Exception ex) {

        } finally {
            lk.unlock();
        }
    }

    protected TSocket getMatchingTSocket(int localPort, int remotePort) {
        lk.lock();
        try {
            // A completar per l'estudiant (veieu practica 4):
            int i = 0;
            boolean noMatch = true;
            TSocket t = new TSocket(this,localPort, remotePort);
            while(noMatch){
                noMatch = !(sockets.get(i) == t);
                i++;
            }
            return sockets.get(i-1);
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
