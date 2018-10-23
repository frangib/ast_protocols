package ast.practica1;

public class Receiver {

    private final TSocketRecv tsr;
    private int valor;
    private final int I;

    public Receiver(Channel c, int I) {
        tsr = new TSocketRecv(c);
        this.I = I;
    }

    public int receive() {
        int N = 4;
        int random = (int) (10 * Math.random());
        byte[] b = new byte[N + random];

        int n = tsr.receiveData(b, random, N);
        System.out.print(new String(b, random, n) + ",");
        if (valor > I - 5) {
            n = tsr.receiveData(b, random, N);
            System.out.print(new String(b, random, n) + ",");
        }
        valor = valor + 1;
        return n;
    }

}
