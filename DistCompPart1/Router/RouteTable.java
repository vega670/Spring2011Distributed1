package Router;

import java.util.Set;
import java.util.TreeMap;

public class RouteTable {
	
	TreeMap<Integer, ServerLink> table;

	public RouteTable() {
		super();
		this.table = new TreeMap<Integer, ServerLink>();
	}
	
	public void addServerLink(int port, ServerLink link){
		table.put(new Integer(port), link);
	}
	
	public void removeServerLink(int port){
		for(Integer i : table.keySet()){
			if(i.equals(port)){
				table.remove(i);
			}
		}
	}
	
	public ServerLink getServerLink(int port){
		for(Integer i : table.keySet()){
			if(i.equals(port)){
				return table.get(i);
			}
		}
		
		return null;
	}
	
	public Set<Integer> getPorts(){
		return table.keySet();
	}

}
