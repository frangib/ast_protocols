/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ast.practica1;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lastusr39
 */
public class Receiver {

    private final TSocketReceiver tsr;
    private final int N = 1000;
    private FileOutputStream fr;

    public Receiver(Channel ch) throws FileNotFoundException {
        tsr = new TSocketReceiver(ch);
        fr = new FileOutputStream("poema.txt");
    }
//Rep un segment i guarda els bytes rebuts al fitxer //retorna el ńumero de bytes rebuts
//-1 si no ha rebut cap byte (final)

    public int receive() throws IOException {
        int i=0;
        byte[] data = new byte[N];
        i = tsr.receiveData(data, 0, N);
        if(data.length == 0){
            return -1;
        }else{
            fr.write(data, 0, i);
            return i;
        }
        
    }
//Tanca l’stream al fitxer i la connexi ́o public void close() {

    public void close() {
        try {
            fr.close();
        } catch (IOException ex) {
            Logger.getLogger(Receiver.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}