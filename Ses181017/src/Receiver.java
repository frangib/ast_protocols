public class Receiver extends Thread{
    QueueChannel c;

    public Receiver(QueueChannel c) {
        this.c = c;
    }

    public Integer rebre() {
        return c.receive();
    }
    
    public void run(){
        for (int j = 0; j < 2000; j++) {
            System.out.print(rebre() + ",");   
        }
    }
}
