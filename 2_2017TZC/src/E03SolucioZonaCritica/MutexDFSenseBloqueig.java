package E03SolucioZonaCritica;

public class MutexDFSenseBloqueig implements Mutex {

    private volatile boolean[] flag;

    public MutexDFSenseBloqueig() {
        flag = new boolean[2];
    }

    @Override
    public void entrar_zc() {
        int idmeu = ((MyFil) (Thread.currentThread())).getFilId();
        setFlag(idmeu, true);
        while (flag[1 - idmeu]) {
            setFlag(idmeu, false);
            setFlag(idmeu, true);
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
