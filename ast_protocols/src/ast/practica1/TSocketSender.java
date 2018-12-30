/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ast.practica1;

import ast.protocols.tcp.TCPSegment;

/**
 *
 * @author franSSD
 */
public class TSocketSender {

    private final Channel ch;
    private TCPSegment tcp = new TCPSegment();

    public TSocketSender(Channel ch) {
        this.ch = ch;
    }

    public void sendData(byte[] data, int offset, int length) {
        tcp.setData(data, offset, length);
        ch.send(tcp);
    }

    public void close() {
    //envia un segment sense dades per indicar final de transmissío
        TCPSegment s = new TCPSegment();
        ch.send(s);
    }
}
