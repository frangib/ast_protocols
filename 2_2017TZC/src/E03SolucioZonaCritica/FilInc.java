package E03SolucioZonaCritica;

public class FilInc extends MyFil {

    private final int N;
    private final Comptador c;

    public FilInc(int N, Comptador c, int id) {
        this.N = N;
        this.c = c;
        this.id = id;
    }

    @Override
    public void run() {
        for (int i = 0; i < N; i++) {
            c.inc();
        }
    }
}
