public class CardStack{
    public ArrayList<Cards> Stack;
    
    public Card getTopCard()
    {
        if(empty()){
            return null;
        }
        else{
            return Stack.get(Stack.size() - 1);
        }
    }
    
    public void placeCard(Cards card)
    {
        if(validPlace(card)
            Stack.add(card);
    }
    
    public boolean validPlace(Cards card)
    {
        if(empty() && card.value == 1)
            return true;
        if((getTopCard.value - 1) == card.value)
            return true;
        return false;
    }
    
    public boolean empty()
    {
        return Stack.size() == 0;
    }

}