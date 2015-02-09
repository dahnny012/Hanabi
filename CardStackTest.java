import java.util.ArrayList;


public class CardStackTest{
	public static void main(String args[]){
		CardStackTest app = new CardStackTest();
		app.test_peekAtTopCard();
		app.test_addToStack();
	}

	public void test_peekAtTopCard(){
		CardStack test = new CardStack();
		boolean status = test.placeCard(Cards.makeCard(1, Color.BLUE));
		if(!status)
			System.out.println("Unable to add 1 Blue");
		if(test.getTopCard().getValue() != 1)
			System.out.println("Top isnt 1");
		test.placeCard(Cards.makeCard(2, Color.BLUE));
		if(test.getTopCard().getValue() != 2)
			System.out.println("Top isnt 2");
	}
	public void test_addToStack(){
		
		// Basic add and unable to add.
		CardStack test = new CardStack();
		boolean status = test.placeCard(Cards.makeCard(1, Color.BLUE));
		if(!status)
			System.out.println("Unable to add 1 Blue");
		status = test.placeCard(Cards.makeCard(2,Color.BLUE));
		if(!status)
			System.out.println("Unable to add 2 Blue");
		status = test.placeCard(Cards.makeCard(5,Color.BLUE));
		if(status)
			System.out.println("Able to add 5 Blue ontop 2");
		
		// Fill a stack
		boolean status2;
		CardStack test2 = new CardStack();
		for(int i=1; i<=5; i++){
			status= test2.placeCard(Cards.makeCard(i,Color.BLUE));
			if(!status)
				System.out.println("Failed to stack "+i + "on top of " + (i-1));
		}
	}
}