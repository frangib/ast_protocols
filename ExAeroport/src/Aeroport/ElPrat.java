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
    private boolean busyRunway[];
    private boolean allEmpty;

    public ElPrat(int N) {
        this.N = N;
        lk = new ReentrantLock();
        grounded = lk.newCondition();
        for(int i = 0; i < N; i++){
            busyRunway[i] = false;
        }  
        allEmpty = true;
    }

    public void permisEnlairar(int numPista) {
//        //...
//        ordreArr = ordreArr + numPista;
//        //...
//        ordreEnl = ordreEnl + numPista;
        lk.lock();
        try {
            ordreArr = ordreArr + numPista;
            //TODO: no estoy seguro de si esta es la mejor forma de seguir.
            //Quizá es mejor hacer un array de variables de condición?
            
            for(int i = 0; !allEmpty; i++){
                allEmpty = busyRunway[i];
            }
            while (allEmpty) {
                grounded.await();
            }
            ordreEnl = ordreEnl + numPista;
            busyRunway[numPista] = true;
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
            busyRunway[numPista] = false;
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
