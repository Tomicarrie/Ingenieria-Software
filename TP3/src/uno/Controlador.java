package uno;

public abstract class Controlador {
    public abstract Jugador getNextPlayer(Jugador jugadorActual);
    public abstract Controlador changeControlador();
}

