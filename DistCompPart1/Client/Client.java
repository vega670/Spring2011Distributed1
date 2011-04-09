//DistComp - Project
//Alex Sieland
//Matthew Dale
package Client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;

public class Client {
	
	private InetAddress host;
	private int port;
	
	private static final int timeout = 10000;
	
	//Analysis Data
	private int messagesSent;
	private int messagesFailed;
	private long totalLength;
	private long totalTime;
	private long startTime;
	
	
	
	public Client(InetAddress host, int port) {
		super();
		this.host = host;
		this.port = port;
		this.messagesSent = 0;
		this.messagesFailed = 0;
		this.totalLength = 0;
		this.totalTime = 0;
		this.startTime = 0;
	}

	public String send(String message){
		//Analysis Data
		messagesSent++;
		totalLength += message.length();
		
		//Actual Send Code
		try{
			
			startTime = System.currentTimeMillis();
			
			Socket sock = new Socket(host, port);
			
			PrintWriter out = new PrintWriter(sock.getOutputStream());
			out.println("ping");
			out.flush();
			
			sock.setSoTimeout(timeout);
			BufferedReader in = new BufferedReader( new InputStreamReader( sock.getInputStream() ) ) ;
			String ipMessage = in.readLine();
			String portMessage = in.readLine();
			
			sock.close();
			
			Socket sockMessage = new Socket(ipMessage, Integer.parseInt(portMessage));
			
			PrintWriter outMessage = new PrintWriter(sockMessage.getOutputStream());
			outMessage.println(message);
			outMessage.flush();
			
			sockMessage.setSoTimeout(timeout);
			BufferedReader inMessage = new BufferedReader( new InputStreamReader( sockMessage.getInputStream() ) ) ;
			message = inMessage.readLine();
			
			sockMessage.close();
			
			totalTime += System.currentTimeMillis() - startTime;
			
			return message;
		}catch(Exception e){
			messagesFailed++;
			System.err.println("Error: " + e);
			return "Error: " + e.getMessage();
		}
		
	}

	
	
	public int getMessagesSent() {
		return messagesSent - messagesFailed;
	}
	
	public int getMessagesFailed(){
		return messagesFailed;
	}

	public long getAverageLength() {
		return totalLength/messagesSent;
	}

	public long getAverageTime() {
		return totalTime/messagesSent;
	}
	
}
