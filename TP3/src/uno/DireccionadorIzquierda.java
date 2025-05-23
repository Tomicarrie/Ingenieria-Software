package uno;

public class DireccionadorIzquierda extends Direccionador {

    public Jugador getNextPlayer(Jugador jugadorActual){
        return jugadorActual.getPreviousPlayer();
    }

    public Direccionador invertirDireccion(){
        return new DireccionadorDerecha();
    }
}