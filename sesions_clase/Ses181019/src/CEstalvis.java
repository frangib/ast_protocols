
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class CEstalvis {

    private int quantitat = 0;
    ReentrantLock l = new ReentrantLock();
    Condition diners = l.newCondition();

    public void posar(int q) {
        //quatitat = quantitat + q;
        l.lock();
        try {
            quantitat = quantitat + q;
            diners.signalAll();
        } finally {
            l.unlock();
        }
    }

    public void treure(int q) {
        //quantitat = quantitat - q;
        l.lock();
        try {
            while (q > quantitat) {//<---- if??
                diners.awaitUninterruptibly();
            }
            quantitat = quantitat - q;
        } finally {
            l.unlock();
        }
    }
}
