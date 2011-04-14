//DistComp - Project
//Alex Sieland
//Matthew Dale
package Router;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class DiscoveryService implements Runnable{
    public static final String address = "229.229.1.1";
    public static final int port = 9876;

    public void run(){
        InetAddress group = null;
        MulticastSocket socket = null;
        try{
            group = InetAddress.getByName(address);
            socket = new MulticastSocket(port);
            socket.joinGroup(group);
        }catch(IOException e){

        }

        byte[] buffer = new byte[1000];
        DatagramPacket in = new DatagramPacket(buffer, buffer.length);
        String inString, inSplit[];
        while(true){
            inString = "";
            try{
                socket.receive(in);
            }catch(IOException e){

            }
            for(int i = 0; ((char)buffer[i]) != '/';i++)
                inString += (char)buffer[i];
            inSplit = inString.split(" ");
            // add new server with address inSplit[0] and port Integer.parseInt(inSplit[1])
            System.out.println("New server: " + inSplit[0] + ":" + Integer.parseInt(inSplit[1]) + ".");
        }
    }
}
