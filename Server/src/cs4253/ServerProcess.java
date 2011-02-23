package cs4253;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ServerProcess implements Runnable {
    private UserList users;
    private IRCUser user;
    private BufferedReader in;


    public ServerProcess(Socket client, UserList users) throws IOException, Exception {
        in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        this.users = users;
        String username = in.readLine();
        user = new IRCUser(client, username);
        if(users.usernameExists(username)){
        	Thread.sleep(50);
        	user.sendMessage("Username already exists.");
        	throw new Exception("Client " + client.getInetAddress() + " submitted a duplicate username.");
        }
        users.add(user);
        System.out.println("Client " + client.getInetAddress() + " connected with username " + username + ".");
    }

    public void run(){
    	boolean connected = true;
        String inputLine, command = "", message;
        String[] inputSplit;
        char action;

        try {
            while(connected && (inputLine = in.readLine()) != null){
            	message = "";
            	// parse input
                inputSplit = inputLine.split(" ");
                action = inputSplit[0].charAt(0);
                if(inputSplit.length > 1)
                	for(int i = 1; i < inputSplit.length; i++)
                		message += inputSplit[i] + " ";

                switch(action){
                case '@':	// if @user, find appropriate IRCUser
                	command = inputSplit[0].substring(1, inputSplit[0].length());
                	try{
                		users.sendMessage("#" + user.getUsername() + " " + message, command);
                	}catch(NullPointerException e){
                		users.sendMessage("Username " + command + " not found!", user.getUsername());
                	}
                	message = "";
                    break;
                case '/':	// if /command, execute command
                	command = inputSplit[0].substring(1, inputSplit[0].length());
                	if(command.equalsIgnoreCase("disconnect")){
                		connected = false;
                		users.remove(user);
                		user.sendMessage("/disconnect");
                		in.close();
                		user.close();
                	}else if(command.equalsIgnoreCase("uppercase")){
                		users.sendMessageToAllExcept("#" + user.getUsername() + " " + inputLine, user.getUsername());
                	}
                    break;
                default:
                	users.sendMessageToAllExcept("#" + user.getUsername() + " " + inputLine, user.getUsername());
                }
            }
        } catch(IOException e){
            System.err.println("Client " + user.getSocket().getInetAddress() + " reset connection.");
            users.remove(user);
            return;
        }
    }
}
