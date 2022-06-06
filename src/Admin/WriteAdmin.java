/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Admin;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author Dell
 */
public class WriteAdmin extends Thread{
	private Socket admin;
        
	public WriteAdmin(Socket admin) {
		this.admin = admin;
	}
	public void thongtin(String username) throws IOException
        {
            Admin.dos.writeUTF("infoadmin"+username);
        }
	@Override
	public void run() {
		try {
			Admin.dos = new DataOutputStream(admin.getOutputStream());			
                        thongtin("admin");			
		} catch (Exception e) {
			try {
				Admin.dos.close();
				admin.close();
			} catch (IOException ex) {
				System.out.println("Ngắt kết nối Server");
			}
		}
                
	}
}