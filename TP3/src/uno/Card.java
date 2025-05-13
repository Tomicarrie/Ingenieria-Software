package uno;

public abstract class Card {
    protected String color = "black";
    protected String type;

    public String getColor() {
        return color;
    }
    public boolean colorIsValid(Card aCard) {
        return aCard.getColor().equals(color) || aCard.getColor().equals("black");
    }

    // BORRAR DPS
    public int getNumber() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    // BORRAR DPS
    public String getSymbol() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public abstract boolean isValid(Card aCard);


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Card aCard = (Card) obj;

        if (!this.color.equals(aCard.color)) return false;

        if (this instanceof NumberedCard && aCard instanceof NumberedCard) {
            return this.getNumber() == aCard.getNumber();
        } else if (this instanceof SymbolicCard && aCard instanceof SymbolicCard) {
            return this.getSymbol().equals(aCard.getSymbol());
        }
        // Comparación para wildcards
        return false;
    }




}

