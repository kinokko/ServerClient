import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;


public class ClientMain {
	
	public final static String LOCALHOST = "127.0.0.1";
	public final static int TCPPORT = 23721;
	
	public static void main(String[] args) {
		Socket client = null;
		OutputStream ostream = null;
		PrintWriter pw = null;
		String serverip = "142.58.35.188";
		Scanner scanner = new Scanner(System.in);
		
		
		try {
			// connect to a server
			client = new Socket();
			System.out.println("Connecting to Server");
			InetSocketAddress socaddr = new InetSocketAddress(LOCALHOST, TCPPORT);
			client.connect(socaddr);
			System.out.println("Server Connected");
			
			while (true) {
				// send data to server
				System.out.println("Try to send data to server...");
				String ostring = "Play less game, do more work!";
				ostream = client.getOutputStream();
				ostream.write(ostring.length());
				pw = new PrintWriter(ostream);
				pw.print(ostring);
				pw.flush();
				System.out.println("Success!");
				
				// receive input data from server
				System.out.println("Waiting for reply");
				BufferedReader dataIn = new BufferedReader(new InputStreamReader(client.getInputStream()));
				int bufferSize = dataIn.read();
				
				//dataIn = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				char[] inBuffer=new char[bufferSize];
				dataIn.read(inBuffer, 0, bufferSize);
				System.out.println(new String(inBuffer));
				
				System.out.println("Enter Y to close connection, or press enter to continue");
				
				String usrinput = scanner.nextLine();
				if (usrinput.equalsIgnoreCase("Y")) break; 
			}
		}
		catch (IOException e) {
			System.out.println(e);
		}
		
		try {
			client.close();
			System.out.println("Socket Closed");
		}
		catch (IOException e) {
			System.out.println(e);
		}
		
		scanner.close();
	}
}
