package cs4253;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class IRCUser {
    private final String username;
    private PrintWriter out;

    public IRCUser(Socket s, String username){
        this.username = new String(username);
        try {
            out = new PrintWriter(s.getOutputStream());
        } catch(IOException e){
            System.out.println("Error: " + e);
        }
    }

    public synchronized void sendMessage(String message){
        out.println(message);
    }

    public String getUsername(){
        return username;
    }
}
