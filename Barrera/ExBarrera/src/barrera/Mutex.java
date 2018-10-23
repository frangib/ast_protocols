package barrera;

import java.util.concurrent.atomic.AtomicBoolean;

public class Mutex {

    private final AtomicBoolean ts = new AtomicBoolean(false);
    
    public void entrar_zc() {
        while (ts.getAndSet(true));
    }
    
    public void sortir_zc() {
        ts.set(false);
    }
}
