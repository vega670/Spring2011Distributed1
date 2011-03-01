package Client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;

import Server.Server;

public class RunClient {

	public static void main(String[] args){
		
		int port = 17654;
		
		try{
		
			InetAddress ip;
			BufferedReader in = new BufferedReader( new InputStreamReader(System.in) );
			
			if(args.length == 0){
				System.out.print("Router IP: ");
				ip = InetAddress.getByName(in.readLine());
			}else{
				ip = InetAddress.getByName(args[0]);
			}
			
			new Thread( new Server(17655)).start(); 
			Client client = new Client(ip, port);
			while(true){
				System.out.println( client.send( in.readLine() ) );
			}
			
		}catch(Exception e){
			System.err.println("Error: " + e);
		}
		
	}
	
}
