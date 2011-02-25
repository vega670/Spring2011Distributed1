package Router;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class RouterPort implements Runnable{
	
	private int port;
	private RouteTable table;
	private boolean running;
	
	private static final int timeout = 10000;
	
	public RouterPort(int port, RouteTable table) {
		super();
		this.port = port;
		this.table = table;
		running = false;
	}
	
	public void run(){
		
		ServerSocket serverSock = null;
		running = true;
		
		try{
			serverSock = new ServerSocket(port);
			
			while(running){
				new Thread( new RouterThread( serverSock.accept() ) ).start();
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
		
		public RouterThread(Socket sock) {
			super();
			this.sockClient = sock;
		}

		public void run(){
			try{	
				//read from client
				BufferedReader inClient = new BufferedReader(new InputStreamReader(sockClient.getInputStream()));
				String message = inClient.readLine();
				
				//search route table
				ServerLink sLink = table.getServerLink(port);
				Socket sockServer = new Socket(sLink.getIp(), sLink.getPort());
				
				//write to server
				PrintWriter outServer = new PrintWriter(sockServer.getOutputStream());
				outServer.println(message);
				outServer.flush();
				
				//read from server
				sockServer.setSoTimeout(timeout);
				BufferedReader inServer = new BufferedReader(new InputStreamReader(sockServer.getInputStream()));
				message = inServer.readLine();
				
				//write to client
				PrintWriter outClient = new PrintWriter(sockClient.getOutputStream());
				outClient.println(message);
				outClient.flush();
				
				//close sockets
				sockServer.close();
				sockClient.close();
			}catch(Exception e){
				System.out.println("Error: " + e);
			}
		}
		
	}

}
