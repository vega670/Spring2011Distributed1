package Server;

import java.io.PrintWriter;

public class UppercaseReturn implements ServerCommand{

	public void command(String input, PrintWriter out){
		out.println(input.toUpperCase());
	}
	
}
