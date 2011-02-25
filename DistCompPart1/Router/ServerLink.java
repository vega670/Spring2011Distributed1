package Router;

import java.net.InetAddress;

public class ServerLink {

	private int port;
	private InetAddress ip;
	
	public ServerLink(int port, InetAddress ip) {
		super();
		this.port = port;
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public InetAddress getIp() {
		return ip;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public void setIp(InetAddress ip) {
		this.ip = ip;
	}
	
}
