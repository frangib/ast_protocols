package E03SolucioZonaCritica;

public class Test_Inc_NFils extends MyFil {

    @Override
    public void run() {
        int N = 10000;
        int M = 10;
        Comptador c = new Comptador(0);

        FilInc[] fil = new FilInc[M];
        for (int i = 0; i < M; i++) {
            fil[i] = new FilInc(N, c, i);
            fil[i].start();
        }

        try {
            for (int i = 0; i < M; i++) {
                fil[i].join();
            }
        } catch (Exception e) {
        }
        System.out.println("fi simulació");
        System.out.println("El valor de num és: " + c.getValor());
    }

    public static void main(String[] args) {
        new Test_Inc_NFils().start();
    }
}
