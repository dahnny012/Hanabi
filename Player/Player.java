// Most likely will need to import deck

public class Player{
	private Cards hand[5];
	public Move move;
	
	
	
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
	public Move hint(Cards.Color color,int value,Player player)
	{
		move = new Move("Hint");
		
	}
	
	public validHandIndex(int index)
	{
		return index >= 0 && index < 5;
	}
	
}