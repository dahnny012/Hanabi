import java.net.Socket;

public class GameManager{
	private int gmAddr;
	private Socket socket;
	public CardStack[] stacksMap;
	public int numPlayers;
	public Board board;
	public int boardIndex;
	public GameManager(int numPlayers ,int boardIndex,int gmAddr)
	{
		this.boardIndex = boardIndex;
		board = new Board(numPlayers,boardIndex);
		stacksMap = new CardStack[5];
		this.gmAddr = gmAddr;
		this.numPlayers = numPlayers;
		
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
