//DistComp - Project
//Alex Sieland
//Matthew Dale
package Server;

import java.net.*;
import java.io.*;
import java.util.concurrent.Semaphore;

public class Server implements Runnable{
	
	int port;
	boolean running;
	PrintWriter printOut;
    private final Semaphore threadResource;
    private ServerCommand com;
	
	public Server(int port, ServerCommand com) {
		super();
		this.port = port;
		this.com = com;
		this.running = false;
        threadResource = new Semaphore(100, true);
	}

	public void run(){
		
		ServerSocket serverSock = null;
		running = true;
		
		try{
			serverSock = new ServerSocket(port);
			
			while(running){
                threadResource.acquire();
                new Thread( new ServerThread( serverSock.accept(), threadResource ) ).start();
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
        Semaphore threadResource;
		
		public ServerThread(Socket sock, Semaphore threadResource) {
			super();
			this.sock = sock;
            this.threadResource = threadResource;
		}

		public void run(){
			try{			
				BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
				
				String message = in.readLine();
				
				PrintWriter out = new PrintWriter(sock.getOutputStream());
				com.command(message, out);
				out.flush();
				
				sock.close();
			}catch(Exception e){
				System.err.println("Error: " + e);
			}
            threadResource.release();
		}
		
	}
	
}
