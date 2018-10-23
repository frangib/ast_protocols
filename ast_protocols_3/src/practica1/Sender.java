package practica1;

public class Sender {

    private final TSocketSend tss;
    private Integer valor = 0;
    private int random;
    private byte[] b;
    private final int I;

    public Sender(Channel c, int I) {
        tss = new TSocketSend(c);
        this.I = I;
    }

    public void enviar() {

        random = (int) (I * Math.random());
        b = new byte[valor.toString().getBytes().length + random];
        if (valor <= I - 5) {
            System.arraycopy(valor.toString().getBytes(), 0, b, random, valor.toString().getBytes().length);
            tss.sendData(b, random, valor.toString().getBytes().length);
        } else {
            System.arraycopy(valor.toString().getBytes(), 0, b, random, valor.toString().getBytes().length);
            tss.sendData(b, random, valor.toString().getBytes().length);
            valor = valor + 1;
            System.arraycopy(valor.toString().getBytes(), 0, b, random, valor.toString().getBytes().length);
            tss.sendData(b, random, valor.toString().getBytes().length);
        }
        valor = valor + 1;

    }
}
