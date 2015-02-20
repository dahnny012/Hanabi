import java.util.*;
import java.util.concurrent.Semaphore;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.ServerSocket;
import java.io.*;



class Handlers{
	HashMap<Integer,List<ClientSocket>> rooms = new HashMap<Integer,List<ClientSocket>>();
	public void join(ClientSocket client,BufferedReader req,PrintWriter res,String[] args){
		try{
			int roomNum = Integer.parseInt(args[1]);
			List<ClientSocket> room = rooms.get(roomNum);
			client.roomNum = roomNum;
			room.add(client);
			System.out.println("Added player to room " + roomNum);
			res.println("JOINED " + roomNum);
			res.flush();
			String msg = "A player has joined";
			broadcast(client.roomNum,room,client,msg);
		}
		catch(Exception e){
			System.out.println(e);
			res.println("Error Can't Connect");
			res.flush();
		}
	}
	
	public void create(ClientSocket client,BufferedReader req,PrintWriter res){
		List<ClientSocket> sockets = new ArrayList<ClientSocket>();
		Random rando = new Random();
		int roomNum = rando.nextInt(10000);
		
		sockets.add(client);
		client.roomNum = roomNum;
		rooms.put(roomNum,sockets);
		System.out.println("Created " + roomNum);
		res.println("CREATE "+ Integer.toString(roomNum));
		res.flush();
	}
	
	public void message(ClientSocket client,BufferedReader req, PrintWriter res,String[] args) throws IOException
	{
		List<ClientSocket> room = rooms.get(client.roomNum);
		try{
		String msg = args[1];
		broadcast(client.roomNum,room,client,msg);
		}
		catch(Exception e){
			System.out.println(e);
			res.println("Error: Cant message");
			res.flush();
		}
	}
	
	private void broadcast(int roomNum,List<ClientSocket> room,ClientSocket client,String msg) throws IOException{
		System.out.println("Room size: " + room.size());
		for(int i=0; i<room.size(); i++){
			ClientSocket player = room.get(i);
			if(player.id != client.id){
				PrintWriter pipe = new PrintWriter(player.socket.getOutputStream());
				pipe.println(msg);
				pipe.flush();
			}
		}
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
		BufferedReader req = new BufferedReader(new InputStreamReader(client.socket.getInputStream()));
		PrintWriter res = new PrintWriter(client.socket.getOutputStream());
		try{
			if(req.ready()){
				request = req.readLine();
				args = request.split(" ");
				route = args[0];
				System.out.println("Route: " + route);
			}else{
				return;
			}
		}
		catch(IOException io){
			System.out.println("Error reading");
			System.out.println(io);
			return;
		}
		switch(route){
		case "join":
			System.out.println("Proceding to Join");
			handle.join(client,req,res,args);
			return;
		case "create":
			System.out.println("Proceding to Create");
			handle.create(client,req,res);
			return;
		case "msg":
			System.out.println("Proceding to Message");
			handle.message(client,req,res,args);
		default:
			return;
		}
	}
}

public class Server{
	ServerSocket server;
	public Server() throws IOException{
		server = new ServerSocket(8000);
	}
	
	public void start() throws IOException, InterruptedException{
		//Dispatch[] dispatchers = new Dispatch[3];
		//Worker[] workers = new Worker[5];
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
				clients.addConnection(task);
				sem.produce();
				//System.out.println("Semaphore: " + sem.signals);
				//clients.print();
			}

		}
		
	}	
}

class ClientSocket{
	Socket socket;
	public int id;
	public int roomNum;
	public ClientSocket(Socket socket){
		Random rando = new Random();
		id = rando.nextInt(10000);
		this.socket = socket;
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
	
	public void addConnection(ClientSocket client){
		connections.add(client);
	}
	
	public ClientSocket getWork(){
		if(connections.isEmpty())
			return null;
		return connections.remove(0);
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


