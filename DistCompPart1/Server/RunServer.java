//DistComp - Project
//Alex Sieland
//Matthew Dale
package Server;

import java.net.InetAddress;

public class RunServer {

	public static void main(String[] args){
		
		RunServer.runServer();
		
	}
	
	public static void runServer(){
		
		int routingPort = 17655;
		int messagePort = 17755;
		try{
			
			Thread mServer = new Thread( new Server(messagePort, new UppercaseReturn()) );
			mServer.start();
			Thread rServer =  new Thread(new Server(routingPort, new AddressReturn(InetAddress.getLocalHost(), messagePort)) );
			rServer.start();
			
		}catch(Exception e){
			System.err.println("Error: " + e);
		}
		
	}
	
}
