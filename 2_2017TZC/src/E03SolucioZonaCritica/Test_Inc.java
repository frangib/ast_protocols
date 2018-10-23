package E03SolucioZonaCritica;

public class Test_Inc {

    public static void main(String[] args) {
        int N = 10000;
        Comptador c = new Comptador(0);

        FilInc mt1 = new FilInc(N, c, 0);
        FilInc mt2 = new FilInc(N, c, 1);

        mt1.start();
        mt2.start();

        try {
            mt1.join();
            mt2.join();
        } catch (Exception e) {
        }
        System.out.println("fi simulació");
        System.out.println("El valor de num és: " + c.getValor());
    }
}
