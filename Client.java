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
		boolean start = false; 
		boolean host = false;
		
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
				switch(app.getJSON(json,"Event")){
				case "Create":
					host = true;
				case "Start":
					start = true;
					if(host){
						// Server will write number of players
						int numPlayers = Integer.parseInt(app.getJSON(json,"players"));
						board = new Board(numPlayers);
						gm = new GameManager(numPlayers,server,board);
						// 
					}
				break;
				case "Sync":
					output = server.readLine();
					int numPlayers = Integer.parseInt(app.getJSON(json,"players"));
					board = new Board(numPlayers);
					gm = new GameManager(numPlayers,server,board);
					// Sync data with json.deck , json.hands
					break;
				case "Join":
				case "Leave":
				case "Error":
				case "Msg":
					app.log(app.getJSON(json,"msg"));
					break;
				default:
					app.log("gg");
				}
			}
		}
		while(true){
			if(gm.currPlayer == 1){
				if(in.ready()){
					input = in.readLine();
				}
				gm.playOneTurn();
			}
			if(server.ready()){
				output = server.readLine();
				JSONObject json = (JSONObject)parser.parse(output);
				switch(app.getJSON(json,"Event")){
					case "Move":
					case "Leave":
					case "Msg":
					case "Error":
				}
			}
		}
		
		}
		catch(Exception e){
			System.out.println(e);
		}
		return;
		
	}
	
	public void log(String arg)
	{
		System.out.println(arg);
	}
	public String getJSON(JSONObject obj,String key){
		return obj.get(key).toString();
	}
}