package Server;

import java.io.PrintWriter;
import java.net.InetAddress;

public class AddressReturn implements ServerCommand {
	
	int port;
	InetAddress ip;

	public AddressReturn(InetAddress ip, int port) {
		super();
		this.port = port;
		this.ip = ip;
	}

	public void command(String input, PrintWriter out) {
		out.println(ip.getHostAddress());
		out.println(port);
	}

}
