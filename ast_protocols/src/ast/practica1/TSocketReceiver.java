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
public class TSocketReceiver {

    private final Channel ch;
    private TCPSegment tcp;
    
    public static void main(String[] args) {
        
    }

    public TSocketReceiver(Channel ch) {
        this.ch = ch;
    }

    public int receiveData(byte[] data, int offset, int length) {
        //Passa el TCPSegment a un array de byte
       TCPSegment seg=ch.receive();
       if(length<seg.getDataLength()) {
           //error
       }
       System.arraycopy(seg.getData(), seg.getDataOffset(), data, offset, seg.getDataLength());
       return seg.getDataLength()-seg.getDataOffset();
    }

    public void close() {
    //envia un segment sense dades per indicar //final de transmissi ́o
        
    }
}