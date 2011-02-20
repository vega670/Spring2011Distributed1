package cs4253;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class Server {
    public static final int PORT = 20999;

    public static void main(String[] args){
        ServerSocket server = null;
        ArrayList<Thread> threads = new ArrayList<Thread>();
        UserList users = new UserList();

        try {
            server = new ServerSocket(PORT);
        } catch (IOException e){
            System.out.println("Could not listen on port " + PORT + ". " + e);
            System.exit(-1);
        }

        Socket client;
        Thread clientThread;

        while (true){
            try {
                //client = server.accept();
                //System.out.println("Client connected: " + client.getInetAddress());
                clientThread = new Thread(new ServerProcess(server.accept(), users));
                clientThread.start();
                threads.add(clientThread);
            } catch (IOException e){
                System.out.println("Accepting connection failed on port " + PORT + ". " + e);
                System.exit(-1);
            }
        }
    }
}
