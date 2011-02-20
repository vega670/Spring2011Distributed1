package cs4253;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	public static final int PORT = 20999;

	public static void main(String[] args) {

		Socket server = null;
		String host;
		BufferedReader in = null;
		
		if (args.length == 0) {
			try{
				System.out.print("Hostname: ");
				host = new BufferedReader(new InputStreamReader(System.in)).readLine();
			}catch(Exception e){
				host = "localhost"; // otherwise java throws a fit
				System.err.println("Error: " + e);
				System.exit(1);
			}
		}else{
			host = args[0];
		}
		
		try {
			server = new Socket(host, PORT);
			in = new BufferedReader(new InputStreamReader(server.getInputStream()));
		} catch (UnknownHostException e) {
			System.err.println("Don't know about host " + host + ". " + e);
			System.exit(1);
		} catch (IOException e) {
			System.err.println("Couldn't get I/O from the connection to host " + host + ". " + e);
		}
		String serverResponse;


		try {
			(new Thread(new InputReaderProcess(server))).start();

			while ((serverResponse = in.readLine()) != null){
				System.out.println(serverResponse);
				if(serverResponse.equalsIgnoreCase("/disconnect"))
					break;
			}
		} catch (IOException e) {
			System.err.println("Error: " + e);
			System.exit(1);
		}
	}
}
