import java.util.*;
import java.net.Socket;
import java.net.ServerSocket;
import java.io.*;


/*Todo
* Setup for multiple threads well need this for each connected player. 
* Write back to players.
*/

class Handlers{
	HashMap<Integer,List<ClientSocket>> rooms = new HashMap<Integer,List<ClientSocket>>();
	
	public void join(ClientSocket client,BufferedReader req){
		try{
		String roomNum = req.readLine();
		List<ClientSocket> room = rooms.get(roomNum);
		room.add(client);
		}
		catch(IOException io){
			return;
		}
	}
	
	public int create(ClientSocket client){
		List<ClientSocket> sockets = new ArrayList<ClientSocket>();
		sockets.add(client);
		Random rando = new Random();
		int roomNumber = rando.nextInt(10000);
		rooms.put(roomNumber,sockets);
		return roomNumber;
	}
}


class Router{
	Handlers handle = new Handlers();
	public void handle(ClientSocket client,BufferedReader req,PrintWriter res){
		String route;
		try{
			route = req.readLine();
		}
		catch(IOException io){
			return;
		}
		switch(route){
		case "join":
			handle.join(client,req);
			return;
		case "create":
			int roomNum = handle.create(client);
			res.write(Integer.toString(roomNum)+"/n");
			return;
		default:
			return;
		}
	}
}

class ClientSocket extends Socket{
	public String req; // Join # or Create.
	public void setReq(String req){
		this.req = req;
	}
	
}

public class Server{
	ServerSocket server;
	Router router;
	public Server() throws IOException{
		server = new ServerSocket(8000);
	}
	public ClientSocket acceptConnection()
	{
		try{
			ClientSocket client = (ClientSocket) server.accept();
			return client;
		}
		catch(IOException e){
			System.out.println("Error with server");
			return null;
		}
	}
	
	public void dispatcher(ClientSocket client){
		try{
		PrintWriter response = new PrintWriter(client.getOutputStream());
		BufferedReader request = new BufferedReader(new InputStreamReader(client.getInputStream()));
		router.handle(client,request,response);
		}
		catch(IOException e){
			return;
		}
	}
	
	public void log(String arg)
	{
		System.out.println(arg);
	}
}