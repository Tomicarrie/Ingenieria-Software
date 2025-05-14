package uno;

public class ColoredWildCard extends ColoredCard {
    public String type = "Colored Wild";
    public ColoredWildCard(String color) {
        super();
        this.color = color;
        
    }

    public boolean accepts(Card aCard) {
        return aCard.colorIsValid(this);
    }


    public boolean colorIsValid(ColoredCard aCard) {
        return color.equals(aCard.color);
    }
}
