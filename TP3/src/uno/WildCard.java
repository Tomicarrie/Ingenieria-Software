package uno;

public class WildCard extends Card {

    public int getNumber() {
        throw new RuntimeException("Esta carta no tiene numero");
    }

    public String getSymbol() {
        return "WildCard";
    }

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

    public void actionOn(Juego juego) {
        return;
    }


    public ColoredCard asRed() {
        return new ColoredWildCard(UnoTest.Rojo);
    }
    public ColoredCard asBlue() {
        return new ColoredWildCard(UnoTest.Azul);
    }
    public ColoredCard asGreen() {
        return new ColoredWildCard(UnoTest.Verde);
    }
    public ColoredCard asYellow() {
        return new ColoredWildCard(UnoTest.Amarillo);
    }

}