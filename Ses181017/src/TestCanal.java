
import java.util.logging.Level;
import java.util.logging.Logger;

public class TestCanal {

    public static void main(String[] args) {
        QueueChannel c = new QueueChannel(2);
        Sender s = new Sender(c);
        Sender s2 = new Sender(c);
        Receiver r = new Receiver(c);
        s.start();
        s2.start();
        r.start();
        try {
            s.join();
            s2.join();
            r.join();
        } catch (InterruptedException ex) {
        }
        System.out.println("\nFinal simulació");
    }
}
