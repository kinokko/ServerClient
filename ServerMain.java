import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


public class ServerMain {
	public final static int TCPPORT = 23721;
	
	public static void main(String[] args) {
		ServerSocket server = null;
		Socket connection = null;		
		
		try {
			// open and receive the socket
			server = new ServerSocket(TCPPORT);
			System.out.println("Waiting for client");
			connection = server.accept();
			System.out.println("Client Connected from"  + connection.getRemoteSocketAddress());
			
			while (true) {
				// receive input data from client
				System.out.println("Waiting for data from client");
				BufferedReader dataIn = null;
				dataIn = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				
				int bufferSize = 0;
				bufferSize = dataIn.read();
				if (bufferSize < 0) break;
				
				//dataIn = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				char[] inBuffer=new char[bufferSize];
				dataIn.read(inBuffer, 0, bufferSize);
				System.out.println(new String(inBuffer));
				
	//			System.out.println(dataIn.readLine());
				
				// send data to server
				System.out.println("Try to send data to client...");
				String ostring = "Fine!";
				OutputStream ostream = null;
				ostream = connection.getOutputStream();
				ostream.write(ostring.length());
				PrintWriter pw = new PrintWriter(ostream);
				pw.print(ostring);
				pw.flush();
				System.out.println("Success!");
			}
		}
		catch(IOException e) {
			System.out.println(e);
		}
		
		// close socket
		try {
			server.close();
			System.out.println("Socket Closed");
		}
		catch (IOException e) {
			System.out.println(e);
		}
	}
		
}
