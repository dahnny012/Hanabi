import java.util.ArrayList;

public class CardStack{
    public ArrayList<Cards> Stack = new ArrayList<Cards>();
    
    public Cards getTopCard()
    {
        if(empty()){
            return null;
        }
        else{
            return Stack.get(Stack.size() - 1);
        }
    }
    public boolean placeCard(Cards card)
    {
        if(validPlace(card)){
            Stack.add(card);
            return true;
        }
        return false;
    }
    
    public void discardCard(Cards card){
    	Stack.add(card);
    }
    
    public boolean validPlace(Cards card)
    {
        if(empty() && card.getValue() == 1)
            return true;
        if((getTopCard().getValue() + 1) == card.getValue())
            return true;
        return false;
    }
    
    public boolean empty()
    {
        return Stack.size() == 0;
    }

}