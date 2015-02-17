import java.util.*;
import java.util.concurrent.Semaphore;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.ServerSocket;
import java.io.*;



class Handlers{
	HashMap<Integer,List<ClientSocket>> rooms = new HashMap<Integer,List<ClientSocket>>();
	public void join(ClientSocket client,BufferedReader req,PrintWriter res){
		try{
			int roomNum = Integer.parseInt(req.readLine());
			List<ClientSocket> room = rooms.get(roomNum);
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
	
	public void create(ClientSocket client,BufferedReader req,PrintWriter res){
		List<ClientSocket> sockets = new ArrayList<ClientSocket>();
		Random rando = new Random();
		int roomNumber = rando.nextInt(10000);
		
		sockets.add(client);
		rooms.put(roomNumber,sockets);
		System.out.println("Created " + roomNumber);
		res.println("CREATE "+ Integer.toString(roomNumber));
		res.flush();
	}
	
	public void message(ClientSocket client,BufferedReader req, PrintWriter res) throws IOException
	{
		List<ClientSocket> room = rooms.get(client.roomNumber);
		String msg= req.readLine();
		for(ClientSocket player : room){
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
		BufferedReader req = new BufferedReader(new InputStreamReader(client.socket.getInputStream()));
		PrintWriter res = new PrintWriter(client.socket.getOutputStream());
		try{
			if(req.ready()){
				route = req.readLine();
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
			handle.join(client,req,res);
			return;
		case "create":
			System.out.println("Proceding to Create");
			handle.create(client,req,res);
			return;
		case "msg":
			System.out.println("Proceding to Message");
			handle.message(client,req,res);
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
			}

		}
		
	}	
}

class ClientSocket{
	Socket socket;
	public int id;
	public int roomNumber;
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
	  private int signals = 0;
	  public synchronized void produce() {
	    this.signals++;
	    this.notify();
	  }

	  public synchronized void consume() throws InterruptedException{
	    while(this.signals == 0) wait();
	    this.signals--;
	  }

}


