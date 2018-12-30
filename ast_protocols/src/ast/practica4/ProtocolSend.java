package ast.practica4;

// declareu imports
import ast.practica3.Channel;
import java.util.ArrayList;

/**
 *
 * @author upcnet
 */
public class ProtocolSend extends ProtocolBase {

    protected ArrayList<TSocketSend> sockets;

    public ProtocolSend(Channel ch) {
        super(ch);
        sockets = new ArrayList<TSocketSend>();
    }

    public TSocketSend openForOutput(int localPort, int remotePort) {
        lk.lock();
        try {
            TSocketSend TSS = new TSocketSend(this, localPort, remotePort);
            sockets.add(TSS);
            return TSS;

        } finally {
            lk.unlock();
        }
    }
}
