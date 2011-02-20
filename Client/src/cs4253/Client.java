package cs4253;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	public static final int PORT = 20999;

	public static void main(String[] args) {
		if (args.length == 0) {
			System.out.println("Client usage:");
			System.out.println("   java Client <hostname>");
			System.exit(1);
		}

		Socket server = null;
		String host = args[0];
		BufferedReader in = null;

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
				System.out.println("\n" + serverResponse);
				if(serverResponse.equalsIgnoreCase("/disconnect"))
					break;
			}
		} catch (IOException e) {
			System.err.println("Error: " + e);
			System.exit(1);
		}
	}
}
