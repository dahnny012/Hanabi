public class Board{
    public Deck deck;
    public ArrayList<Player> Players;
    public int numPlayers;
    public int fireworksTokens = 3;
    public int timeTokens = = 8;
    public boolean gameInProgress = false;
    // maxScore tracks the maximum possible total with the remaining cards
	// it does NOT track the maximum possible total with the remaining turns
	// (eg. if two players, each with hands of five 1s, draw the last card
	// 	without anything on the board, maxScore = 5, not 2)
	// We could do it, but I think it's unnecessary work.
	
    public int score = 0;
    public int maxScore =25;
    public boardIndex;
    // currentTurn is an int within [0, n), where n = playerCount
	private int currPlayer = 0;
	
	
	
    public Board(int num,int boardIndex)
    {
        this.boardIndex = boardIndex;
        deck = new Deck();
        stacksMap = new HashMap();
        
    }
    
    public void addPlayer()
    {
        // probably add their network connection stuff later.
        if(gameInProgress != progress)
         Players.add(new Player());
    }
    
    public void startGame()
    {
        gameInProgress = true;
        dealCards();
    }
    
    public dealCards()
    {
        playerCount = Players.size();
        for (i = 0; i < playerCount; i++){
			for (j = 0; j < 5; j++){
				 Players.get(i).hand[i] = deck.draw();
			}
		}
    }
    
    
    
    
}