//DistComp - Project
//Alex Sieland
//Matthew Dale
package Router;

public class RunRouter {

	public static void main(String[] args) {
		try{
			
			RouteTable table = new RouteTable();  //for what ports to listen on
			
			table.addServerTable(17654);  //for what servers are linked under that port
			
			for(Integer i : table.getPorts()){
				new Thread( new RouterPort(i, table.getServerTable(i)) ).start();
			}
			
		}catch(Exception e){
			System.err.println("Error: " + e);
		}
		
	}

}
