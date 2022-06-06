/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Set;

/**
 *
 * @author Thanh Phuong
 */
public class WriteServer extends Thread{
        private Socket socket;
        DataOutputStream dos = null;
	public WriteServer(Socket socket) {
		this.socket = socket;
	}
        
	public void DSClient() throws IOException
        {           
            int count=Server.ListClient.size()-1;
            Set<String> keySet = Server.ListClient.keySet();
            String kq="dsuser/"+count;
            if(Server.ListClient.containsKey("admin")==true)
            {
                
                for (Socket item : Server.ListSocket) {

                if(String.valueOf(item.getInetAddress()).substring(1).equals( Server.ListClient.get("admin"))  )
                            {
                                
                               dos = new DataOutputStream(item.getOutputStream());
                        for (String key : keySet) {
                                if(!key.equals("admin"))
                                {
                                    
                                     kq=kq+"/"+key + "/" + Server.ListClient.get(key);
                                  
                                } 
                                
                            }    
                                //System.out.println("Server.WriteServer.DSClient()"+kq);
                                dos.writeUTF(kq);
                            //dos.writeUTF(Server.aes.encrypt(kq, Server.Key.get(Server.ListClient.get("admin"))));
                        }
                }
                
            }
            
        }
        @Override
	public void run() {
		try {
                        DSClient();
			
		} catch (Exception e) {
			try {
				dos.close();
				socket.close();
			} catch (IOException ex) {
				System.out.println("Ngắt kết nối Server");
			}
		}
	}    
}
