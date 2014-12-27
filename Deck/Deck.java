public class Deck{
	private ArrayList<Cards> deck;
	private static Deck()
	{
		Cards card = new Cards();
		int valStart = 0;
		int valEnd = 4;
		
		//Generate a Deck
		for(valStart; valStart<valEnd; valStart++)
		{
			foreach(card.Color color in Enum.GetValues(typeof(card.Color)))
			{
				Cards gameCard = new Cards(valStart,color);
				if(gameCard == null)
				{
					System.out.println("New card in Deck failed " + valStart + " " + color);
				}
				deck.add(gameCard);
			}
		}
		
	}
	public Cards draw()
	{
		if(deck.size() == 0)
		{
			return null;
		}
		return deck.remove(0);
	}
	public void shuffle()
	{
		if(deck == null){
			System.out.println("Null Deck");
			return;
		}
		int size = deck.size()
		for(int i=0; i<size; i++)
		{
			j = rand(0,size-1);	
			swap(i,j);
		}
	}
	private void swap(int i,int j)
	{
		Cards temp = deck.get(i);
		deck.set(i,deck.get(j));
		deck.set(j,temp);
	}
	
	private static int rando(int min,int max)
	{
		Random rand = new Random();
		int randomNum = rand.nextInt((max - min) + 1) + min;
		return randomNum;

	}
}