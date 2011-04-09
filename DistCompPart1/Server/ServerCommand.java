package Server;

import java.io.PrintWriter;

public interface ServerCommand {
	
	public void command(String input, PrintWriter out);

}
