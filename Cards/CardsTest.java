public class CardsTest
{
	public static void main(String[] args){
		CardsTest app = new CardsTest();
		app.test_createCard();
		
	}
	public void test_createCard()
	{
		Cards bad = Cards.makeCard(121,Color.BLUE);
		if(bad != null){
			System.out.println("Not Null card");
			return;
		}
		Cards valid = Cards.makeCard(1,Color.RED);
		if(valid == null){
			System.out.println("Null card");
			return;
		}
		System.out.println("Test passed");
	}
	
}