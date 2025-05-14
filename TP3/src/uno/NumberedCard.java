package uno;
public class NumberedCard extends ColoredCard {
    private int number;

    public NumberedCard(String color, int number) {
        super();
        this.color = color;
        this.number = number;
    }
    public int getNumber() {
        return number;
    }
    public boolean accepts(Card aCard) {
        return aCard.acceptsColor(this) || aCard.acceptsNumber(this);
    }

    public boolean acceptsSymbol(SymbolicCard aCard) {return false;}
    public boolean acceptsNumber(NumberedCard aCard) { return number == aCard.getNumber();}

}

