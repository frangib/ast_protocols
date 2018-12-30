/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ast.practica2;

import ast.protocols.tcp.TCPSegment;
import ast.util.CircularQueue;
import static java.lang.Thread.sleep;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lastusr39
 */

public class BusyWaitChannel implements Channel {
    CircularQueue cua= new CircularQueue(10);
    
    @Override
    public void send(TCPSegment seg){
        while(cua.full()){
            try {
                sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(BusyWaitChannel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        cua.put(seg);
    }
    
    @Override
    public TCPSegment receive(){
        while(cua.empty()){
            try {
                sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(BusyWaitChannel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return (TCPSegment) cua.get ();   
    } 
}