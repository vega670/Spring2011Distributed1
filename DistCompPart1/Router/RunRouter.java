//DistComp - Project
//Alex Sieland
//Matthew Dale
package Router;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class RunRouter {

	public static void main(String[] args) {
		try{
                        String identification = "";
                        byte[] ip;
                        ip = InetAddress.getLocalHost().getAddress();
                        identification += Integer.toString(unsignedByteToInt(ip[0]));
                        for(int i = 1; i < ip.length; i++)
                            identification += "." + Integer.toString(unsignedByteToInt(ip[i]));
                        identification += " " + Integer.toString(DiscoveryService.port) + "/";  // change to the port to connect to

			InetAddress group = InetAddress.getByName(DiscoveryService.address);
                        MulticastSocket socket = new MulticastSocket(DiscoveryService.port);
                        socket.joinGroup(group);
                        DatagramPacket out = new DatagramPacket(identification.getBytes(), identification.length(), group, DiscoveryService.port);
                        socket.send(out);

			RouteTable table = new RouteTable();  //for what ports to listen on
			
			table.addServerTable(17654);  //for what servers are linked under that port
			
                        (new Thread(new DiscoveryService())).start();

			for(Integer i : table.getPorts()){
				new Thread( new RouterPort(i, table.getServerTable(i)) ).start();
			}

		}catch(Exception e){
			System.err.println("Error: " + e);
		}
		
	}

        // found at http://www.rgagnon.com/javadetails/java-0026.html
        private static int unsignedByteToInt(byte b) {
                return (int) b & 0xFF;
        }


}
