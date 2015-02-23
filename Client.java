import java.util.*;
import java.net.Socket;
import java.net.ServerSocket;
import java.io.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/* Todo
 * Setup to recieve server responses.
 */


public class Client{
	public static void main(String[] arg) throws ParseException
	{
		try{
		Client app = new Client();
		Socket connection = new Socket("127.0.0.1",8000);
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter req = new PrintWriter(connection.getOutputStream(), true);
		BufferedReader server = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		JSONParser parser = new JSONParser();
		String input;
		String output;
		GameManager gm;
		Board board;
		Boolean start = false; 
		
		// Client
		while(!start)
		{
			// Print it to the server
			if(in.ready()){
				input = in.readLine();
				req.println(input);
			}
			// Read input from server
			if(server.ready()){
				output = server.readLine();
				JSONObject json = (JSONObject)parser.parse(output);
				switch(app.JSONget(json,"request")){
				case "Start":
					start = true;
					if(true){// if host
						// Create board
						// create gm
						// Send deck data and hand data.
					}
				break;
				case "Sync":
					// create gm 
					// sync data from server.
				default:
					app.log("gg");
				}
			}
		}
		
		
		// Game loop
		/*
		while(true){
			if(gm.currPlayer == 1)
			{
				if(in.ready()){
					input = in.readLine();
				}
				gm.playOneTurn();
			}
			if(server.ready()){
				output = server.readLine().split(",");
				gm.playOneTurn();
			}
		}*/
		
		
		
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
	public String JSONget(JSONObject obj,String key){
		return obj.get(key).toString();
	}
}