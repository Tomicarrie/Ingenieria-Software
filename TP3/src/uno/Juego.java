package uno;

public abstract class Juego {
    public abstract Jugador getJugadorActual();
    public abstract Juego tirar(String nombre, ColoredCard card);
    public abstract Juego tirarYCantarUno(String nombre, ColoredCard card);
    public abstract Card pit();
    public String getGanador() {throw new RuntimeException("El juego todavia no tiene un ganador");}

}
