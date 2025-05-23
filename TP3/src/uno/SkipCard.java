package uno;

public class SkipCard extends SymbolicCard {
    public SkipCard(String color) {
        super(color, "Skip");
    }
    public void actionOn(JuegoEnCurso juego) {
        juego.comportamientoSkip();
    }
}