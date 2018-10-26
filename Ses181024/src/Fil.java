
public class Fil extends Thread {

    private final ComptadorAcotat c;
    private final int id;

    public Fil(ComptadorAcotat c, int id) {
        this.c = c;
        this.id = id;
    }

    @Override
    public void run() {
        if (id % 2 == 0) {
            c.inc();
        } else {
            c.dec();
        }
    }
}
