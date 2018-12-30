/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ast.practica1;

import ast.protocols.tcp.TCPSegment;
import ast.util.CircularQueue;
import static java.lang.Thread.sleep;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lastusr39
 */
public class QueueChannel implements Channel {

    TCPSegment seg = new TCPSegment();
    CircularQueue<TCPSegment> q;

    public QueueChannel(int n) {
        q = new CircularQueue<TCPSegment>(n);
    }

    //Fa un put a la cua
    public void send(TCPSegment s) {
        while (q.full()) {
            try {
                sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(QueueChannel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        q.put(s);
    }

//fa un get de la cua
    public TCPSegment receive() {
        while(q.empty()) {
            try {
                sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(QueueChannel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return q.get();
    }
}


