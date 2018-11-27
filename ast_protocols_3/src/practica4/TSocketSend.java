package practica4;

import ast.protocols.tcp.TCPSegment;

/**
 * @author AST's teachers
 */
public class TSocketSend extends TSocketBase {

    protected int sndMSS;       // Send maximum segment size

    /**
     * Create an endpoint bound to the local IP address and the given TCP port.
     * The local IP address is determined by the networking system.
     *
     * @param ch
     */
    protected TSocketSend(ProtocolSend p, int localPort, int remotePort) {
        super(p, localPort, remotePort);
        // IP maximum message size - TCP header size
        sndMSS = p.channel.getMMS() - TCPSegment.HEADER_SIZE; 
    }

    public void sendData(byte[] data, int offset, int length) {
        //...
        //TODO: PREGUNTAR ALFONSO. El me dijo que el ultimo argumento 
        //del segmentize debajo de este comentario tenia que ser
        //length - i * sndMSS pero no tiene sentido. Precisamente hago
        //el for porque length > sndMSS pero en la primera iteración
        //ya le estoy metiendo length bytes al segmento. El ultimo
        //argumento debería ser sndMSS (yo creo, vamos). Así vas
        //metiendo de sndMSS en sndMSS.
        TCPSegment s;
        int i;
        //Habra dos casos: si length es menor o igual que sndMSS o si es mayor
        System.out.println("Llega con length/sndMSS ==> " + 
                length + "/" + sndMSS);
        if (length <= sndMSS) {
            this.segmentize(data, offset, length);
        } else {
            for (i = 0; i < Math.floor((double) length / sndMSS); i++) {
                s = this.segmentize(data, offset + i * sndMSS, length - i * 
                        sndMSS);
                //TODO: Este print lo pongo yo para comprobar.
                System.out.println("Llega3 con length/por enviar ==> "
                        + length + "/" + (length - ((i + 1) * sndMSS)));
            }
            s = this.segmentize(data, offset + i * sndMSS, length - i * sndMSS);
        }
    }

    protected TCPSegment segmentize(byte[] data, int offset, int length) {
        TCPSegment seg = new TCPSegment();
        //...
        byte[] aux = new byte[length];

        System.arraycopy(data, offset, aux, 0, length);
        seg.setData(aux, 0, length);
        return seg;
    }

    protected void sendSegment(TCPSegment segment) {
        proto.channel.send(segment);
    }
}
