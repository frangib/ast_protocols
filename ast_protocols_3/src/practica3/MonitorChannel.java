
package practica3;

import practica1.Channel;


public class MonitorChannel implements Channel {

    . . .

    public static final int MAX_MSG_SIZE = 1480; // Link MTU - IP header
                                                 
    . . .

    public MonitorChannel() {  . . .  }

    public MonitorChannel(double lossRatio) {
    . . .
    }


    public void send(TCPSegment seg) {  . . .  }

    public TCPSegment receive(){  . . .  }

    public int getMMS() {
        return MAX_MSG_SIZE;
    }

}

