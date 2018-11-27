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
    private boolean[] runwayOccupied;

    //En este no hace falta array de pistas ocupadas.
    public ElPrat(int N) {
        this.N = N;
        lk = new ReentrantLock();
        grounded = lk.newCondition();
        runwayOccupied = new boolean[N];
        for (int i = 0; i < N; i++) {
            runwayOccupied[i] = false;
        }

    }

    public void permisEnlairar(int numPista) {
        lk.lock();
        try {
            //Ordre d'arribada
            ordreArr = ordreArr + numPista;
            /*
             Si l'espai aeri esta ocupat, que els avions s'esperin
             */
            while (this.busyAirspace(runwayOccupied)) {
                grounded.await();
            }
            /*
             Si l'espai aeri no esta ocupat, l'avio que demana la pista numPista
             pot enalirarse. S'actualitza l'estat de la pista a ocupada i 
             s'afegeix numPista a l'ordre d'enalairament.
             */
            runwayOccupied[numPista] = true;
            ordreEnl = ordreEnl + numPista;

        } catch (Exception ex) {

        } finally {
            lk.unlock();
        }
    }

    public void fiEnlairar(int numPista) {
        lk.lock();
        try {
            /*
             Que s'acabai l'enalirament vol dir que la pista numPista passa
             a estar lliure.
             */
            runwayOccupied[numPista] = false;
            /*
             Si després d'acabar l'enalairament totes les pistes estan lliures,
             imprimir ordreEnl+"*", que indica que totes les pistes estan lliures.
             Sino imprimeix només l'ordre d'enalirament.
             */
            if (this.busyAirspace(runwayOccupied)) {
                ordreEnl = ordreEnl + "*";
            }

        } catch (Exception ex) {

        } finally {
            /*
            Un cop acabat l'enalirament cal despertar els avions que estaven
            esperant per enalairar-se (grounded).
            */
            grounded.signalAll();
            lk.unlock();
        }
    }

    /*
     Aquest mètode mira si l'espai aeri esta ocupat o no.
     PISTA OCUPADA = TRUE
     PISTA LLIURE = FALSE
     ////////////////////
     retorna check
     check = FALSE si TOTES PISTES LLIURES
     check = TRUE si AL MENYS UNA PISTA OCUPADA
     */
    public boolean busyAirspace(boolean[] pistes) {
        boolean check = false;
        int i = 0;
        
        while (check == false && i < N) {
            check = pistes[i];
            i++;
        }
        
        return check;
    }

    public void mostrar() {
        System.out.println("**********************************************");
        System.out.println("***********VEURE COMENTARIS AL CODI***********");
        System.out.println("**********************************************");
        System.out.println();

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
