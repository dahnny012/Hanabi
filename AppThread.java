

public class AppThread implements Runnable{
    Thread t;
    String name;
    public AppThread(String name){
        this.name = name;
        System.out.println("Creating " + name);
    }
    
    public void run(){
        System.out.println("Thread running: " + name);
        if(name.equals("Server"))
        {
            // Create a server
            while(true)
            {
                
            }
        }
        else if(name.equals("GameManager")){
            
            // Create a Game manager
            while(true){
                
            }
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




