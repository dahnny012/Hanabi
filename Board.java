import java.util.ArrayList;

public class Board{
    public Deck deck;
	public int numPlayers;
	public int fireworksTokens = 3;
	public int timeTokens = 8;
	public boolean gameInProgress = false;
	public int score = 0;
	public int maxScore = 25;
	public int countDown;
	public CardStack[] stacks; 
	public CardStack discard;
	
	public Board(int numPlayers){
		this.numPlayers = numPlayers;
		deck = new Deck();
		countDown = deck.cardsLeft();
		stacks = new CardStack[5];
	}

}