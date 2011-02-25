package Router;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeMap;

public class RouteTable {

        final Comparator<Integer> COMPARE_INTS = new Comparator<Integer>(){
            public int compare(Integer one, Integer two){
                return one.compareTo(two);
            }
        };
	TreeMap<Integer, ServerLink> table;

	public RouteTable() {
		super();
		this.table = new TreeMap<Integer, ServerLink>();
	}
	
	public void addServerLink(int port, ServerLink link){
		table.put(port, link);
	}
	
	public void removeServerLink(int port){
		table.remove(port);
	}
	
	public ServerLink getServerLink(int port){
		return table.get(port);
	}
	
	public Set<Integer> getPorts(){
		return table.keySet();
	}

}
