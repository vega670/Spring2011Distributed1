package cs4253;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class IRCUser {
    private final String username;
    private PrintWriter out;
    private Socket s;

    public IRCUser(Socket s, String username) throws IOException{
        this.username = new String(username);
        this.s = s;
        out = new PrintWriter(s.getOutputStream(), true);
    }

    public synchronized void sendMessage(String message){
        out.println(message);
    }

    public String getUsername(){
        return username;
    }
    
    public Socket getSocket(){
    	return s;
    }
    
    public void close(){
    	out.close();
    	try {
    		s.close();
    	} catch(IOException e){
    		System.err.println("Error: " + e);
    		System.exit(-1);
    	}
    }
    
    public boolean equals(IRCUser u){
    	return (username == u.getUsername());
    }
}
