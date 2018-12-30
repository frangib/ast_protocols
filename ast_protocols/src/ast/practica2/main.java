/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ast.practica2;

import java.io.FileNotFoundException;

/**
 *
 * @author lastusr39
 */
public class main {
    public static void main(String[] args) throws FileNotFoundException{
        BusyWaitChannel ch = new BusyWaitChannel();
        Sender sndr = new Sender(ch);
        Receiver rcvr = new Receiver(ch);
        
        sndr.run();
        rcvr.run();   
    }
}