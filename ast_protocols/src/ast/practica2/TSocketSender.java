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
public class TSocketSender {

    private final Channel ch;

    public TSocketSender(Channel ch) {
        this.ch = ch;
    }

    public void sendData(byte[] data, int offset, int length) {
        TCPSegment s = new TCPSegment();
        byte[] vector = data;
        s.setData(vector, offset, length);
        ch.send(s);
    }

    public void close() {
    //envia un segment sense dades per indicar final de transmissío
        TCPSegment s = new TCPSegment();
        ch.send(s);
    }
}
