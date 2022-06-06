package Admin;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Set;

public class Admin {
	private String host;
	private int port;
        
	public static HashMap<String, String> dsuser ;
        public static Socket admin;
        public static DataInputStream dis = null;
        public static DataOutputStream dos = null;
        public static String key;
        public static AESEncryption aes;
        public static ReadAdmin read;
        public static WriteAdmin write;
        
	public Admin(String host, int port) {
		this.host = host;
		this.port = port;
	}	
	public void execute() throws IOException {		
		admin = new Socket(host, port);
                System.out.println("connected");
                aes=new AESEncryption();
                
                dsuser= new HashMap<>();
                dis = new DataInputStream(admin.getInputStream());
                dos = new DataOutputStream(admin.getOutputStream());
                write=new WriteAdmin(admin);
                write.start();
                read = new ReadAdmin(admin);
                read.setkey();
		read.start();
                
                Set<String> keySet = dsuser.keySet();
                        for (String key : keySet) {
                            System.out.println(key + " - " + dsuser.get(key));
                        }
                        
        }
}




