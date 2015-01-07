
public class App{
	static ArrayList<Board> boards;
	public static void main(String[] args){
		
		App app = new App();
		AppThread server = new AppThread("Server",boards);
		AppThread gameManager = new AppThread("GameManager",boards);
		
		server.start();
		gameManager.start();
		sleep(0);
	}
	
	
	
	
	
	
	
	
}
