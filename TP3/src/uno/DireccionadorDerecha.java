package uno;

public class DireccionadorDerecha extends Direccionador {
    public Jugador getNextPlayer(Jugador jugadorActual){
        return jugadorActual.getNextPlayer();
    }

    public Direccionador invertirDireccion(){
        return new DireccionadorIzquierda();
    }
}