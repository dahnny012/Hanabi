import java.util.*;
import java.net.Socket;
import java.net.ServerSocket;
import java.io.*;

import org.json.simple.JSONObject;



class Handlers{
	HashMap<Integer,List<ClientSocket>> rooms = new HashMap<Integer,List<ClientSocket>>();
	public void join(ClientSocket client,String[] args){
		try{
			int roomNum = Integer.parseInt(args[1]);
			List<ClientSocket> room = rooms.get(roomNum);
			client.roomNum = roomNum;
			String msg;
			
			room.add(client);
			System.out.println("Added player to room " + roomNum);
			//client.write("JOINED " + roomNum);
			
			JSONObject joinMsg=new JSONObject();
			StringWriter out = new StringWriter();
			joinMsg.put("Event","Join");
			joinMsg.put("roomId",roomNum);
			joinMsg.writeJSONString(out);
			msg = joinMsg.toString();
			
			client.write(msg);
			broadcast(client.roomNum,room,client,msg);
		}
		catch(Exception e){
			System.out.println(e);
			writeError(client,"Cant connect");
		}
	}
	
	public void create(ClientSocket client) throws IOException{
		List<ClientSocket> sockets = new ArrayList<ClientSocket>();
		Random rando = new Random();
		int roomNum = rando.nextInt(10000);
		
		sockets.add(client);
		client.roomNum = roomNum;
		rooms.put(roomNum,sockets);
		System.out.println("Created " + roomNum);
		//client.write("CREATE "+ Integer.toString(roomNum));
		
		JSONObject obj=new JSONObject();
		StringWriter out = new StringWriter();
		obj.put("Event","Create");
		obj.put("roomId",roomNum);
		obj.writeJSONString(out);
		client.write(obj.toString());
	}
	
	public void message(ClientSocket client,String[] args) throws IOException
	{
		List<ClientSocket> room = rooms.get(client.roomNum);
		try{
		String msg = args[1];
		
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
			List<ClientSocket>room = rooms.get(client.roomNum);
			
			JSONObject obj=new JSONObject();
			StringWriter out = new StringWriter();
			obj.put("Event","Leave");
			obj.put("msg","A Player has left");
			obj.writeJSONString(out);
			String eventMsg = obj.toString();
			
			for(int i=0; i<room.size(); i++){
				ClientSocket player = room.get(i);
				if(player.id == client.id){
					broadcast(client.roomNum, room, client,eventMsg);
					room.remove(i);
				} 
			}
			return;
		}
	}
	
	public void move(ClientSocket client,String[] args){
		// Broadcast move to all players
		List<ClientSocket> room = rooms.get(client.roomNum);
		try{
		String msg = args[1];
		
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
	
	private void broadcast(int roomNum,List<ClientSocket> room,ClientSocket client,String msg) throws IOException{
		System.out.println("Room size: " + room.size());
		for(int i=0; i<room.size(); i++){
			ClientSocket player = room.get(i);
			if(player.id != client.id){
				player.write(msg);
			}
		}
	}
	
	private void writeError(ClientSocket client,String msg){
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
	
	public boolean handle(ClientSocket client) throws IOException{
		String route;
		String[] args;
		String request;
		try{
			if(client.req.ready()){
				request = client.read();
				args = request.split(" ");
				route = args[0];
				System.out.println("Route: " + route);
			}else{
				return true;
			}
		}
		catch(IOException io){
			System.out.println("Error reading");
			System.out.println(io);
			return true;
		}
		switch(route){
		case "join":
			System.out.println("Proceding to Join");
			handle.join(client,args);
			return true;
		case "create":
			System.out.println("Proceding to Create");
			handle.create(client);
			return true;
		case "msg":
			System.out.println("Proceding to Message");
			handle.message(client,args);
			return true;
		case "leave":
			System.out.println("Proceding to leave");
			handle.leave(client);
			return false;
		case "move":
			System.out.println("Proceding to leave");
			handle.move(client,args);
			return true;
		case "json":
			handle.json(client);
		default:
			return true;
		}
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
			boolean reenter = true;
			if(task != null){
				Router route = Router.getInstance();
				try {
					reenter = route.handle(task);
				} catch (IOException e) {
					e.printStackTrace();
				}
				if(reenter){
					clients.addConnection(task);
					sem.produce();
				}
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
		Random rando = new Random();
		id = rando.nextInt(10000);
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
	private Clients(){
		this.connections = new ArrayList<ClientSocket>();
	}
	
	public static Clients getInstance(){
		if(clients == null)
            clients = new Clients();
        return clients;
	}
	
	public synchronized void addConnection(ClientSocket client){
		connections.add(client);
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


