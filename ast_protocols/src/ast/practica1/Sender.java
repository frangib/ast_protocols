/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ast.practica1;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lastusr39
 */
public class Sender {

    private final TSocketSender tss;
    private final int N = 10;
    private FileInputStream fr;
//el fitxer poema.txt ha d’estar en la carpeta del projecte.

    public Sender(Channel ch) {
        tss = new TSocketSender(ch);
        try {
            fr = new FileInputStream("poema.txt");
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }
//llegeix N bytes del fitxer i els envia.
//Retorna el n´umero real de bytes enviats
//-1 en cas de final de fitxer

    public int enviar() throws IOException {
      int i=0;
      byte[] e=new byte[N];
      i=fr.read(e);
      if(i==-1) {
          return -1;
      }else{
          tss.sendData(e, 0, i);
          return i;
      }
    }
    
//Tanca l’stream al fitxer i la connexió
    public void close() {
        try {
            fr.close();
            tss.close();
        } catch (IOException ex) {
            Logger.getLogger(Sender.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
