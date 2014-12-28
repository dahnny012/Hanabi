import java.util.ArrayList;
import java.util.*;
import java.net.Socket;
import java.net.ServerSocket;
import java.io.*;

public class Server{
	public static void main(String[] arg)
	{
		try{
		Server app = new Server();
		// Server fds
		ServerSocket server = new ServerSocket(8000);
		// client fds 
		Socket client;
		while(true){
			client = server.accept();
			PrintWriter response = new PrintWriter(client.getOutputStream());
			BufferReader request = new BufferedReader(client.getInputStream());
		}
		}
		catch(IOException e){
			System.out.println("Error connecting");
		}
		return;
		
	}
	
	public void log(String arg)
	{
		System.out.println(arg);
	}
}