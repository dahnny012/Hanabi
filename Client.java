import java.util.ArrayList;
import java.util.*;
import java.net.Socket;
import java.net.ServerSocket;
import java.io.*;

/* Todo
 * Setup to recieve server responses.
 */


public class Client{
	public static void main(String[] arg)
	{
		try{
		Client app = new Client();
		Socket connection = new Socket("127.0.0.1",8000);
		
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter req = new PrintWriter(connection.getOutputStream(), true);
		String input;
		// Get a line from stdin. 
		while((input = in.readLine()) != null)
		{
			req.println(input);
		}
		// Write it to connected server.
		
		
		}
		catch(IOException e){
			System.out.println("Error connecting");
		}
		return;
		
	}
	
	public void log(String arg)
	{
		System.out.println(arg);
	}
}