public class Move{
    String moveType;
    int discardIndex;
    String clueMsg ="";
    int playIndex;
    Player target;
    Cards card;
    
    public Move(){
    }
    
    public Move(String moveType)
    {
        this.moveType = moveType;
    }
    
    public void setDiscardIndex(int index)
    {
        discardIndex = index;
    }
    
    public void setPlayIndex(int index)
    {
        playIndex = index;
    }
    public void addCard(Cards card){
    	this.card = card;
    }
}