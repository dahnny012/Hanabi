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
			}
		}
		
	}
	public Cards draw()
	{
		
	}
	public void shuffle()
	{
		if(deck == null){
			System.out.println("Null Deck");
			return;
		}
		/*
		To shuffle an array a of n elements (indices 0..n-1):
  for i from n − 1 downto 1 do
       j ← random integer with 0 ≤ j ≤ i
       exchange a[j] and a[i]*/
		
		
	}
	
}