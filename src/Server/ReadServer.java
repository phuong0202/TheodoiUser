/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import static Server.Server.socket;
import static Server.Server.write;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Set;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author Thanh Phuong
 */
public class ReadServer extends Thread {

    private Socket socket;

    public ReadServer(Socket socket) {
        this.socket = socket;
    }

    public void thongtin() throws IOException {
        DataInputStream dis = new DataInputStream(socket.getInputStream());
        String tn = dis.readUTF();
        String ip = String.valueOf(socket.getInetAddress());
        if (tn.contains("infouser")) {

            Server.ListClient.put(ip.substring(1), tn.substring(8));

        }
        if (tn.contains("infoadmin")) {

            Server.ListClient.put(tn.substring(9), ip.substring(1));

        }
        String key = Server.aes.getkey();

        Server.Key.put(ip.substring(1), key);

        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        dos.writeUTF("key/server/" + key);

    }

    @Override
    public void run() {
        try {
            String c = null;
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            while (true) {
                String sms = dis.readUTF();
                //System.out.println(sms);
                if (sms.equals("dsuser/admin/updateDSuser/")) {
                    write = new WriteServer(socket);
                    write.DSClient();
                } else {
                    //System.out.println(sms);
                    InetAddress name = socket.getInetAddress();

                    sms = Server.aes.decrypt(sms, Server.Key.get(String.valueOf(name).substring(1)));

                     //System.out.println(sms);
                    if (sms.contains("exit")) {
                        String tam = sms.substring(4);
                        Server.ListSocket.remove(socket);
                        System.out.println("Đã ngắt kết nối với " + socket + Server.ListClient.get(tam));
                        Server.ListClient.remove(tam);
//                        Set<String> keySet = Server.ListClient.keySet();
//                        for (String key : keySet) {
//                            System.out.println(key + " -abcẻt  " + Server.ListClient.get(key));
//                        }
                        dis.close();
                        socket.close();
                        continue; //Ngắt kết nối rồi
                    }
                    if (sms.contains("data/")) {

                        String[] nhan = sms.split("/");
                        c = nhan[2];
                        if (c.equals("admin")) {
                            c = Server.ListClient.get(c);
                        }

                        if ( (sms.contains("/logout") || sms.contains("/shutdown"))) {
                            //System.out.println("Server.ReadServer.run()"+Server.Key.get(String.valueOf(c)));
                            for (Socket item : Server.ListSocket) {
                                String tam1 = Server.aes.encrypt(sms, Server.Key.get(String.valueOf(c)));

                                if (String.valueOf(item.getInetAddress()).substring(1).equals(c)) {
                                    DataOutputStream dos = new DataOutputStream(item.getOutputStream());
                                    dos.writeUTF(tam1);
                                    //System.out.println("Server.ReadServer.run()"+tam1);
                                }
                            }
//                            for (Socket item : Server.ListSocket) {
//
//                                if (String.valueOf(item.getInetAddress()).substring(1).equals(c)) {
//                                    Server.ListSocket.remove(item);
//                                    Server.ListClient.remove(c);
//                                System.out.println("Đã ngắt kết nối với " + item + c);
//                                Set<String> keySet = Server.ListClient.keySet();
//                        for (String key : keySet) {
//                            System.out.println(key + " xóa  " + Server.ListClient.get(key));
//                        }
////                                    dis.close();
////                                item.close();
//                                continue;
//                                }
//                                
//                                
//                                
//                            }
                        } else {

                            String tam1 = Server.aes.encrypt(sms, Server.Key.get(c));

                            for (Socket item : Server.ListSocket) {

                                if (String.valueOf(item.getInetAddress()).substring(1).equals(c)) {
                                    DataOutputStream dos = new DataOutputStream(item.getOutputStream());
                                    dos.writeUTF(tam1);

                                }
                            }
                        }

                    }
//                if (sms.contains("data/") && sms.contains("/capscreen")) {
//                    for (Socket item : Server.ListSocket) {
//                        if (String.valueOf(item.getInetAddress()).substring(1).equals("amdin")) {
//                            DataOutputStream dos = new DataOutputStream(item.getOutputStream());
//                            dos.writeUTF(sms);
//                        }
//                    }
//                    BufferedImage img = ImageIO.read(ImageIO.createImageInputStream(socket.getInputStream()));
//
//                    ImageIO.write(img, "png", new File("C:\\Users\\Thanh Phuong\\Desktop\\132.png"));
//                    System.out.println("Server.ReadServer.run()");
//                }
                }

            }
        } catch (Exception e) {
            try {
                socket.close();
            } catch (IOException ex) {
                System.out.println("Ngắt kết nối Server");
            }
        }
    }
}
