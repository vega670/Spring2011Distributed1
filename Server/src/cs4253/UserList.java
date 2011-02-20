package cs4253;

import java.util.ArrayList;

public class UserList {
    private ArrayList<IRCUser> users;

    public UserList(){
        users = new ArrayList<IRCUser>();
    }

    public synchronized void add(IRCUser u){
        users.add(u);
    }
    
    public synchronized void remove(IRCUser u){
    	users.remove(u);
    }
    
    public void sendMessage(String message, String username){
    	for(IRCUser u : users){
    		if(u.getUsername().equalsIgnoreCase(username)){
    			u.sendMessage(message);
    			break;
    		}
    	}
    }
    
    public void sendMessageToAll(String message){
    	for(IRCUser u : users)
    		u.sendMessage(message);
    }
    
    public void sendMessageToAllExcept(String message, String username){
    	for(IRCUser u : users)
    		if(!u.getUsername().equalsIgnoreCase(username))
    			u.sendMessage(message);
    }
}
