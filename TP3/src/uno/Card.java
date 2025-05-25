package uno;

public abstract class Card {

    public abstract int getNumber();

    public abstract String getSymbol();

    public abstract boolean accepts(Card aCard);

    public abstract boolean acceptsColor(ColoredCard aCard);

    public abstract boolean acceptsSymbol(SymbolicCard aCard);

    public abstract boolean acceptsNumber(NumberedCard aCard);

    public abstract boolean equals(Object obj);

    public abstract String getColor();

    public abstract void actionOn(Juego juego);

}
