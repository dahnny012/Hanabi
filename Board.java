import java.util.ArrayList;

public class Board{
    public Deck deck;
	public ArrayList<Player> Players;
	public int numPlayers;
	public int fireworksTokens = 3;
	public int timeTokens = 8;
	public boolean gameInProgress = false;
	public int score = 0;
	public int maxScore = 25;
	public int boardIndex;
	private int currPlayer = 0;
	
	public Board(int numPlayers,int boardIndex){
		this.numPlayers = numPlayers;
		this.boardIndex = boardIndex;
		for(int i=0; i<numPlayers; i++)
			Players.add(new Player(boardIndex));
		gameInProgress= true;
		deck = new Deck();
	}
}