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
		BufferedReader server = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String input;
		String output;
		GameManager gm;
		Board board;
		
		
		// Client
		while(true)
		{
			// Print it to the server
			if(in.ready()){
				input = in.readLine();
				req.println(input);
			}
			// Read input from server
			if(server.ready()){
				output = server.readLine();
				app.log(output);
				if(output.equals("Start")){
					gm = new GameManager(0, 0, 0);
					board = new Board(0, 0);
					break;
				}
			}
		}
		
		
		// Game loop
		while(true){
			if(gm.currPlayer == 1)
			{
				if(in.ready()){
					input = in.readLine();
				}
				gm.playOneTurn();
			}
			if(server.ready()){
				output = server.readLine();
				gm.playOneTurn();
			}
		}
		
		
		
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