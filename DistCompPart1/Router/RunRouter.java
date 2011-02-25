package Router;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;

public class RunRouter {

	public static void main(String[] args) {
		try{
			
			InetAddress ip;
			
			RouteTable table = new RouteTable();
			
			if(args.length == 0){
				System.out.print("Server IP: ");
				BufferedReader in = new BufferedReader( new InputStreamReader(System.in) );
				ip = InetAddress.getByName(in.readLine());
			}else{
				ip = InetAddress.getByName(args[0]);
			}
			
			table.addServerLink(17654, new ServerLink(17655, ip));
			
			for(Integer i : table.getPorts()){
				new Thread( new RouterPort(i, table) ).start();
			}
			
		}catch(Exception e){
			System.err.println("Error: " + e);
		}
		
	}

}
