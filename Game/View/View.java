public class View extends Game{
    
    public playerView; 
    public View(int num,int boardIndex,int playerView)
    {
        this.playerView = playerView;
        this.numPlayers = num;
        this.boardIndex = boardIndex;
    }
}