package practica4;
// declareu imports

import java.util.ArrayList;
import practica1.Channel;

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
            //...
            return new TSocketSend(this, localPort, remotePort);
        } finally {
            lk.unlock();
        }
    }
}
