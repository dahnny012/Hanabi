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


class Connection{
	Socket socket;
	PrintWriter req;
	BufferedReader res;
	public Connection(String url,int port){
		socket = new Socket(url,port);
		req = new PrintWriter(socket.getOutputStream(), true);
		res = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	}
	
	public void close(){
		socket.close();
		req.close();
		res.close();
	}
	public void send(String str){
		req.println(str);
	}
}

public class Client{
	JSONParser parser = new JSONParser();
	int userid;
	Connection request;
	Connection server;
	boolean start = false;
	boolean host = false;
	GameManager gm;
	Board board;
	
	public static void main(String[] arg) throws ParseException
	{
		try{
		Client app = new Client();
		String input;
		String output;
		String args[];
		String url = "127.0.0.1";
		int port = 8000;
		
		
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		while(true){
			if(in.ready()){
				input = in.readLine();
				args = input.split(" ");
				// Do things
				// Create a persistent socket on
				// msgs Create,Join
			}
			// if a persistent socket was made..
				// poll it for response.
		}
		
		// Client
		while(!app.start)
		{
			if(in.ready()){
				input = in.readLine();
				app.request = new Connection(url,port);
				int keep = app.handleReq(input);
				
				// wait for server response
				while(!app.request.res.ready());
				output = app.request.res.readLine();
				app.handleRes(output);
				
				if(keep != 1){
					app.request.close();
				}
				else{
					// shallow copy but it should be fine. Might want to test this though
					app.server = app.request;
				}
			}
		}
		
		
		// Game things	
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
	
	public int handleReq(String msg){
		int keep = 0;
		String[] args = msg.split(" ");
		JSONObject json=new JSONObject();
		StringWriter out = new StringWriter();
		switch(args[0]){
			case "Join":
				json.put("Request","Join");
				json.put("roomId",args[1]);
				keep = 1;
				break;
			case "Create":
				json.put("Request","Create");
				keep = 1;
				break;
			case "Start":
				if(host){
				    json.put("Request","Start");
					json.put("roomId",args[1]);
					// json put board info;
				}else{
					return keep;
				}
				break;
			case "Msg":
				json.put("Request","Msg");
				String content = "";
				
				// Compile the message
				int length = args.length;
				for(int i=1; i<length; i++){
					content += args[i]+" ";
				}
				json.put("msg",content);
				break;
		}
		json.writeJSONString(out);
		String eventMsg = json.toString();
		request.req.println(eventMsg);
		
		//See if we are keeping the socket or not.
		return keep;
	}
	
	public void handleRes(String res){
		// Turn it into a json object
		JSONObject json = (JSONObject)parser.parse(res);
		switch(getJSON(json,"Event")){
			case "Create":
				host = true;
				break;
			case "Start":
				start = true;
				if(host){
					if (board == null && gm == null){
						// Server will write number of players
						int numPlayers = Integer.parseInt(app.getJSON(json,"players"));
						board = new Board();
						gm = new GameManager();
					    //board.initDeck();
					    //board.initHands();
						// Encode through JSON
						// Send Start, and data
				    }
				}
				break;
			case "Sync":
				int numPlayers = Integer.parseInt(app.getJSON(json,"players"));
				Deck deck = app.getJSON(json,"deck"); // Not implemented yet
				Hand hand = app.getJSON(json,"hand"); // Not implemented yet
				board = new Board(numPlayers);
				gm = new GameManager();
				break;
			case "Join":
				// Increment a player count;
			case "Leave":
				// Decrement a player count;
			case "Error":
			case "Msg":
				app.log(app.getJSON(json,"msg"));
				break;
			default:
				app.log("gg");
		}
	}
}