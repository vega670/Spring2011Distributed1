//DistComp - Project
//Alex Sieland
//Matthew Dale
package Router;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class RunRouterLookupTest {
	
	public static void main(String[] args) {
		try{
			
			RouteTable table = new RouteTable();  //for what ports to listen on
			
			table.addServerTable(17654);  //for what servers are linked under that port
			
			for(Integer i : table.getPorts()){
				new Thread( new RouterPort(i, table.getServerTable(i)) ).start();
			}
			
			BufferedReader in = new BufferedReader( new InputStreamReader(System.in) );
			System.out.println("Press enter when ready to do router lookup speed test...");
			in.readLine();
			
			ServerTable sTable = table.getServerTable(17654);
			long startTime = 0;
			long totalTime = 0;
			for(int i = 0; i < 1000; i++){
				startTime = System.currentTimeMillis();
				sTable.getServerLink();
				totalTime += System.currentTimeMillis() - startTime;
			}
			
			System.out.println("Average Lookup Time: " + totalTime/1000);
			
		}catch(Exception e){
			System.err.println("Error: " + e);
		}
		
	}

}
