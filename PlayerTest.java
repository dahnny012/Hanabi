public class PlayerTest{
	public static void main(String[] args){
		
	}
	
	public void test_createPlayer(){
		Player test = new Player(1);
		if(test.boardIndex != 1)
			System.out.println("Error setting board index");
	}
	
	public void test_drawFromDeck(){
		Player test = new Player(1);
		Deck deck = new Deck();
		for(int i=0; i<test.maxHandsize; i++)
		{
			test.drawFromDeck(deck);
		}
		if(test.currentHandsize != 5)
			System.out.println("Error made hand size: "+test.currentHandsize);
		
		Player test2 = new Player(2);
		Deck deck2 = new Deck();
		for(int i=0; i<test.maxHandsize+1; i++)
		{
			test2.drawFromDeck(deck2);
		}
		if(test.currentHandsize > 5)
			System.out.println("Error made hand size: "+(test.currentHandsize+1));
	}
	
	public void test_movePlay(){
		Player test = new Player(1);
		Deck deck = new Deck();
		for(int i=0; i<test.maxHandsize; i++)
		{
			test.drawFromDeck(deck);
		}
		if(test.currentHandsize != 5)
			System.out.println("Error made hand size: "+test.currentHandsize);
		if(test.play(5) != null)
			System.out.println("Invalid play index: "+5);
		Cards peek = test.hand.get(4);
		Move move=  test.play(4);
		if(move == null)
			System.out.println("Could play index: "+4);
		if(test.currentHandsize != 4)
			System.out.println("Handsize did not go down: "+4);
		if(peek.getValue() != move.card.getValue())
			System.out.println("Played card does not match peeked card");
	}
	
	public void test_moveDiscard(){
		
	}
	
	public void test_moveHint(){
		
	}
}