package cs4253;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class Client extends JPanel implements ActionListener {
	public static final int PORT = 20999;
	private static final long serialVersionUID = -3774278101232795311L;
	private static Socket server;
	private static Client client;
	private String username;
	private PrintWriter out;
	private JTextField messageInput;
	private JTextArea messageOutput;
	private JScrollPane scrollPane;
	
	private Client(){
		super(new GridBagLayout());
		
		try {
			out = new PrintWriter(Client.server.getOutputStream(), true);
			
			System.out.print("Please enter a username: ");
			username = (new BufferedReader(new InputStreamReader(System.in))).readLine();
		} catch (IOException e) {
			System.err.println("Error: " + e);
		}
		out.println(username);
		
		messageInput = new JTextField(20);
		messageInput.addActionListener(this);
		
		messageOutput = new JTextArea(5, 20);
		messageOutput.setEditable(false);
		scrollPane = new JScrollPane(messageOutput);
		
		GridBagConstraints c = new GridBagConstraints();
		
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.fill = GridBagConstraints.HORIZONTAL;
		add(messageInput, c);
		
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1.0;
		c.weighty = 1.0;
		add(scrollPane, c);
	}
	
	private void scrollPaneToBottom() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				scrollPane.getVerticalScrollBar().setValue(
						scrollPane.getVerticalScrollBar().getMaximum());
			}
		});
	}
	
	public static void addInputLine(String text){
		Client.client.printToTextArea(text);
	}
	
	private void printToTextArea(String text){
		messageOutput.append(text + "\n");
		messageOutput.setCaretPosition(messageInput.getDocument().getLength());
		scrollPaneToBottom();
	}
	
	public void actionPerformed(ActionEvent evt){
		String text = messageInput.getText();
		printToTextArea(username + ": " + text);
		sendToServer(text);
		messageInput.selectAll();
	}
	
	public static void sendMessage(String message){
		Client.client.sendToServer(message);
	}
	
	private synchronized void sendToServer(String message){
		out.println(message);
	}
	
	private static void createAndShowGUI(){
		JFrame frame = new JFrame("Client");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setMinimumSize(new Dimension(400, 500));
		
		Client.client = new Client();
		
		frame.add(Client.client);
		
		frame.pack();
		frame.setVisible(true);
	}

	public static void main(String[] args){
		Client.server = null;
		String host;
		
		if (args.length == 0) {
			try{
				System.out.print("Hostname: ");
				host = (new BufferedReader(new InputStreamReader(System.in))).readLine();
			}catch(Exception e){
				host = "localhost"; // otherwise java throws a fit
				System.err.println("Error: " + e);
				System.exit(1);
			}
		}else{
			host = args[0];
		}
		
		try {
			Client.server = new Socket(host, PORT);
		} catch (UnknownHostException e) {
			System.err.println("Don't know about host " + host + ". " + e);
			System.exit(1);
		} catch (IOException e) {
			System.err.println("Couldn't get I/O from the connection to host " + host + ". " + e);
		}
		
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
		(new Thread(new ResponseHandlerProcess(server))).start();
		
		
	}
}
