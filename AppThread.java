import java.util.ArrayList;



public class AppThread implements Runnable{
    Thread t;
    String name;
    // Globals dunno how else to do shared memory.
    //ArrayList<Socket> boardSocket;
    ArrayList<Board> boards;
    public AppThread(String name,ArrayList<Board> boards){
        this.name = name;
        System.out.println("Creating " + name);
        this.boards = boards;
    }
    pub
    
    public void run(){
        System.out.println("Thread running: " + name);
        if(name.equals("Server"))
        {
            Server server = new Server(boardSocket,boards);
            while(true){
                Socket newClient = server.acceptConnection();
                if(newClient != null)
                    switch(newClient.req){
				    case "join":
				        if(// req for room)
				    	    showBoards();
				    	// If they input join 42342 we can assume they had an invite.
				    	else{
				    	    connectToBoard(someID);
				    	}
				    case "create":
				    	createBoard()
				    	writeToClient(boardID);
				    default:
				    	// close for now.
                    }   
            }
        }
        else if(name.equals("GameManager")){
            GameManager gm = new GameManager(3,0,1);
            // Check board for new moves
                // See what they want to do and write the changes to
                // to the other players.
        }
    }
    
    public void start(){
        if(t == null){
            System.out.println("Starting " + name);
            t = new Thread(this,name);
            t.start();
        }
    }
    
    public void showBoards(){}
    public void connectToBoard(int id){}
    public void createBoard(){}
    public void writeToClient()
}




