public class Cards
{
	private int value;
	private Color color;
	  
	
	public static Cards makeCard(int value,Color color){
		if(validCard(value,color))
			return new Cards(value,color);
		return null;
	}
	private Cards(int value,Color color)
	{
		this.value = value;
		this.color = color;
	}
	public Cards()
	{
		
	}
	
	public static boolean validCard(int value,Color color)
	{
		return color.value>= Color.BLUE.value && 
			color.value <= Color.YELLOW.value &&
			value >= 1 &&
			value <= 5;
	}
	public int getValue()
	{
		return this.value;
	}
	public Color getColor(){
		return this.color;
	}
	
}