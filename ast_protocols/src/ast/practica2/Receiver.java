/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ast.practica2;

import ast.practica1.*;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lastusr39
 */
public class Receiver implements Runnable {

    private final TSocketReceiver tsr;
    private final int N = 1000;
    private FileOutputStream fr;

    public Receiver(Channel ch) throws FileNotFoundException {
        tsr = new TSocketReceiver(ch);
        fr = new FileOutputStream("poema2.txt");
    }
//Rep un segment i guarda els bytes rebuts al fitxer //retorna el ńumero de bytes rebuts
//-1 si no ha rebut cap byte (final)
    
    public void run(){
        //TODO:
        byte[] missatgeRebut = new byte[1024];
        //si l'emissor tanca el canal, s'acaba la transmissio
        while (true) {
            //missatgeRebut = canal.receive().getData();    
            int rebuts = tsr.receiveData(missatgeRebut,0, missatgeRebut.length);
            if (rebuts == -1) {
               tsr.close();
            }
            String aplicacio = new String(missatgeRebut, 0, rebuts);
            System.out.println("S'ha rebut: " + aplicacio + "\n");
        }
    }
    
//    public int receive() throws IOException {
//        int i=0;
//        byte[] data = new byte[N];
//        i = tsr.receiveData(data, 0, N);
//        if(data.length == 0){
//            return -1;
//        }else{
//            fr.write(data, 0, i);
//            return i;
//        }
//        
//    }
//Tanca l’stream al fitxer i la connexi ́o public void close() {
}