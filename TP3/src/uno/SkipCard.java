package uno;

public class SkipCard extends SymbolicCard {
    public SkipCard(String color) {
        super(color, "Skip");
    }
    public void actionOn(Juego juego) {
        juego.comportamientoSkip();
    }
}