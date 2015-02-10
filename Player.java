import java.util.ArrayList;

public class Player{
	public ArrayList<Cards> hand = new ArrayList<Cards>();
	public int currentHandsize = 0;
	public Move move;
	public int maxHandsize =5;
	public int boardIndex;
	public View view;
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
		move.setPlayIndex(index);
		move.addCard(hand.remove(index));
		currentHandsize--;
		// Manager will call draw from deck
		return move;
	}
	
	public Move discard(int index){
		if(!validHandIndex(index)) return null;
		move = new Move("Discard");
		move.setDiscardIndex(index);
		move.addCard(hand.remove(index));
		currentHandsize--;
		// Manager will call draw from deck
		return move;
	}
	
	public Move hintTo(Color color,int value,Player player){
		move = new Move("Hint");
		// Dunno about this check
		if(color  == null && value == -1)
			return null;
		
		if(color != null){
			for(int i=0; i<maxHandsize; i++)
			{
				Cards card = player.hand.get(i);
				if(card.getColor() == color)
					move.clueMsg += i + " ";
			}
			move.clueMsg += "Are the color " + color;
		}
		else{
			for(int i=0; i<maxHandsize; i++)
			{
				Cards card = player.hand.get(i);
				if(card.getValue() == value)
					move.clueMsg += i + " ";
			}
			move.clueMsg += "Are the numbers " + value;
		}
		
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
}
