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
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lastusr39
 */
public class Sender implements Runnable {

    private final TSocketSender tss;

    public Sender(Channel ch) {
        tss = new TSocketSender(ch);
    }
    
    @Override
    public void run() {
        String bfString = "";
        boolean flag = true;
        System.out.println("Per tancar transmissio escriure EOF o eof:");
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));

        while (flag) {
            try {
                bfString = bf.readLine();
                if (bfString.contains("EOF") || bfString.contains("eof")) {
                    tss.close();
                    flag = false;
                } else {
                    tss.sendData(bfString.getBytes(), 0, bfString.getBytes().length);
                }
            } catch (IOException ex) {
                Logger.getLogger(Sender.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
