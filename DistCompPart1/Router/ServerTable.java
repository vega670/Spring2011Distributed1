//DistComp - Project
//Alex Sieland
//Matthew Dale
package Router;

import java.net.InetAddress;
import java.util.Comparator;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;

public class ServerTable {
	    
	final Comparator<InetAddress> COMPARE_INETADDRESS = new Comparator<InetAddress>(){
        public int compare(InetAddress one, InetAddress two){
            return one.getHostAddress().compareTo(two.getHostAddress());
        }
    };
	
	TreeMap<InetAddress, ServerLink> table;

	public ServerTable() {
		super();
		this.table = new TreeMap<InetAddress, ServerLink>(COMPARE_INETADDRESS);
	}
	
	public void addServerLink(ServerLink link){
		table.put(link.getIp(), link);
	}
	
	public void removeServerLink(InetAddress ip){
		table.remove(ip);
	}
	
	public ServerLink getServerLink(InetAddress ip){
		return table.get(ip);
	}
	
	public ServerLink getServerLink(){
		int stop = new Random().nextInt(table.size());
		int ii = 0;
		for(InetAddress i : table.keySet()){
			if(stop == ii){
				return getServerLink(i);
			}else{
				ii++;
			}
		}
		return null;
	}
	
	public boolean checkForAddress(InetAddress ip){
		return table.containsKey(ip);
	}
	
	public Set<InetAddress> getAddresses(){
		return table.keySet();
	}

}
