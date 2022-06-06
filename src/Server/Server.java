/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 *
 * @author Thanh Phuong
 */
public class Server {
        private int port;
	public static ArrayList<Socket> ListSocket;
        public static HashMap<String, String> ListClient ;
        public static HashMap<String, String> Key ;
        public static AESEncryption aes;
        public static WriteServer write;
        public static ReadServer read;
        public static Socket socket;
	public Server(int port) {
		this.port = port;
	}
        private void Start() throws IOException {
		ServerSocket server = new ServerSocket(port);		
		System.out.println("Server is listening...");
		while (true) {
			socket = server.accept();
                        //ExecutorService executor = Executors.newFixedThreadPool(numThread);
                        Server.ListSocket.add(socket);
                        aes=new AESEncryption();
                        read = new ReadServer(socket);
                        write =new WriteServer(socket);
                        read.thongtin();
			System.out.println("Đã kết nối với " + socket);
                        Set<String> keySet = Server.ListClient.keySet();
                        for (String key : keySet) {
                            System.out.println(key + " - " + Server.ListClient.get(key));
                        }	
                        write.DSClient();
			read.start();                        
		}
	}
        public static void main(String[] args) throws IOException {
		Server.ListSocket = new ArrayList<>();
                Server.ListClient= new HashMap<>();
                Server.Key= new HashMap<>();
		Server server = new Server(1234);
		server.Start();
	}
}
