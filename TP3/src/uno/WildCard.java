package uno;

public class WildCard extends Card {
    public String type = "Wild";
    public boolean accepts(Card aCard) {
        return true;
    }

    public boolean acceptsColor(ColoredCard aCard) {
        return true;
    }
    public boolean acceptsSymbol(SymbolicCard aCard) {return false;}
    public boolean acceptsNumber(NumberedCard aCard) {return false;}

    public String getColor() {
        throw new RuntimeException("Wildcard no tiene un colo asignado todavia");
    }

    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        return true;
    }

}

