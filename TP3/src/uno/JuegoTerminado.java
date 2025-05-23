package uno;

public class JuegoTerminado extends Juego{
    private Jugador ganador;
    public String ExcepcionJuegoTerminado = "El juego ya ha finalizado";

    public JuegoTerminado(Jugador ganador) {
        this.ganador = ganador;
    }
    public String getGanador() {return ganador.getName();}

    public Jugador getJugadorActual() {throw new RuntimeException(ExcepcionJuegoTerminado);}

    public Juego tirar(String nombre, ColoredCard card) {throw new RuntimeException(ExcepcionJuegoTerminado);}
    public Juego tirarYCantarUno(String nombre, ColoredCard card) {throw new RuntimeException(ExcepcionJuegoTerminado);}
    public Card pit() {throw new RuntimeException(ExcepcionJuegoTerminado);}



}
