/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package User;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

/** 
 *
 * @author Dell
 */
class WriteClient extends Thread {
	private Socket client;
	public WriteClient(Socket client) {
		this.client = client;	
	}
	public void thongtin() throws IOException
        {
            DataOutputStream dos = null;
            dos = new DataOutputStream(client.getOutputStream());
            InetAddress name =InetAddress.getLocalHost();
            dos.writeUTF("infouser"+name.getHostName());
        }
        public void exit() throws IOException
        {
            DataOutputStream dos = null;
            dos = new DataOutputStream(client.getOutputStream());
            InetAddress name =InetAddress.getLocalHost();
            dos.writeUTF(Client.aes.encrypt("exit"+name.getHostAddress(), Client.key));
            Client.client.close();
        }
	@Override
	public void run() {
		DataOutputStream dos = null;
		try {
			dos = new DataOutputStream(client.getOutputStream());
                        thongtin();
			
		} catch (Exception e) {
			try {
				dos.close();
				client.close();
			} catch (IOException ex) {
				System.out.println("Ngắt kết nối Server");
			}
		}
	}       
}
