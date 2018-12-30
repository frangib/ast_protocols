/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

/**
 *
 * @author franSSD
 */
public class Test extends Thread {

    private final Barrier b;

    public Test(Barrier b) {
        this.b = b;
    }

    public void run() {
        try {
            sleep((int) (Math.random() * 20));
        } catch (InterruptedException ex) {

        }
        b.barrier_wait();

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        int M = 10;
        Barrier b = new Barrier(M);
        Test[] fil = new Test[M];
        for (int i = 0; i < M; i++) {
            fil[i] = new Test(b);
            fil[i].start();
        }
        try {
            for (int i = 0; i < M; i++) {
                fil[i].join();
            }
        } catch (InterruptedException e) {
        }
        System.out.println("fi simulació");
    }

}
