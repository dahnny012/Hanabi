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
	public 
}

public class Server{
	Socket server;
	
	public Server(){
		server = new ServerSocket(8000);
	}
	public Socket accept()
	{
		try{
		//Server app = new Server();
		
		// client fd 
		Socket client;
			
	
		while(true){
			// Setup the Request and Response stream.
			client = accept();
			PrintWriter resStream = new PrintWriter(client.getOutputStream());
			BufferedReader reqStream = new BufferedReader(new InputStreamReader(client.getInputStream()));
			String msg;
			
			// Keep printing lines till "quit"/
			/*
			while(true)
			{
				req = reqStream.readLine();
				if(req != null)
				{
					if(req.equals("quit"))
					{
						return;
					}
					log(req);
				}

			}*/
		}
		}
		catch(IOException e){
			System.out.println("Error with server");
		}
		return;
		
	}
	
	public void log(String arg)
	{
		System.out.println(arg);
	}
}