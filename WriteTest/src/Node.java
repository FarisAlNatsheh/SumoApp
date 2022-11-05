import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Node {
	private ServerSocket ss;
	private Socket s;
	private DataInputStream dis;
	private DataOutputStream dout;
	private Socket sender;
	private String lastMessage;
	public void sendMessage(String s) {
		try {
			dout.writeUTF(s);
			dout.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}  
}
	public void startServerSocket(final int port) {
		new Thread()
		{
			public void run() {
				try{  
					ss=new ServerSocket(port);  
					s=ss.accept();//establishes connection
					System.out.println("Server started");
					while(true) {
						dis=new DataInputStream(s.getInputStream());  
						String  str=(String)dis.readUTF();  
						lastMessage = str;
						System.out.println(str + " " + port);
					}
					//ss.close();  
				}catch(Exception e){}  
			} 
		}.start();
	}
	public void startSendSocket(final String ip, final int port) {
		new Thread()
		{
			public void run() {

				boolean connected = false;
				while(!connected) {
					try {
						sender=new Socket(ip,port);
						dout=new DataOutputStream(sender.getOutputStream());  
						System.out.println("Client started");
						sendMessage("I am alive");
						while(true); //keep socket open
					}
					catch (IOException e) {
						//System.out.println("Failed");
						if(!connected)
							continue;
					}
				}
			}

		}.start(); 	
	}
	public String getLastMessage() {
		return lastMessage;
	}
}
