
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Barrera {

    private final ReentrantLock l = new ReentrantLock();
    private final Condition ocupat = l.newCondition();
    private final Condition altres = l.newCondition();
    private final int B = 5;
    private int arribats = 0;
    private int acabats = 0;

    public void entrar(int id) {
        l.lock();
        try {
            while (arribats == B) {
                ocupat.awaitUninterruptibly();
            }
            arribats = arribats + 1;
            while (arribats < B) {
                altres.awaitUninterruptibly();
            }
            //<-- **
            altres.signal();
            acabats = acabats + 1;
            if (acabats == B) {
                acabats = 0;
                arribats = 0;
                ocupat.signalAll();//<-- signalB
            }
        } finally {
            l.unlock();
        }
    }
}
