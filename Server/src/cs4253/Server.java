package cs4253;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {
    public static final int PORT = 20999;

    public static void main(String[] args){
        ServerSocket server = null;
        UserList users = new UserList();

        try {
            server = new ServerSocket(PORT);
        } catch (IOException e){
            System.err.println("Could not listen on port " + PORT + ". " + e);
            System.exit(-1);
        }

        while (true){
            try {
                (new Thread(new ServerProcess(server.accept(), users))).start();
            } catch (IOException e){
                System.err.println("Accepting connection failed on port " + PORT + ". " + e);
                System.exit(-1);
            } catch (Exception e){
            	System.err.println(e);
            }
        }
    }
}
