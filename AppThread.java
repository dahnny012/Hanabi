

public class AppThread implements Runnable{
    Thread t;
    String name;
    // Globals dunno how else to do shared memory.
    static ArrayList<Socket> boardSocket;
    static ArrayList<Board> board
    public AppThread(String name){
        this.name = name;
        System.out.println("Creating " + name);
    }
    
    public void run(){
        System.out.println("Thread running: " + name);
        if(name.equals("Server"))
        {
            Server server = new Server();
            // listen for requests
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




