package uno;

public class ReverseCard extends SymbolicCard {
    public ReverseCard(String color) {
        super(color, "Reverse");
    }

    public void actionOn(JuegoEnCurso juego) {
        juego.comportamientoReverse();
    }
}