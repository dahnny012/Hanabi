public class Cards
{
	private int value;
	private Color color
	public enum Color {
        BLUE(0), GREEN(1), RED(2), WHITE(3),YELLOW(4)
        private int value;
		

        private Color(int value) {
                this.value = value;
        }
	}   
	
	public Cards(value,color)
	{
		if(validCard){
			this.value = value;
			this.color = color;
		}
		else{
			return NULL;
		}
	}
	public Cards()
	{
		
	}
	
	public validCard(value,color)
	{
		return color>= BLUE && 
			color <= YELLOW &&
			value >= 1 &&
			value <= 5;
	}
	
}