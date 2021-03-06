
package ast.practica4;

// declareu imports


import ast.practica3.MonitorChannel;
import ast.practica3.Channel;


public class Main {

    public static void main(String[] args){
            Channel c = new MonitorChannel(50,0);

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
        new Thread(new Receiver(proto.openForInput(PORT, Host2.PORT1))).start();
        new Thread(new Receiver(proto.openForInput(PORT, Host2.PORT2))).start();
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
        new Thread(new Sender(proto.openForOutput(PORT1, Host1.PORT))).start();
        new Thread(new Sender(proto.openForOutput(PORT1, Host1.PORT))).start();
    }
    
}
