package uno;

public class controladorIzquierda extends Controlador{
    public Jugador getNextPlayer(Jugador jugadorActual){
        return jugadorActual.getPreviousPlayer();
    }
    
    public Controlador changeControlador(){
        return new controladorDerecha();
    }
}
