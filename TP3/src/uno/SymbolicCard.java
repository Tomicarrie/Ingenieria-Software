package uno;

public abstract class SymbolicCard extends ColoredCard {
    protected String symbol;

    public SymbolicCard(String color, String symbol) {
        super();
        if (!colorEsValido(color)) {
            throw new RuntimeException(ColoredCard.ColorInvalidoException);
        }
        this.color = color;
        this.symbol = symbol;

    }

    public String getSymbol() {
        return symbol;
    }
    public int getNumber() {
        throw new RuntimeException("Esta carta no tiene numero");
    }

    public boolean accepts(Card aCard) {
        return aCard.acceptsColor(this) || aCard.acceptsSymbol(this);
    }
    public boolean acceptsSymbol(SymbolicCard aCard) {return symbol.equals(aCard.getSymbol());}
    public boolean acceptsNumber(NumberedCard aCard) {return false;}
    public abstract void actionOn(Juego juego);
}