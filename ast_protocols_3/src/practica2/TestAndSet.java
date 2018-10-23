package practica2;

import java.util.concurrent.atomic.AtomicBoolean;

public class TestAndSet {

    private final AtomicBoolean ts = new AtomicBoolean(false);

    public void entrarZC() {
        while (ts.getAndSet(true));
    }

    public void sortirZC() {
        ts.getAndSet(false);
    }
}
