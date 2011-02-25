package Client;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.net.InetAddress;

public class RunClientTest {
	
public static void main(String[] args){
		
		int port = 17654;
		
		try{
		
			InetAddress ip;
			
			if(args.length == 0){
				System.out.print("Router IP: ");
				BufferedReader in = new BufferedReader( new InputStreamReader(System.in) );
				ip = InetAddress.getByName(in.readLine());
			}else{
				ip = InetAddress.getByName(args[0]);
			}
			
			Client client = new Client(ip, port);
			BufferedReader in = new BufferedReader( new FileReader("loremIpsum.txt") );
			while(in.ready()){
				System.out.println( client.send( in.readLine() ) );
			}
			
			System.out.println( "\nMessages Sucessfully Sent: " + client.getMessagesSent() );
			System.out.println( "Messages Failed to Send: " + client.getMessagesFailed() );
			System.out.println( "Average  Message Length: " + client.getAverageLength() );
			System.out.println( "Average  Send/Recieve Time: " + client.getAverageTime() + "ms");
			
		}catch(Exception e){
			System.err.println("Error: " + e);
		}
		
	}
	
}
