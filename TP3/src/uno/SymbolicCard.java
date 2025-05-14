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

        boolean isSameColor = aCard.colorIsValid(this);
        if (aCard.type.equals(type)) {
            return aCard.getSymbol().equals(symbol) || isSameColor;
        }
        return isSameColor;

    }
}
