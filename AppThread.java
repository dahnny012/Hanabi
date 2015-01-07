

public class AppThread implements Runnable{
    Thread t;
    String name;
    // Globals dunno how else to do shared memory.
    ArrayList<Socket> boardSocket;
    ArrayList<Board> boards
    public AppThread(String name,ArrayList<Board> boards){
        this.name = name;
        System.out.println("Creating " + name);
        this.boards = board;
    }
    
    public void run(){
        System.out.println("Thread running: " + name);
        if(name.equals("Server"))
        {
            Server server = new Server();
            while(true){
                Socket newClient = server.accept();
                if(newClient != null)
                    // If connecting to game
                        // add to list of sockets at board I.
                    // If hosting game 
                        // create board , add to list of sockets.
            }
        }
        else if(name.equals("GameManager")){
            
            GameManager gm = new GameManager();
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
}




