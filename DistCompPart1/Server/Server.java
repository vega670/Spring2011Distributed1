package Server;
import java.net.*;
import java.io.*;

public class Server{
	
	int port;
	boolean running;
	PrintWriter printOut;
	
	public Server(int port) {
		super();
		this.port = port;
		running = false;
	}
	
	public void run(){
		
		ServerSocket serverSock = null;
		running = true;
		
		try{
			serverSock = new ServerSocket(port);
			
			while(running){
				new Thread( new ServerThread( serverSock.accept() ) ).start();
			}
			
			serverSock.close();
		}catch(Exception e){
			System.err.println("Error: " + e);
			running = false;
		}
		
	}
	
	//Section for Thread component
	
	private class ServerThread implements Runnable{
		
		Socket sock;
		
		public ServerThread(Socket sock) {
			super();
			this.sock = sock;
		}

		public void run(){
			try{			
				BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
				
				String message = in.readLine();
				
				PrintWriter out = new PrintWriter(sock.getOutputStream());
				out.println(message.toUpperCase());
				out.flush();
				
				sock.close();
			}catch(Exception e){
				System.err.println("Error: " + e);
			}
		}
		
	}
	
}
