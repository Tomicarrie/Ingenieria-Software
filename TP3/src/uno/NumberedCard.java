package uno;

import java.util.ArrayList;
import java.util.List;

public class NumberedCard extends ColoredCard {
    public static String NumeroInvalidoException = "El numero no es valido. Debe estar entre 0 y 9";
    private int number;
    private List<Integer> numerosValidos = new ArrayList<>(List.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9));

    public NumberedCard(String color, int number) {
        super();
        if (!colorEsValido(color)) {
            throw new RuntimeException(ColoredCard.ColorInvalidoException);
        }
        if (!numeroEsValido(number)) {
            throw new RuntimeException(NumeroInvalidoException);
        }
        this.color = color;
        this.number = number;


    }
    public int getNumber() {
        return number;
    }

    public boolean numeroEsValido(int number) {
        return numerosValidos.contains(number);
    }

    public String getSymbol() {
        throw new RuntimeException("Esta carta es numerica, no simbolica");
    }

    public boolean accepts(Card aCard) {
        return aCard.acceptsColor(this) || aCard.acceptsNumber(this);
    }

    public boolean acceptsSymbol(SymbolicCard aCard) {return false;}
    public boolean acceptsNumber(NumberedCard aCard) { return number == aCard.getNumber();}

    public void actionOn(Juego juego) {
        return;
    }

}