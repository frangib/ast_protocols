package E03SolucioZonaCritica;

public class MutexPeterson implements Mutex {

    private volatile long ultim;
    private volatile boolean[] flag;

    public MutexPeterson() {
        flag = new boolean[2];
    }

    @Override
    public void entrar_zc() {
        int idmeu = ((MyFil) (Thread.currentThread())).getFilId();
        setFlag(idmeu, true);
        ultim = idmeu;
        while (flag[1 - idmeu] && ultim == idmeu);
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
