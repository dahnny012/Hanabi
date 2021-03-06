import java.io.BufferedReader;
import java.net.Socket;
import java.util.ArrayList;

public class GameManager{
	private BufferedReader  server;
	private Socket socket;
	public int numPlayers;
	public Board board;
	public int boardIndex;
	public ArrayList<Player> players;
	public int currPlayer = 0;
	public GameManager(int numPlayers ,BufferedReader server,Board board)
	{
		this.server = server;
		this.numPlayers = numPlayers;	
	}
	
	
	public int playOneTurn()
	{
		// Print All Hands except current players
		// Print board variables
		// Print card stacks
		
		Move move = getCurrentPlayer().askForMove();
		// Do neccessary actions
			// If they play , or discard place it in the correct place.
			// If they hint relay msg.
		   switch(move.moveType){
		   case"play":
			   // See if you can add to stack
			   int colorIndex = move.hintColor.value;
			   if(!board.stacks[colorIndex].placeCard(move.card)){
				   // Add a fireworks token
				   // Add it to discard pile otherwise.
				   board.discard.discardCard(move.card);
			   }else{
				   // Increase score
				   // If completed stack gain time.
			   }
			   // Checking for empty deck needs to be implemented.
			   getCurrentPlayer().drawFromDeck(board.deck);
			   break;
		   case"discard":
			   // discard card
			   board.discard.discardCard(move.card);
			   // gain time
			   board.timeTokens++;
			   // draw card
			   getCurrentPlayer().drawFromDeck(board.deck);
			   break;
		   case"hint":
			   if(board.timeTokens <= 0)
				   return playOneTurn();
			   String msg = createHint(move);
			   // write it to console
			   System.out.println(msg);
			   break;
		   default:
			   // Something fked up
			   return -1;
		   }
		   currPlayer++;
		   if(currPlayer >= numPlayers)
			   currPlayer = 0;
		   return 1;
	}

	public void addPlayer()
	{
		// probably add their network connection stuff later.
		if(!board.gameInProgress)
			players.add(new Player(5));
	}

	public void startGame()
	{
		board.gameInProgress = true;
		dealCards();
		while(playOneTurn() == 1){};
	}

	public void dealCards()
	{	
		int playerCount = players.size();
		int handSize = 0;
		if (playerCount < 2 || playerCount > 5){
			//error out
		}else if (playerCount <= 3){
			handSize = 5;
		}else{
			handSize = 4;
		}
		for (int i = 0; i < playerCount; i++){
			players.get(i).maxHandsize = handSize;
			for (int j = 0; j < handSize; j++){
				 players.get(i).hand.set(i,board.deck.draw());
			}
		}
	}

	public boolean endGame()
	{
		if(board.fireworksTokens >= 3 || board.countDown == 0 || board.score == board.maxScore)
			return true;
		return false;
	}
	
	public Player getCurrentPlayer(){
		return players.get(currPlayer);
	}
	
	public void loadPlayers(){
		for(int i=0; i<numPlayers; i++)
			players.add(new Player(boardIndex));
	}
	
	public String createHint(Move move){
		if(move.hintColor  == null && move.hintValue == -1)
			return "";
		int handsize = players.get(move.targetPlayer).currentHandsize;
		Player targetPlayer = players.get(move.targetPlayer);
		if(move.hintColor != null){
			for(int i=0; i<handsize; i++)
			{
				Cards card = targetPlayer.hand.get(i);
				if(card.getColor() == move.hintColor)
					move.clueMsg += i + " ";
			}
			move.clueMsg += "Are the color " + move.hintColor;
		}
		else{
			for(int i=0; i<handsize; i++)
			{
				Cards card = targetPlayer.hand.get(i);
				if(card.getValue() == move.hintValue)
					move.clueMsg += i + " ";
			}
			move.clueMsg += "Are the numbers " + move.hintValue;
		}
		return move.clueMsg;
	}

}
