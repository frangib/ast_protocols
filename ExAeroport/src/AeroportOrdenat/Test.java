package AeroportOrdenat;

public class Test extends Thread {

    private final ElPrat cd;
    private final int id;
    private static final int N = 3;

    public Test(ElPrat cd, int id) {
        this.id = id;
        this.cd = cd;
    }

    @Override
    public void run() {
        try {
            sleep((int) (Math.random() * 20));
        } catch (InterruptedException ex) {

        }
        cd.permisEnlairar(id % N);
        try {
            sleep((int) (Math.random() * 20));
        } catch (InterruptedException ex) {

        }
        cd.fiEnlairar(id % N);
    }

    public static void main(String[] args) {
        int M = 30;

        ElPrat c = new ElPrat(N);

        Test[] fil = new Test[M];
        for (int i = 0; i < M; i++) {
            fil[i] = new Test(c, i);
            fil[i].start();
        }
        try {
            for (int i = 0; i < M; i++) {
                fil[i].join();
            }
        } catch (InterruptedException e) {
        }
        c.mostrar();
        System.out.println("fi simulació");
    }
}
