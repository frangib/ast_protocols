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

    //En este no hace falta array de pistas ocupadas.
    public ElPrat(int N) {
        this.N = N;
        lk = new ReentrantLock();
        grounded = lk.newCondition();
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
            busyAirspace = true;
            ordreEnl = ordreEnl + numPista;
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
            if (busyAirspace) {
                ordreEnl = ordreEnl;
            } else {
                ordreEnl = ordreEnl + "*";
            }
            busyAirspace = false;
        } catch (Exception ex) {

        } finally {
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
