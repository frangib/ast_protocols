/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ast.practica3;

import ast.protocols.tcp.TCPSegment;
import ast.util.CircularQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author franSSD
 */
public class MonitorChannel implements Channel {

    public static final int MAX_MSG_SIZE = 1480; // Link MTU − IP header
    protected ReentrantLock lk;
    protected Condition esperarNoBuida, esperarNoPlena;
    protected CircularQueue<TCPSegment> q;
    protected double lossRatio;
    protected TCPSegment seg;

    public MonitorChannel(int capacitat, double lossRatio) {
        lk = new ReentrantLock();
        esperarNoBuida = lk.newCondition();
        esperarNoPlena = lk.newCondition();
        q = new CircularQueue<TCPSegment>(capacitat);
        seg = new TCPSegment();
        this.lossRatio = lossRatio;

    }

    public int getMMS() {
        return MAX_MSG_SIZE;
    }

    public void send(TCPSegment seg) {
        lk.lock();
        try {
            while (q.full()) {
                try {
                    esperarNoPlena.await();
                } catch (InterruptedException ex) {
                    Logger.getLogger(TSocketRecv.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (Math.random() > lossRatio) {
                q.put(seg);
                esperarNoBuida.signalAll();
            }
        } catch (Exception ex) {

        } finally {
            lk.unlock();
        }

    }

    public TCPSegment receive() {
        lk.lock();
        try {

            while (q.empty()) {
                try {
                    esperarNoBuida.await();
                } catch (InterruptedException ex) {
                    Logger.getLogger(TSocketRecv.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            seg = q.get();
            esperarNoPlena.signalAll();

        } catch (Exception ex) {

        } finally {
            lk.unlock();
            return seg;
        }
    }
}
