package uno;
public class WildCard extends Card {
    public String type = "Wild";
    
    public void setColor(String color) {
        this.color = color;
    }
    
    public boolean isValid(Card aCard) {
        return colorIsValid(aCard);
    }
}
