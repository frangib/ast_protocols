package AeroportOrdenat;

import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ElPrat {

    private final int N;
    private String ordreArr = "";
    private String ordreEnl = "";
    //...
    private boolean busyAirspace;
    private ReentrantLock lk;
    private final ArrayList<Condition> esp;
    private boolean[] runwayOccupied;

    public ElPrat(int N) {
        this.N = N;
        busyAirspace = false;
        lk = new ReentrantLock();
        esp = new ArrayList<>();
        runwayOccupied = new boolean[N];
        for (int i = 0; i < N; i++) {
            runwayOccupied[i] = false;
        }
    }

    public void permisEnlairar(int numPista) throws Exception {
        lk.lock();
        ordreArr = ordreArr + numPista;
        try {
            Condition c = null;
            /*
             Si l'espai aeri està ocupat o hi ha algú esperant i l'avio acaba d'arribar s'ha d'esperar
             */
            while (this.busyAirspace(runwayOccupied) || esp.size() > 1 && c == null) {
                try {
                    /*
                     Si c es null vol dir que es el primer co pque m'aturo. Per tant
                     es crea la variable de condicio i es posa a la llista ordenada.
                     */
                    if (c == null) {
                        c = lk.newCondition();
                        esp.add(c);
                    }
                    c.await();
                } catch (InterruptedException ex) {

                }
            }

            if (c != null) {
                esp.remove(c);
                if (esp.size() > 0) {
                    esp.get(0).signal();
                }
            }
            ordreEnl = ordreEnl + numPista;

        } catch (Exception ex) {

        } finally {
            lk.unlock();
        }
    }

    public void fiEnlairar(int numPista) {
        lk.lock();
        try {

            runwayOccupied[numPista] = false;
            if(this.busyAirspace == false){
                ordreEnl = ordreEnl + "*";
            }
            
        } catch (Exception ex) {

        } finally {
            lk.unlock();
        }
    }

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
