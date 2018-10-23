package ast.practica1;

public class TestProtocol {

    public static void main(String[] args) {
        QueueChannel c = new QueueChannel(2);
        int I = 15;
        Sender s = new Sender(c, I);
        Receiver r = new Receiver(c, I);

        for (int i = 0; i < I; i++) {
            s.enviar();
            r.receive();
        }
        System.out.println("");
    }
}
