import java.util.ArrayList;
import java.util.*;
import java.net.Socket;
import java.net.ServerSocket;
import java.io.*;

public class Client{
	public static void main(String[] arg)
	{
		try{
		Client app = new Client();
		Socket client = new Socket("127.0.0.1",8000);
		
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		String input;
		//while((input = in.readLine()) != null && input.length() != 0)
		while(true);
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