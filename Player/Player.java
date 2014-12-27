// Most likely will need to import deck

public class Player{
	private Hand hand[5];
	
	public Cards play(int index){
		// check valid hand index
		// store in temp, then from hand[index]
		// draw card replace in hand[index];
		// return temp
	}
	public Cards discard(int index){
		// Discard card in index.
		// Draw from deck and put in card index
	}
	public String tell(Cards.Color color,int value,Player player)
	{
		// Color or value will be Null/0.
		// Tell player some message about his hand.
	}
	
}