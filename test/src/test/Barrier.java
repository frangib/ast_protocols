/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author franSSD
 */
public class Barrier {

    private ReentrantLock lk;
    private Condition arribats;
    private int numArribats, n, numSuperats;

    public Barrier(int n) {
        lk = new ReentrantLock();
        arribats = lk.newCondition();
        this.n = n;
        numArribats = 0;
        numSuperats = 0;
    }

    public void barrier_wait() {
        lk.lock();
        try {
            numArribats += 1;
            
            while (numArribats < n) {
                arribats.await();
            }
            numSuperats += 1;
            //numArribats -= 1;
            System.out.println("numSuperats = " + numSuperats + " / numArribats = " + numArribats);
            while (numSuperats <= n) {
                arribats.signalAll();
            }
        } catch (Exception ex) {

        } finally {
            lk.unlock();
        }
    }
}
