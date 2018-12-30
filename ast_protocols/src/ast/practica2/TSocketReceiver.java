/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ast.practica2;

import ast.practica1.*;
import ast.protocols.tcp.TCPSegment;

/**
 *
 * @author franSSD
 */
public class TSocketReceiver {

    private final Channel ch;
    private TCPSegment tcp;

    public TSocketReceiver(Channel ch) {
        this.ch = ch;
    }

    public int receiveData(byte[] data, int offset, int length) {
        //Passa el TCPSegment a un array de byte
        //FIXME:PREGUNTAR EN LAB
        if (tcp.getDataLength() == 0) {
            return -1;
        } else {
            tcp = ch.receive();
            byte[] tcpdata = tcp.toBytes();
            System.arraycopy(tcpdata, tcp.getDataOffset(), data, offset, length);
            return tcpdata.length;
        }
    }

    public void close() {
        //envia un segment sense dades per indicar //final de transmissi ́o
        TCPSegment empty_tcp = new TCPSegment();
        ch.send(empty_tcp);
    }
}