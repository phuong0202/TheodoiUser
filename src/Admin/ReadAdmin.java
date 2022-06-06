/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Admin;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author Dell
 */
public class ReadAdmin extends Thread {

    private Socket admin;
    public static String test = "";

    public ReadAdmin(Socket admin) {
        this.admin = admin;
    }

    public void dsuser(String nhan) throws IOException {

        String[] tam = nhan.split("/");
        int count=Integer.valueOf(tam[1]);
        
        int i=2;
        while ( i <=count*2) {
                       
            Admin.dsuser.put(tam[i], tam[i+1]);
            i=i+2;
        }
        
        
    }

    public void setkey() {
        try {
            DataInputStream dis = new DataInputStream(admin.getInputStream());
            String tn = dis.readUTF();
            if (tn.contains("key/server/")) {
                Admin.key = tn.substring(11);
                
            }
        } catch (Exception e) {
        }
    }

    @Override
    public void run() {
        ReceiveData receive = new ReceiveData();
        try {

            while (true) {

                String sms1 = Admin.dis.readUTF();
                //System.out.println( sms1);
                
                if (sms1.contains("dsuser/")) {
                    dsuser(sms1);
                    
                } else {
                    String sms = Admin.aes.decrypt(sms1, Admin.key);
                    
                    ReadAdmin.test=sms;
                    receive.nhan(sms);

                }
            }
        } catch (Exception e) {
        }
    }

}
