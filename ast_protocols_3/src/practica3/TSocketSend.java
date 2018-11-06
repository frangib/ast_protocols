package practica3;

import ast.protocols.tcp.TCPSegment;
import practica1.Channel;

public class TSocketSend extends TSocketBase {

    protected int sndMSS;       // Send maximum segment size

    public TSocketSend(Channel ch) {
        super(ch);
        sndMSS = 10;/*IP maximum message size - TCP header size TODO: Probar 
         primer amb un valor petit*/

    }

    public void sendData(byte[] data, int offset, int length) {

        // Amb l'ajuda del metode segmentize,
        // dividir la sequencia de bytes de data
        // en segments de mida maxima sndMSS
        // i enviar-los pel canal
        //CORRECTO
        TCPSegment s;
        int i;
        //Habra dos casos: si length es menor o igual que sndMSS o si es mayor
        if (length <= sndMSS) {
            this.segmentize(data, offset, length);
        } else {
            for (i = 0; i < Math.floor((double) length / sndMSS); i++) {

                s = this.segmentize(data, offset + i * sndMSS, length - i * sndMSS);
                super.channel.send(s);
                System.out.println("Llega3 con length/por enviar ==> " + length + "/" + (length - ((i + 1) * sndMSS)));
            }
            s = this.segmentize(data, offset + i * sndMSS, length - i * sndMSS);
        }
    }

    protected TCPSegment segmentize(byte[] data, int offset, int length) {

        // Crea un segment que en el seu payload (camp) de dades
        // conte length bytes que hi ha a data a partir
        // de la posicio offset.
        //TODO: CORRECTO
        TCPSegment seg = new TCPSegment();
        byte[] aux = new byte[length];

        System.arraycopy(data, offset, aux, 0, length);
        seg.setData(aux, 0, length);
        return seg;
    }

    protected void sendSegment(TCPSegment segment) {
        channel.send(segment);
    }
}
