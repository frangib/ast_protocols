package E03SolucioZonaCritica;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Comptador {

    private int valor;
    private final Mutex m;

    public Comptador(int v) {
        valor = v;
        //m = new MutexOcupat();
        m = new MutexDosFlags();
        //m = new MutexDFSenseBloqueig();
        //m = new MutexPeterson();
        //m = new MutexTestAndSet();
    }

    public int getValor() {
        int tmp;
        m.entrar_zc();
        tmp = valor;
        m.sortir_zc();
        return tmp;
    }

    public void setValor(int valor) {
        m.entrar_zc();
        this.valor = valor;
        m.sortir_zc();
    }

    public void inc() {
        m.entrar_zc();
        int tmp=valor;
        try {
            Thread.sleep(1);
        } catch (InterruptedException ex) {
            Logger.getLogger(Comptador.class.getName()).log(Level.SEVERE, null, ex);
        }
        tmp = tmp+1;
        valor = tmp;
        //valor = valor + 1 <--- original
        m.sortir_zc();

    }

    public void dec() {
        m.entrar_zc();
        valor = valor - 1;
        m.sortir_zc();
    }

}
