
public class Sender extends Thread {

    QueueChannel c;
    int i = 0;

    public Sender(QueueChannel c) {
        this.c = c;
    }

    public void enviar() {
        c.send(i);
        i = i + 1;
    }
    
    public void run(){
        for (int j = 0; j < 1000; j++) {
            enviar();   
        }
    }
}
