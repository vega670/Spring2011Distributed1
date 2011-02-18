package cs4253;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class Server {
    public static final int PORT = 20999;

    public static void main(String[] args){
        ServerSocket server = null;
        ArrayList<Thread> threads = new ArrayList<Thread>();

        try {
            server = new ServerSocket(PORT);
        } catch (IOException e){
            System.out.println("Could not listen on port " + PORT + ". " + e);
            System.exit(-1);
        }

        Socket client = null;

        while (true){
            try {
                client = server.accept();
            } catch (IOException e){
                System.out.println("Accepting connection failed on port " + PORT + ". " + e);
                System.exit(-1);
            }
            try {
                threads.add(new Thread(new ServerProcess(client)));
            } catch(IOException e){
                System.out.println("Error: " + e);
            }
        }
    }
}
