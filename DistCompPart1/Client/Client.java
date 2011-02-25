package Client;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;

public class Client {
	
	InetAddress host;
	int port;
	
	private static final int timeout = 10000;
	
	public Client(InetAddress host, int port) {
		super();
		this.host = host;
		this.port = port;
	}

	public String send(String message){
		try{
			Socket sock = new Socket(host, port);
			
			PrintWriter out = new PrintWriter(sock.getOutputStream());
			out.println(message);
			out.flush();
			
			sock.setSoTimeout(timeout);
			BufferedReader in = new BufferedReader( new InputStreamReader( sock.getInputStream() ) ) ;
			//while(!in.ready()){};
			message = in.readLine();
			
			sock.close();
			return message;
		}catch(Exception e){
			System.err.println("Error: " + e);
			return "Error: " + e.getMessage();
		}
	}
	
}
