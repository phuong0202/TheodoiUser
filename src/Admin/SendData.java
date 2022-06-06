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
public class SendData extends Thread{
	private Socket admin;
        private String user;
        private String sms="";
        
	public SendData(Socket admin,String user, String sms) {
		this.admin = admin;   
                this.user = user;
                this.sms=sms;
	}
	
	@Override
	public void run() {
		try {					                                      
                                    String tam="data/admin/"+user+"/"+sms;
                                    //System.out.println("Admin.SendData.run()"+tam);
                                    Admin.dos.writeUTF(Admin.aes.encrypt(tam, Admin.key));
                                    //System.out.println(Admin.aes.encrypt(tam, Admin.key));
//                                    sms=null;
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