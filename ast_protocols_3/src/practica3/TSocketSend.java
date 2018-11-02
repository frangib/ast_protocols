package practica3;

import ast.protocols.tcp.TCPSegment;
import practica1.Channel;

public class TSocketSend extends TSocketBase {

    protected int sndMSS;       // Send maximum segment size

    public TSocketSend(Channel ch) {
        super(ch);
        sndMSS = 10;/*IP maximum message size - TCP header size TODO: Provar 
                    primer amb un valor petit*/
    }

    public void sendData(byte[] data, int offset, int length) {

        // Amb l'ajuda del metode segmentize,
        // dividir la sequencia de bytes de data
        // en segments de mida maxima sndMSS
        // i enviar-los pel canal
        //TODO: COMPROBAR si está bien
        TCPSegment s;

        //Habra dos casos: si length es menor o igual que sndMSS o si es mayor
        if (length <= sndMSS) {
            this.segmentize(data, offset, length);
        } else {
            for (int i = 0; i < Math.ceil((double) length / sndMSS); i++) {  
                s = this.segmentize(data, offset + i * length, length);
                super.channel.send(s);
            }
        }
    }

    protected TCPSegment segmentize(byte[] data, int offset, int length) {

        // Crea un segment que en el seu payload (camp) de dades
        // conte length bytes que hi ha a data a partir
        // de la posicio offset.
        //TODO: COMPROBAR si está bien
        TCPSegment seg = null;
        byte[] aux = null;

        System.arraycopy(data, offset, aux, 0, length);
        seg.setData(aux);
        return seg;
    }

    protected void sendSegment(TCPSegment segment) {
        channel.send(segment);
    }
}

/*
 FIXME: A ver, puede ser que segmentize tenga que devolver más de un segmento.
 No puedo hacer más de un return y sólo puedo devolver un TCPSegmente. Si por
 ejemplt sndMSS = 100 y data tiene 100 bytes, eso resulta en 10 TCPSegments
 de 10 bytes. Sin embargo, segmentize sólo puede devolver 1 TCPSegment. 
 RESPUESTA: EN SENDDATA PONES LA LLAMADA A SEGMENTIZE DENTRO DE UN BUCLE. ¿Vale
 pero que pasa con las posiciones y los offsets?
 TODO: Test con las clases de la practica 2. ¿Cómo?
 */
