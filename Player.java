import java.util.ArrayList;
import java.util.Scanner;

public class Player{
	public ArrayList<Cards> hand = new ArrayList<Cards>();
	public int currentHandsize = 0;
	public Move move;
	public int maxHandsize =5;
	public int boardIndex;
	public View view;
	public Scanner scan;
	public Player(int boardIndex){
		this.boardIndex = boardIndex; 
	}
	
	public void drawFromDeck(Deck deck){
		if(hand.size() >= maxHandsize)
			return;
		hand.add(deck.draw());
		currentHandsize++;
	}
	
	public Move play(int index){
		if(!validHandIndex(index)) return null;
		move = new Move("Play");
		move.targetHandIndex = index;
		move.addCard(hand.remove(index));
		currentHandsize--;
		// Manager will call draw from deck
		return move;
	}
	
	public Move discard(int index){
		if(!validHandIndex(index)) return null;
		move = new Move("Discard");
		move.targetHandIndex = index;
		move.addCard(hand.remove(index));
		currentHandsize--;
		// Manager will call draw from deck
		return move;
	}
	
	public Move hintTo(Color color,int value,int playerIndex){
		move = new Move("Hint");
		move.hintColor = color;
		move.hintValue = value;
		move.targetPlayer = playerIndex;
		// Dunno about this check
		return move;
	}
	
	public boolean validHandIndex(int index)
	{
		return index >= 0 && index < currentHandsize;
	}
	public void printHand(){
		for(int i=0; i<currentHandsize; i++)
		{
			Cards card = hand.get(i);
			System.out.println("Card: "+card.getValue() +" " + card.getColor());
		}
	}
	
	public Move askForMove(){
		// Load Scanner 
		String num = scan.nextLine();
		String[]args = num.split(" ");
		// Give menu options
		System.out.println("Options to play,discard,hint %i(hand[i] or player[i]) [Color %i Number %i]");
		try{
			int index = Integer.parseInt(args[1]);
            switch(args[0]){
            case "play":
            	return play(index);
            case "discard":
            	return discard(index);
            case "hint":
            	// Temporary
            	return hintTo(Color.BLUE,1,1);
            default:
            	return askForMove();
            }
        }
        catch(NumberFormatException e){
        	return askForMove();
        }
		// Based on User input call Discard,Play,Hint
	}
}
