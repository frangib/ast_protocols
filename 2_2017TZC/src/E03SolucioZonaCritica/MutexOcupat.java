package E03SolucioZonaCritica;

public class MutexOcupat implements Mutex {

    private volatile boolean ocupat = false;

    @Override
    public void entrar_zc() {
        //ocupat = true <--- aquí no hi pot anar ja que autobloqueja
        while (ocupat);
        //*<----- punt crític!!
        ocupat = true;
    }

    @Override
    public void sortir_zc() {
        ocupat = false;
    }
}
