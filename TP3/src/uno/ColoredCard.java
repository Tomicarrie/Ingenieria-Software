package uno;

public abstract class ColoredCard extends Card {
    protected String color;

    public String getColor() {
        return color;
    }

    public boolean colorIsValid(ColoredCard aCard) {
        return aCard.getColor().equals(color);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        ColoredCard aCard = (ColoredCard) obj;

        if (!this.color.equals(aCard.color)) return false;

        if (this instanceof NumberedCard && aCard instanceof NumberedCard) {
            return this.getNumber() == aCard.getNumber();
        } else if (this.getClass().getSuperclass().equals(SymbolicCard.class) && aCard.getClass().getSuperclass().equals(SymbolicCard.class)) {
            return this.getSymbol().equals(aCard.getSymbol());
        }
        return false;
    }
    public abstract boolean accepts(Card aCard);
    public boolean acceptsColor(ColoredCard aCard) {return color.equals(aCard.getColor());}
    public abstract void actionOn(Juego juego);

}