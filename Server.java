import java.util.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.ServerSocket;
import java.io.*;


/*Todo
* Setup for multiple threads well need this for each connected player. 
* Write back to players.
*/

class Handlers{
	HashMap<Integer,List<Socket>> rooms = new HashMap<Integer,List<Socket>>();
	
	public void join(Socket client,BufferedReader req,PrintWriter res){
		try{
		int roomNum = Integer.parseInt(req.readLine());
		List<Socket> room = rooms.get(roomNum);
			room.add(client);
			System.out.println("Added player to room " + roomNum);
			res.println("JOINED " + roomNum);
			res.flush();
		}
		catch(Exception e){
			System.out.println(e);
			res.println("Error Can't Connect");
			return;
		}
	}
	
	public void create(Socket client,BufferedReader req,PrintWriter res){
		List<Socket> sockets = new ArrayList<Socket>();
		Random rando = new Random();
		int roomNumber = rando.nextInt(10000);
		
		sockets.add(client);
		rooms.put(roomNumber,sockets);
		System.out.println("Created " + roomNumber);
		res.println("CREATE "+ Integer.toString(roomNumber));
		res.flush();
		res.close();
	}
}


class Router{
	Handlers handle = new Handlers();
	public void handle(Socket client) throws IOException{
		String route;
		PrintWriter res = new PrintWriter(client.getOutputStream());
		BufferedReader req = new BufferedReader(new InputStreamReader(client.getInputStream()));
		res.println("Hello , welcome");
		res.flush();
		try{
			route = req.readLine();
			System.out.println("Route: " + route);
		}
		catch(IOException io){
			return;
		}
		switch(route){
		case "join":
			System.out.println("Proceding to Join");
			handle.join(client,req,res);
			return;
		case "create":
			System.out.println("Proceding to Create");
			handle.create(client,req,res);
			return;
		default:
			return;
		}
	}
}

public class Server{
	ServerSocket server;
	Router router;
	public Server() throws IOException{
		server = new ServerSocket(8000);
		router = new Router();
	}
	
	public void start() throws IOException{
		while(true){
			Socket client = server.accept();
			System.out.println("A client has connected");
			router.handle(client);
		}	
	}
	
	public void log(String arg)
	{
		System.out.println(arg);
	}
}