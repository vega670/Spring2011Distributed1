package cs4253;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class InputReaderProcess implements Runnable{
	
	private PrintWriter out;
	
	public InputReaderProcess(Socket s) throws IOException{
		out = new PrintWriter(s.getOutputStream(), true);
	}
	
	public void run() {
		BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
		String userInput;
		String username;
		
		try {
			System.out.print("Please enter a username: ");
			username = stdIn.readLine();
			out.println(username);
			System.out.print(username + ": ");
			
			while ((userInput = stdIn.readLine()) != null) {
				out.println(userInput);
				Thread.sleep(50);
				System.out.print(username + ": ");
			}
		} catch(IOException e){
			System.err.println("Error: " + e);
			return;
		} catch (InterruptedException e){
			System.err.println("Interrupted: " + e);
			return;
		}
	}
}
