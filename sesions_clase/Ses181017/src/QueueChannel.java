
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

public class QueueChannel {

    private CuaCirc<Integer> q;
    private ReentrantLock l = new ReentrantLock();
    private Condition vcGet = l.newCondition();
    private Condition vcPut = l.newCondition();

    public QueueChannel(int N) {
        q = new CuaCirc(N);
    }

    public void send(Integer e) {
        l.lock(); //z.entrar_zc();
        try {
            while (q.full()) {//<--ZC
                /*z.sortir_zc();
                 while (q.full());
                 z.entrar_zc();*/
                vcPut.awaitUninterruptibly();//<--**
            }
            //<--- ***** ZC    
            q.put(e);//<-- ZC
            vcGet.signal();
        } finally {
            l.unlock();//z.sortir_zc();
        }
    }

    public Integer receive() {
        l.lock(); //z.entrar_zc();
        try {
            Integer e = null;

            while (q.empty()) {//<-- ZC
                /*z.sortir_zc();
                 while (q.empty());
                 z.entrar_zc();*/
                vcGet.awaitUninterruptibly();
            }
            e = q.get();//<-- ZC
            vcPut.signal();
            return e;
        } finally {
            l.unlock();
        }

    }

    public void me() {
        l.lock();
        try {
            //.. codi mètode
        } finally {
            l.unlock();
        }
    }
}
