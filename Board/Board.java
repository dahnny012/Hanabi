public class Board{
    public Deck deck;
    public ArrayList<Player> Players;
    public int fireworksTokens;
    public int timeTokens;
    
    public Board(int numPlayers)
    {
        for(int i=0; i<numPlayers; i++)
        {
            Players.add(new Player());
        }
        
        deck = new Deck();
        
        fireworksTokens = 3;
        timeTokens = 8;
    }
    
    
}