
public class App{
	
//	private ArrayList<Board> boards;
	public static void main(String[] args){
		
		App app = new App();
		AppThread server = new AppThread("Server");
		AppThread gameManager = new AppThread("GameManager");
		
		server.start();
		gameManager.start();
		sleep(0);
	}
	
	
	
	
	
	
	
	
}
