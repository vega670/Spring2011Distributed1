//DistComp - Project
//Alex Sieland
//Matthew Dale
package Router;

//import java.util.Comparator;
import java.util.Set;
import java.util.TreeMap;

public class RouteTable {

//    final Comparator<Integer> COMPARE_INTS = new Comparator<Integer>(){
//        public int compare(Integer one, Integer two){
//            return one.compareTo(two);
//        }
//    };
    
	TreeMap<Integer, ServerTable> table;

	public RouteTable() {
		super();
		this.table = new TreeMap<Integer, ServerTable>();
	}
	
	public void addServerTable(int port){
		table.put(port, new ServerTable());
	}
	
	public void removeServerTable(int port){
		table.remove(port);
	}
	
	public ServerTable getServerTable(int port){
		return table.get(port);
	}
	
	public Set<Integer> getPorts(){
		return table.keySet();
	}

}
