package uno;

public class DrawTwoCard extends SymbolicCard {
    public DrawTwoCard(String color) {
        super(color, "DrawTwo");
    }
    public void actionOn(Juego juego) {
        juego.comportamientoDrawTwo();
    }
}