import java.util.ArrayList;

public class App{
	static ArrayList<Board> boards = new ArrayList<Board>();
	public static void main(String[] args) throws InterruptedException{
		
		
		App app = new App();
		//AppThread server = new AppThread("Server",boards);
		AppThread gameManager = new AppThread("GameManager",boards);
		//server.start();
		gameManager.start();
		Thread.sleep(0);
	}
	
	
	
	
	
	
	
	
}
