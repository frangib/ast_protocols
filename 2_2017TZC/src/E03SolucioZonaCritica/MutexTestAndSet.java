package E03SolucioZonaCritica;

import java.util.concurrent.atomic.AtomicBoolean;

public class MutexTestAndSet implements Mutex {

    private final AtomicBoolean ocupat;

    public MutexTestAndSet() {
        ocupat = new AtomicBoolean(false);
    }

    @Override
    public void entrar_zc() {
        while (ocupat.getAndSet(true));
    }

    @Override
    public void sortir_zc() {
        ocupat.set(false);
    }
}
