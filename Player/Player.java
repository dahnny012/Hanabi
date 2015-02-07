// Most likely will need to import deck

public class Player{
	public Cards hand = new Cards[5];
	public Move move;
	public int maxHandSize =5;
	public int boardIndex;
	public View view;
	public Player(int boardIndex){
		this.boardIndex = boardIndex; 
	}
	
	
	public Move play(int index){
		if(!validHandIndex(index)) return null;
		move = new Move("Play");
		move.setPlayIndex(index);
		return move;
	}
	
	public Move discard(int index){
		if(!validHandIndex(index)) return null;
		move = new Move("Discard");
		move.setDiscardIndex(index);
		return move;
	}
	
	public Move hint(Cards.Color color,int value,Player player){
		move = new Move("Hint");
		if(color  == null && value == null)
			return null;
		
		if(color != null){
			for(int i=0; i<maxHandSize; i++)
			{
				Cards card = player.hand[i];
				if(card.color == color)
					move.clueMsg += i + " ";
			}
			move.clueMsg += "Are the color " + color;
		}
		else{
			for(int i=0; i<maxHandSize; i++)
			{
				Cards card = player.hand[i];
				if(value == value)
					move.clueMsg += i + " ";
			}
			move.clueMsg += "Are the numbers " + value
		}
		
		return move;
	}
	
	public validHandIndex(int index)
	{
		return index >= 0 && index < maxHandSize;
	}
	
	
}
