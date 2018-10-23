package practica2;

import java.util.logging.Level;
import java.util.logging.Logger;
import practica1.*;

public class TestProtocol {

    public static void main(String[] args) throws InterruptedException {
        AwaitChannel c = new AwaitChannel(2);
        int I = 15;
        Sender s = new Sender(c, I);
        Receiver r = new Receiver(c, I);
        Thread t = new Thread(r);
        s.start();
        t.start();
        s.join();
        t.join();
//        
//        for (int i = 0; i < I; i++) {
//            s.enviar();
//            r.receive();
//        }
        System.out.println("Final simulació...");
    }
}
//FIXME: No funciona