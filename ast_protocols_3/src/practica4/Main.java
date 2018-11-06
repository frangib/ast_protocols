package practica4;

// declareu imports
import practica1.Channel;
import practica3.MonitorChannel;

public class Main {

    public static void main(String[] args) {
        Channel c = new MonitorChannel();

        ProtocolRecv proto1 = new ProtocolRecv(c);
        new Thread(new Host1(proto1)).start();

        ProtocolSend proto2 = new ProtocolSend(c);
        new Thread(new Host2(proto2)).start();
    }
}

class Host1 implements Runnable {

    public static final int PORT = 10;

    protected ProtocolRecv proto;

    public Host1(ProtocolRecv proto) {
        this.proto = proto;
    }

    public void run() {
      //arranca dos fils receptors, cadascun amb el seu socket de recepcio
        //fes servir els ports apropiats
        //...
        TSocketRecv tsr1 = new TSocketRecv(proto, PORT, Host2.PORT1);
        TSocketRecv tsr2 = new TSocketRecv(proto, PORT, Host2.PORT2);
        Thread r1 = new Thread (new Receiver(tsr1));
        Thread r2 = new Thread (new Receiver(tsr2));
        r1.start();
        r2.start();
        /*Hay que crear así los threads porque Receiver implements Runnable 
        (y no extends Thread). Lo mismo para el run de Host2*/
    }
}

class Host2 implements Runnable {

    public static final int PORT1 = 10;
    public static final int PORT2 = 50;

    protected ProtocolSend proto;

    public Host2(ProtocolSend proto) {
        this.proto = proto;
    }

    public void run() {
      //arranca dos fils emissors, cadascun amb el seu socket de transmissio
        //fes servir els ports apropiats
        //...
        TSocketSend tss1 = new TSocketSend(proto, PORT1, Host1.PORT);
        TSocketSend tss2 = new TSocketSend(proto, PORT2, Host1.PORT);
        Thread s1 = new Thread(new Sender(tss1));
        Thread s2 = new Thread(new Sender(tss2));
        s1.start();
        s2.start();
    }
}
