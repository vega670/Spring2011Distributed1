package cs4253;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ResponseHandlerProcess implements Runnable{
	private BufferedReader in;
	
	public ResponseHandlerProcess(Socket server){
		try {
			in = new BufferedReader(new InputStreamReader(server.getInputStream()));
		} catch (IOException e) {
			System.err.println("Couldn't get I/O from the connection to host " + server.getInetAddress() + ". " + e);
		}
	}
	
	public void run() {
		String serverResponse, command = "", message, sender;
		String[] responseSplit;
		char action;
		try {
			while ((serverResponse = in.readLine()) != null){
				message = "";
				sender = "";
				responseSplit = serverResponse.split(" ");
				action = responseSplit[0].charAt(0);
				if(responseSplit.length > 1)
					for(int i = 1; i < responseSplit.length; i++)
						message += responseSplit[i] + " ";
				command = responseSplit[0].substring(1, responseSplit[0].length());
				
				switch(action){
				case '#':
					sender = responseSplit[0].substring(1, responseSplit[0].length());
					if(responseSplit[1].charAt(0) == '/'){
						message = "";
						for(int i = 2; i < responseSplit.length; i++)
							message += responseSplit[i] + " ";
						command = responseSplit[1].substring(1, responseSplit[1].length());
					}else{
						Client.addInputLine(sender + ": " + message);
						break;
					}
				case '/':
					if(command.equalsIgnoreCase("disconnect")){
						Client.addInputLine("Disconnected");
                	}else if(command.equalsIgnoreCase("uppercase")){
                		Client.sendMessage("@" + sender + " " + message.toUpperCase());
                	}
					break;
				default:
					Client.addInputLine(serverResponse);
				}
			}
		} catch (IOException e) {
			System.err.println("Error: " + e);
			System.exit(1);
		}
	}

}
