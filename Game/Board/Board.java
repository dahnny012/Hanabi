public class Board extends Game{
	private int gmAddr;
	private Socket gameManager;
	
	public Board(int num,int boardIndex,int gmAddr)
	{
		this.boardIndex = boardIndex;
		deck = new Deck();
		stacksMap = new HashMap();
		this.gmAddr = gmAddr;
		//open a socket to gameManager and keep it.
	}
	
	public Move playOneTurn()
	{
		askForMove(Players.get(currPlayer));
		// Do neccessary actions
		// alert game manager to change state of other players.
	}
	
	public Move askForMove(Player current)
	{
	   // Get move from player
	   return null;
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
		playOneTurn();
	}

	public dealCards()
	{
		playerCount = Players.size();
		int handSize;
		if (playerCount < 2 || playerCount > 5){
			//error out
		}else if (playerCount <= 3){
			handSize = 5;
		}else{
			handSize = 4;
		}
		for (i = 0; i < playerCount; i++){
			Players.get(i).maxHandSize = handSize;
			for (j = 0; j < handSize; j++){
				 Players.get(i).hand[i] = deck.draw();
			}
		}
	}

	public boolean endGame()
	{
		if(fireworksTokens >= 3 || countdown == 0 || score == maxScore)
			return true;
		return false;
	}

}
