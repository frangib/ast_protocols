package Aeroport;

import static java.lang.Math.ceil;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ElPrat {

    private final int N;
    private String ordreArr = "";
    private String ordreEnl = "";
    //...
    private ReentrantLock lk;
    private Condition grounded;
    private boolean busyAirspace;

    public ElPrat(int N) {
        this.N = N;
        lk = new ReentrantLock();
        for (int i = 0; i < N; i++) {
            grounded = lk.newCondition();
        }
        busyAirspace = false;
    }

    public void permisEnlairar(int numPista) {
//        //...
//        ordreArr = ordreArr + numPista;
//        //...
//        ordreEnl = ordreEnl + numPista;

        lk.lock();
        try {
            ordreArr = ordreArr + numPista;
            while (busyAirspace) {
                grounded.await();
            }
            ordreEnl = ordreEnl + numPista;
            busyAirspace = true;
        } catch (Exception ex) {
        } finally {
            lk.unlock();
        }
    }

    public void fiEnlairar(int numPista) {
//        //...
//        ordreEnl = ordreEnl + "*";
//        //...

        lk.lock();
        try {
            busyAirspace = false;
        } catch (Exception ex) {
        } finally {
            ordreEnl = ordreEnl + "*";
            grounded.signal();
            lk.unlock();
        }
    }

    public void mostrar() {
        System.out.println(ordreArr);
        String ordreEnlNet = ordreEnl.replace("*", "");
        System.out.println(ordreEnlNet);
        System.out.println(ordreEnl);
        if (ordreArr.equals(ordreEnlNet)) {
            System.out.println("S'han enlairat per ordre de petició");
        } else {
            System.out.println("NO s'han enlairat per ordre de petició");
        }
    }

}
