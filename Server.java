import java.util.ArrayList;
import java.util.*;
import java.net.Socket;
import java.net.ServerSocket;
import java.io.*;


/*Todo
* Setup for multiple threads well need this for each connected player. 
* Write back to players.
*/

class clientSocket extends Socket{
	public String req; // Join # or Create.
	Socket socket
	public void setReq(String req){
		this.req = req;
	}
	
}

public class Server{
	Socket server;
	ArrayList<Socket> boardSocket;
    ArrayList<Board> boards
	public Server(ArrayList<Socket> boardSocket,ArrayList<Board> boards){
		this.boardSocket = boardSocket;
		this.boards = boards;
		server = new ServerSocket(8000);
	}
	public Socket acceptConnection()
	{
		try{
		//Server app = new Server();
		
		// client fd 
		clientSocket client;
			// Setup the Request and Response stream.
			client = accept();
			PrintWriter resStream = new PrintWriter(client.getOutputStream());
			BufferedReader reqStream = new BufferedReader(new InputStreamReader(client.getInputStream()));
			String req;
				req = reqStream.readLine();
				req.toLowerCase();
				client.setReq(req);
				return client;
			
		}
		}
		catch(IOException e){
			System.out.println("Error with server");
		}
		return null;
		
	}
	
	public void log(String arg)
	{
		System.out.println(arg);
	}
}