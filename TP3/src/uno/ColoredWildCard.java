package uno;

import java.util.ArrayList;
import java.util.List;

public class ColoredWildCard extends ColoredCard {


    public ColoredWildCard(String color) {
        super();
        if (!colorEsValido(color)) {
            throw new RuntimeException(ColoredCard.ColorInvalidoException);
        }
        this.color = color;

    }

    public int getNumber() {
        throw new RuntimeException("Esta carta no tiene numero");
    }

    public String getSymbol() {
        return "ColoredWildCard";
    }

    public boolean accepts(Card aCard) {
        return true;
    }

    public boolean acceptsSymbol(SymbolicCard aCard) {return false;}
    public boolean acceptsNumber(NumberedCard aCard) {return false;}

    public void actionOn(Juego juego) {
        return;
    }
}