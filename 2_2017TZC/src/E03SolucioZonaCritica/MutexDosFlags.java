package E03SolucioZonaCritica;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MutexDosFlags implements Mutex {

    private volatile boolean[] flag;

    public MutexDosFlags() {
        flag = new boolean[2];
    }

    @Override
    public void entrar_zc() {
        int idmeu = ((MyFil) (Thread.currentThread())).getFilId();
        if (flag[1 - idmeu] == false) {
            setFlag(idmeu, true);
        }else{
            setFlag(idmeu, false);
        }
        //setFlag(idmeu, true);<--- solució original
        //*<---- punt crític
        while (flag[1 - idmeu]);
        try {
            Thread.sleep(1);
        } catch (InterruptedException ex) {
            Logger.getLogger(MutexDosFlags.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void sortir_zc() {
        setFlag(((MyFil) (Thread.currentThread())).getFilId(), false);
    }

    //necessari perquè flag tingui comportament volatile.
    private void setFlag(int id, boolean f) {
        flag[id] = f;
        flag = flag;
    }
}
