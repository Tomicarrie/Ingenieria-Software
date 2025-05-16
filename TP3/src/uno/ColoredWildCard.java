package uno;

public class ColoredWildCard extends ColoredCard {
    public ColoredWildCard(String color) {
        super();
        this.color = color;

    }

    public boolean accepts(Card aCard) {
        return aCard.acceptsColor(this);
    }

    public boolean acceptsSymbol(SymbolicCard aCard) {return false;}
    public boolean acceptsNumber(NumberedCard aCard) {return false;}

    public void actionOn(Juego juego) {
        return;
    }
}