package Server;

public class RunServer {

	public static void main(String[] args){
		
		int port = 17655;
		try{
			
			Server server = new Server(port);
			server.run();
			
		}catch(Exception e){
			System.err.println("Error: " + e);
		}
		
	}
	
}
