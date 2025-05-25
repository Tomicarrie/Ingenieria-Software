package uno;

public abstract class SymbolicCard extends ColoredCard {
    protected String symbol;

    public SymbolicCard(String color, String symbol) {
        super();
        this.color = color;
        this.symbol = symbol;
        this.type = "Symbolic";
    }

    public String getSymbol() {
        return symbol;
    }

    public boolean accepts(Card aCard) {
        return aCard.acceptsColor(this) || aCard.acceptsSymbol(this);
    }
    public boolean acceptsSymbol(SymbolicCard aCard) {return symbol.equals(aCard.getSymbol());}
    public boolean acceptsNumber(NumberedCard aCard) {return false;}
    public abstract void actionOn(Juego juego);
}