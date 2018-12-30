/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ast.practica1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lastusr39
 */
public class main {

    public static void main(String[] args) throws FileNotFoundException, IOException {
        Channel ch = new QueueChannel(2);
        Sender s = new Sender(ch);
        Receiver r = new Receiver(ch);
        int ls = s.enviar();
        while (ls > 0) {
            int lr = r.receive();
            if (lr < 0) {
                System.out.println("Error en recepcio!!");
            }
            ls = s.enviar();
        }
        s.close();
        r.close();
    }
}