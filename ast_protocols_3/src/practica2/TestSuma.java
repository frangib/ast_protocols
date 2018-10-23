package practica2;

public class TestSuma {

    public static void main(String[] args) throws InterruptedException {
        Fil fA = new Fil();
        Fil fB = new Fil();
        fA.start();
        fB.start();
        fA.join();
        fB.join();
        System.out.println("Valor final de x: " + Fil.x);
    }
}

class Fil extends Thread {

    public static volatile int x;
    private final int I = 2000;

    @Override
    public void run() {
        int R;
        for (int i = 0; i < I; i++) {
            x = x + 1;
        }
    }
}
