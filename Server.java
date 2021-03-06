import java.util.*;
import java.net.Socket;
import java.net.ServerSocket;
import java.io.*;

import org.json.simple.JSONObject;



class Handlers{
	HashMap<Integer,HashMap<Integer,ClientSocket>> rooms = 
			new HashMap<Integer,HashMap<Integer,ClientSocket>>();
	
	public void join(ClientSocket client,JSONObject args){
		try{
			
			int roomNum = Integer.parseInt(args.get("roomNum"));
			
			if(client.roomNum != 0)
				leave(client);
			
			HashMap<Integer, ClientSocket> room = rooms.get(roomNum);
			client.roomNum = roomNum;
			String msg;
			
			room.put(client.id, client);
			System.out.println("Added player to room " + roomNum);
			
			JSONObject joinMsg=new JSONObject();
			StringWriter out = new StringWriter();
			joinMsg.put("Event","Join");
			joinMsg.put("roomNum",roomNum);
			joinMsg.put("userId",client.id);
			joinMsg.writeJSONString(out);
			msg = joinMsg.toString();
			
			client.write(msg);
			broadcast(roomNum,room,client,msg);
		}
		catch(Exception e){
			System.out.println(e);
			writeError(client,"Cant connect");
		}
	}
	
	public void create(ClientSocket client) throws IOException{
		if(client.roomNum != 0)
			leave(client);
		
		HashMap<Integer, ClientSocket> room = new HashMap<Integer, ClientSocket>();
		Random rando = new Random();
		int roomNum = rando.nextInt(10000);
		
		room.put(client.id,client);
		client.roomNum = roomNum;
		rooms.put(roomNum,room);
		System.out.println("Created " + roomNum);
		//client.write("CREATE "+ Integer.toString(roomNum));
		
		JSONObject obj=new JSONObject();
		StringWriter out = new StringWriter();
		obj.put("Event","Create");
		obj.put("roomNum",roomNum);
		obj.writeJSONString(out);
		client.write(obj.toString());
	}
	
	public void message(ClientSocket client,JSONObject args) throws IOException
	{
		HashMap<Integer, ClientSocket> room = rooms.get(client.roomNum);
		try{
		String msg = args.get("msg");
		
		JSONObject obj=new JSONObject();
		StringWriter out = new StringWriter();
		obj.put("Event","Msg");
		obj.put("msg",msg);
		obj.writeJSONString(out);
		String eventMsg = obj.toString();
		
		broadcast(client.roomNum,room,client,eventMsg);
		}
		catch(Exception e){
			System.out.println(e);
			writeError(client,"Cant Message");
		}
	}
	
	public void leave(ClientSocket client) throws IOException{
		// If in a game
		if(client.roomNum > 0){
			HashMap<Integer, ClientSocket> room = rooms.get(client.roomNum);
			
			JSONObject obj=new JSONObject();
			StringWriter out = new StringWriter();
			obj.put("Event","Leave");
			obj.put("msg","A Player has left");
			obj.writeJSONString(out);
			String eventMsg = obj.toString();

			for(ClientSocket player :room.values()){
				if(player.id == client.id){
					broadcast(client.roomNum, room, client,eventMsg);
					room.remove(client.id);
				}
			}
			return;
		}
	}
	
	public void move(ClientSocket client,JSONObject args){
		// Broadcast move to all players
		HashMap<Integer, ClientSocket> room = rooms.get(client.roomNum);
		try{
		String msg = args.get("move");
		
		JSONObject obj=new JSONObject();
		StringWriter out = new StringWriter();
		obj.put("Event","Move");
		obj.put("msg","move");
		obj.writeJSONString(out);
		String eventMsg = obj.toString();
		
		broadcast(client.roomNum,room,client,eventMsg);
		}
		catch(Exception e){
			System.out.println(e);
			writeError(client,"Cant send move");
		}
	}
	
	private void broadcast(int roomNum,HashMap<Integer,ClientSocket> room,ClientSocket client,String msg) throws IOException{
		System.out.println("Room size: " + room.size());
		for(ClientSocket player :room.values()){
			if(player.id != client.id){
				player.write(msg);
			}
		}
	}
	
	public void start(ClientSocket client){
		// Read the data
		// Create a Json object
		// broadcast the data to the room
	}
	
	public void writeError(ClientSocket client,String msg){
		JSONObject obj=new JSONObject();
		StringWriter out = new StringWriter();
		obj.put("Event","Error");
		obj.put("msg",msg);
		obj.writeJSONString(out);
		client.write(obj.toString());
	}
}


class Router{
	Handlers handle;
	private static Router router;
	private Router(){
		handle = new Handlers();
	}
	
	public static Router getInstance(){
		if(router == null)
            router = new Router();
        return router;
	}
	
	public void handle(ClientSocket client) throws IOException{
		String route;
		String[] args;
		String request;
		JSONObject json;
		try{
			request = client.read();
			JSONObject json = (JSONObject)parser.parse(output);
			sign(client,json);
			route = json.get("Route");
			System.out.println("Route: " + route);
		}
		catch(IOException io){
			System.out.println("Error reading");
			System.out.println(io);
			handle.writeError(client, "Error reading");
			return;
		}
		switch(route){
		case "Join":
			System.out.println("Proceding to Join");
			handle.join(client,json);
		case "Create":
			System.out.println("Proceding to Create");
			handle.create(client);
		case "Msg":
			System.out.println("Proceding to Message");
			handle.message(client,json);
		case "Leave":
			System.out.println("Proceding to leave");
			handle.leave(client);
		case "Move":
			System.out.println("Proceding to leave");
			handle.move(client,json);
		default:
		}
	}
	
	public void sign(ClientSocket client,JSONObject json){
		client.id = Integer.parseInt(json.get("userId"));
		client.roomNum = Integer.parseInt(json.get("roomNum"));
	}
}

public class Server{
	ServerSocket server;
	public Server() throws IOException{
		server = new ServerSocket(8000);
	}
	
	public void start() throws IOException, InterruptedException{
		CountSemaphore sem = new CountSemaphore();
		for(int i=0; i<3; i++){
			System.out.println("Making Dispatchers");
			(new Thread(new Dispatch(server,sem))).start();
		}
		for(int j=0; j<5; j++){
			System.out.println("Making workers");
			(new Thread(new Worker(sem))).start();
		}
		Thread.sleep(0);
	}
	
	public void log(String arg)
	{
		System.out.println(arg);
	}
}


class Dispatch implements Runnable{
	ServerSocket server;
	CountSemaphore sem;
	Clients clients = Clients.getInstance();
	public Dispatch(ServerSocket server,CountSemaphore sem){
		this.sem = sem;
		this.server = server;
	}
	
	@Override	
	public void run() {
		while(true){
			try {
				Socket socket = server.accept();
				ClientSocket client = new ClientSocket(socket);
				clients.addConnection(client);
				//client.write("Welcome");
				sem.produce();
				System.out.println("Found a client");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}

class Worker implements Runnable{
	Clients clients = Clients.getInstance();
	CountSemaphore sem;
	public Worker (CountSemaphore sem){
		this.sem = sem;
	}
	@Override
	public void run() {
		while(true){
			try {
				//System.out.println("Semaphore: " + sem.signals);
				sem.consume();
			} catch (InterruptedException e1) {
				System.out.println("sem fked up");
			}
			ClientSocket task = clients.getWork();
			if(task != null){
				Router route = Router.getInstance();
				try {
					route.handle(task);
				} catch (IOException e) {
					e.printStackTrace();
				}
					sem.produce();
				//System.out.println("Semaphore: " + sem.signals);
				//clients.print();
			}
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
		
	}	
}

class ClientSocket{
	Socket socket;
	public int id;
	public int roomNum;
	PrintWriter res;
	BufferedReader req;
	public ClientSocket(Socket socket) throws IOException{
		this.socket = socket;
		req = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
		res = new PrintWriter(this.socket.getOutputStream());
	}
	
	public void write(String msg){
		res.println(msg);
		res.flush();
	}
	
	public String read() throws IOException{
		return req.readLine();
	}
	
}


class Clients{
	private static Clients clients;
	private ArrayList<ClientSocket> connections;
	private HashMap<Integer,ClientSocket> users;
	private Clients(){
		this.connections = new ArrayList<ClientSocket>();
		this.users = new HashMap<Integer,ClientSocket>();
	}
	
	public static Clients getInstance(){
		if(clients == null)
            clients = new Clients();
        return clients;
	}
	
	public synchronized void addConnection(ClientSocket client){
		connections.add(client);
	}
	
	public synchronized void addUser(ClientSocket client,int id){
		users.put(id, client);
	}
	public synchronized ClientSocket getUser(int id){
		return users.get(id);
	}
	
	public ClientSocket getWork(){
		if(connections.isEmpty())
			return null;
		synchronized(this){
			return connections.remove(0);
		}
	}
	public void print(){
		String ids = "";
		for(ClientSocket client: connections)
			// String builder would be efficient here w/e
			ids += client.id + " ";
		System.out.println(ids);
	}
}


class CountSemaphore{
	  public int signals = 0;
	  public synchronized void produce() {
	    this.signals++;
	    this.notify();
	  }

	  public synchronized void consume() throws InterruptedException{
	    while(this.signals == 0) wait();
	    this.signals--;
	  }

}


