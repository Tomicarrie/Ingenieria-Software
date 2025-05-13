package uno;
public abstract class SymbolicCard extends Card {
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
    
    public boolean isValid(Card aCard) {

        boolean isSameColor = colorIsValid(aCard);
        if (aCard.type.equals(type)) {
            return aCard.getSymbol().equals(symbol) || isSameColor;
        }
        return isSameColor;

    }
}
