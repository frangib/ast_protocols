
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ComptadorAcotat {

    private final int MAX;
    private final int MIN;
    protected int valor;

    private final ReentrantLock l = new ReentrantLock();
    private final Condition decC = l.newCondition();
    private final Condition incC = l.newCondition();
    

    public ComptadorAcotat(int MIN, int MAX) {
        this.MIN = MIN;
        this.MAX = MAX;
        valor = MIN;
    }

    public void inc() {
        l.lock();
        try {
            while(valor == MAX){
                decC.awaitUninterruptibly();//<-- esperar dec
            }
            valor = valor + 1;
            incC.signal();
            if (valor > MAX) {
                System.out.println("Error > MAX");
            }
        } finally {
            l.unlock();
        }
    }

    public void dec() {
        l.lock();
        try {
            while(valor == MIN){
                incC.awaitUninterruptibly();
            }
            valor = valor - 1;
            decC.signal();
            if (valor < MIN) {
                System.out.println("Error < MIN");
            }
        } finally {
            l.unlock();
        }
    }

}
