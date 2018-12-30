/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ast.practica3;

import ast.protocols.tcp.TCPSegment;

/**
 *
 * @author lastusr39
 */
public class TSocketSend extends TSocketBase {
    
    protected int sndMSS;
    
    public TSocketSend(Channel ch) {
        super(ch);
        sndMSS=1024-128;
    }

    public void sendData(byte[] data, int offset, int length) {
        int size=0;
        int rev=offset;
        while(rev<length) {
            if(length>=sndMSS) {
                size=sndMSS;
            }else{
                size=length-rev;
            }
            sendSegment(segmentize(data,rev,size));
            rev=rev+size;
        }
        
        
    }
    
    protected TCPSegment segmentize(byte[] data, int offset, int length) {
        TCPSegment tcp = new TCPSegment();
        tcp.setData(data, offset, length);
        return tcp;
    }
    
    protected void sendSegment(TCPSegment segment) {
        channel.send(segment);
            
    }
}
