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

    public abstract boolean colorIsValid(ColoredCard aCard);

    public abstract boolean equals(Object obj);

    public abstract String getColor();

}


