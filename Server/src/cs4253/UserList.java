package cs4253;

import java.util.ArrayList;

public class UserList {
    private ArrayList<IRCUser> users;

    public UserList(){
        users = new ArrayList<IRCUser>();
    }

    public void add(IRCUser u){
        users.add(u);
    }
}
