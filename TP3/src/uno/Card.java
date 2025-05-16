package uno;

public abstract class Card {

    protected String type;

    // BORRAR DPS
    public int getNumber() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    // BORRAR DPS
    public String getSymbol() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public abstract boolean accepts(Card aCard);

    public abstract boolean acceptsColor(ColoredCard aCard);

    public abstract boolean acceptsSymbol(SymbolicCard aCard);

    public abstract boolean acceptsNumber(NumberedCard aCard);

    public abstract boolean equals(Object obj);

    public abstract String getColor();

    public abstract void actionOn(Juego juego);

}

