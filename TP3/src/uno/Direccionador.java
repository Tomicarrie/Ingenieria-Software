package uno;

public abstract class Direccionador {
    public abstract Jugador getNextPlayer(Jugador jugadorActual);
    public abstract Direccionador invertirDireccion();
}