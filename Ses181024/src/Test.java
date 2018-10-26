
public class Test {

    public static void main(String[] args) throws InterruptedException {
        ComptadorAcotat c = new ComptadorAcotat(5, 10);
        Fil[] f = new Fil[40];//<---

        for (int i = 0; i < 40; i++) {
            f[i] = new Fil(c, i);
            f[i].start();
        }
        
        for (int i = 0; i < 40; i++) {
            f[i].join();
        }

        System.out.println("Valor final:" + c.valor);
    }

}
