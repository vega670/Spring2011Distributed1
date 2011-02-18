package cs4253;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ServerProcess implements Runnable {
    //public IRCUser user;
    //private PrintWriter out;
    private BufferedReader in;


    public ServerProcess(Socket s) throws IOException{
        //user = new IRCUser(s);
        //out = new PrintWriter(s.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(s.getInputStream()));
    }

    public void run(){
        String inputLine, outputLine;
        String[] inputSplit;
        char action;

        try {
            while((inputLine = in.readLine()) != null){
                inputSplit = inputLine.split(" ");
                action = inputSplit[0].charAt(0);

                switch(action){
                    case '@':
                        break;
                    case '/':
                        break;
                }
                // parse input
                // if @user, find appropriate IRCUser
                // if /command, execute command
            }
        } catch(IOException e){
            // PROBABLY INCORRECT! how to make a thread kill itself?
            System.out.println("Error: " + e);
            System.exit(-1);
        }
    }
}
