public class Move{
    String moveType;
    int discardIndex;
    String clueMsg;
    int playIndex;
    Player target;
    
    
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
}