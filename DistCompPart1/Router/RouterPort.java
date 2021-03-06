//DistComp - Project
//Alex Sieland
//Matthew Dale
package Router;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Semaphore;

public class RouterPort implements Runnable{
	
	private int port;
	private ServerTable table;
	private boolean running;
	private final Semaphore threadResource;
	private static final int timeout = 10000;
	
	public RouterPort(int port, ServerTable table) {
		super();
		this.port = port;
		this.table = table;
		
		this.running = false;
        this.threadResource = new Semaphore(100, true);
	}
	
	public void run(){
		
		ServerSocket serverSock = null;
		running = true;
		
		try{
			serverSock = new ServerSocket(port);
			
			while(running){
                threadResource.acquire();
                new Thread( new RouterThread( serverSock.accept(), threadResource ) ).start();
			}
			
			serverSock.close();
		}catch(Exception e){
			System.err.println("Error: " + e);
			running = false;
		}
		
	}
	
	//Section for Thread component
	
	private class RouterThread implements Runnable{
		
		Socket sockClient;
        Semaphore threadResource;
		
		public RouterThread(Socket sock, Semaphore threadResource) {
			super();
			this.sockClient = sock;
            this.threadResource = threadResource;
		}

		public void run(){
			
			try{
				//read from client
				BufferedReader inClient = new BufferedReader(new InputStreamReader(sockClient.getInputStream()));
				String message = inClient.readLine();
				
				if( !table.checkForAddress( sockClient.getInetAddress() ) ){
					table.addServerLink( new ServerLink(17655 , sockClient.getInetAddress()) );
				}
					
				//search route table
				ServerLink link = table.getServerLink();
				Socket sockServer;
				while(true){
					try{
						sockServer = new Socket(link.getIp(), link.getPort());
						break;
					}catch(Exception e){
						table.removeServerLink(link.getIp());
					}
				}
				
				//write to server
				PrintWriter outServer = new PrintWriter(sockServer.getOutputStream());
				outServer.println(message);
				outServer.flush();
				
				//read from server
				sockServer.setSoTimeout(timeout);
				BufferedReader inServer = new BufferedReader(new InputStreamReader(sockServer.getInputStream()));
				message = inServer.readLine();
				String portMessage = inServer.readLine();
				
				//close server socket
				sockServer.close();
				
				//write to client
				PrintWriter outClient = new PrintWriter(sockClient.getOutputStream());
				outClient.println(message);
				outClient.println(portMessage);
				outClient.flush();
				
				//close client socket
				sockClient.close();
			}catch(Exception e){
				System.out.println("Error: " + e);
			}
                        threadResource.release();
		}
		
	}

}
