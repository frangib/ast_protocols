package barrera;

public class Fil extends Thread {

    private final Barrera b;
    private final int id;

    public Fil(Barrera b, int id) {
        this.b = b;
        this.id = id;
    }

    @Override
    public void run() {
        try {
            sleep((int) (Math.random() * 100));
        } catch (InterruptedException ex) {

        }
        b.entrar(id);
    }
}
