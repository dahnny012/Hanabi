public class Deck{
	private ArrayList<Cards> deck;
	public Deck()
	{
		int valStart = 1;
		int valEnd = 5;
		int numOnes = 3;
		int numDefault = 2;
		int numFives = 1;
		
		for(; valStart<=valEnd; valStart++)
		{
			for(Color color: Enum.GetValues(typeof(Color)))
			{
				switch(valStart){
					case 1:
						addCards(valStart,color,numOnes);
					case 5:
						addCards(valStart,color,numFives);
					default:
						addCards(valStart,color,numDefault);
				}
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
		int size = deck.size();
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
	
	private void addCards(int value , Card.Color color , int numCards)
	{
		for (int i =0; i<numCards; i++)
		{
			Cards gameCard = new Cards(value,color);
			if(gameCard == null)
			{
				System.out.println("New card in Deck failed " + valStart + " " + color);
			}
			deck.add(gameCard);
		}
	}
	
	public int cardsLeft()
	{
		return deck.size();
	}
}