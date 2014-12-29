public class GameManager{
	
	// maxScore tracks the maximum possible total with the remaining cards
	// it does NOT track the maximum possible total with the remaining turns
	// (eg. if two players, each with hands of five 1s, draw the last card
	// 	without anything on the board, maxScore = 5, not 2)
	// We could do it, but I think it's unnecessary work.
	private int maxScore = 25;
	
	// We "should" make this a member of Deck
	private int cardsLeft = 50;
	
	private int score = 0;
	private int fireworksTokens = 0;
	private int timeTokens = 8;
	
	// currentTurn is an int within [0, n), where n = playerCount
	private int currPlayer = 0;
	private int playerCount;

	// board keeps track of the current top number of each of the 5 stacks
	int[] board	= new int[5];
	Cards[][] hands = new Cards[playerCount][5];
	Deck gameDeck = new Deck();
	
	// countdown is a turn counter for the additional turns given
	// after the deck is emptied. We use -1 to represent not-in-use
	// and an int within [0, n] where n = playerCount when in use
	private int countdown = -1;
	
	public static void main(String[] args){
		
		//!! Magically know how many players we need from args
		playerCount = NEEDAVALUE;
		
		// We could make players objects, each with their own hand
		// For now, I'm working with a 2D array of cards
		// and identifying players with ints
		
		// Deal out a hand of five cards to each player
		for (i = 0; i < playerCount; i++){
			for (j = 0; j < 5; j++){
				hands[i][j] = gameDeck.draw();
				cardsLeft --;
			}
		}
		
		// While the game isn't over, play a turn and advance currPlayer
		while (endGame == false){
			playOneTurn();
			currPlayer = (currPlayer + 1) % playerCount;
		}
		
		//!! GAME OVER
	}
	
	public void playOneTurn(){
		//!! Choose 1 of the 2-3 options (If there is no time, do not allow hints)
		//!! Based on the choice, call additional helper functions
	}
	
	public void giveHint(){
		//!! Choose a player to tell
		//!! Choose a value or color to inform on
		//!! Only allow non-zero hints (eg. No "You have no 5s")
		timeTokens --;
	}
	
	// type is an int [0, 1]
		// 0 represents Playing
		// 1 represents Discarding
	// It could be a boolean or enum or a b-tree vector if you prefer
	public void pickCard(int type){
		//!! Pick a card to play/discard
		
		int picked = NEEDAVALUE;
		Cards pickedCard = hands[currPlayer][picked];
		
		// If type is 0, check if the card can be played.
		// 	If it can be, play it.
		//	If it can't, discard it and increment fireworksTokens
		// If type is 1, discard it.
		if (type == 0){
			if (isValidCard(pickedCard)){
				playCard(pickedCard);
			}else{
				discard(pickedCard);
				fireworksTokens ++;
			}
		}else if (type == 1){
			discard(pickedCard);
		}
		
		// If there are cards left in the deck, draw a card
		// Then, if that was the last card, begin the countdown
		if (cardsLeft != 0){
			hands[currPlayer][picked] = gameDeck.draw();
			cardsLeft --;
			if (cardsLeft == 0){
				countdown = playerCount;
			}
		}
	}
	
	public void playCard(Cards pickedCard){
		board[pickedCard.color] ++;
		if ((pickedCard.value == 5) && (timeTokens < 8)){
			timeTokens ++;
		}
		score ++;
	}
	
	public void discard(Cards pickedCard){
		//!! Check if all of these cards have been discarded
		
		// If they have all been discarded, we need to reduce maxScore accordingly
		if (NEEDAVALUE){
			maxScore -= (6 - pickedCard.value);
		}
	}
	
	// Check for 3 fireworks tokens
	// Check for maximum score (eg. If a 5 is discarded, we should end the game if the score reaches 24)
	// Check if there are 0 cards in the deck and each player has gotten their last turn
	// Return true if any of the above conditions are true; otherwise false
	public boolean endGame(){
		if (fireworksTokens == 3){
			return true;
		}else if (score == maxScore){
			return true;
		}else if (countdown == 0){
			return true;
		}else{
			return false;
		}
	}
	
	public int getScore(){
		return score;
	}
	
	public int getFireworksTokens(){
		return fireworksTokens;	
	}
	
	public int getTimeTokens(){
		return timeTokens;
	}
	
}
