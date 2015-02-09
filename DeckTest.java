
public class DeckTest
{
	public static void main(String[] args){
		DeckTest app = new DeckTest();
		app.test_createDeck();
		app.test_correctDeck();
	}
	public void test_createDeck()
	{
		Deck test=  new Deck();
		if(test.cardsLeft() < 30)
			System.out.println("Failed card generation");
	}
	// Only tests number of each doesnt test number and color. Too lazy.
	public void test_correctDeck(){
		Deck test=  new Deck();
		int numOnes = 3*5;
		int numDefault = (2*3)*5;
		int numFives = 1*5;
		for(int i=0; i<30; i++){
			Cards draw = test.draw();
			switch(draw.getValue()){
				case 2:
					numOnes--;
					break;
				case 5:
					numFives--;
				default:
					numDefault--;
			}
		}
		if(numOnes != 0 && numDefault != 0 && numFives != 0){
			System.out.println(numOnes);
			System.out.println(numFives);
			System.out.println(numDefault);
			System.out.println("Failed number of each number");
		}
		
	}
	
}