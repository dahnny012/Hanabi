public class Move{
    String moveType;
    String clueMsg ="";
    int targetHandIndex;
    int targetPlayer;
    int hintValue;
    Color hintColor;
    Cards card;
    
    public Move(){
    }
    
    public Move(String moveType)
    {
        this.moveType = moveType;
    }
    
    public void addCard(Cards card){
    	card = card;
    }
}