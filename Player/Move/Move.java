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
    
    public setDiscardIndex(int index)
    {
        discardIndex = index;
    }
    
    public setPlayIndex(int index)
    {
        playIndex = index;
    }
}