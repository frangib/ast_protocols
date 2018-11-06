package AeroportOrdenat;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ElPrat {

    private final int N;
    private String ordreArr = "";
    private String ordreEnl = "";
    //...
    private boolean busyAirspace;
    private ReentrantLock lk;
    private Condition[] pistes;

    public ElPrat(int N) {
        this.N = N;
        busyAirspace = false;
        lk = new ReentrantLock();
        for (int i = 0; i < N; i++) {
            pistes[i] = lk.newCondition();
        }
    }

    public void permisEnlairar(int numPista) {
        //...
        ordreArr = ordreArr + numPista;
        //...
        ordreEnl = ordreEnl + numPista;
        lk.lock();
        try {
            while (busyAirspace) {
                for (int i = 0; i < N; i++) {
                    pistes[i].await();
                }
            }
            busyAirspace = true;
        } catch (Exception ex) {

        } finally {
            lk.unlock();
        }
    }

    public void fiEnlairar(int numPista) {
        //...
        ordreEnl = ordreEnl + "*";
        //...

        lk.lock();
        try {

        } catch (Exception ex) {

        } finally {
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
