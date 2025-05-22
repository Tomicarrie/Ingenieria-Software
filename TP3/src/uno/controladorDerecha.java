package uno;

public class controladorDerecha extends Controlador{
    public Jugador getNextPlayer(Jugador jugadorActual){
        return jugadorActual.getNextPlayer();
    }

    public Controlador changeControlador(){
        return new controladorIzquierda();
    }
}
