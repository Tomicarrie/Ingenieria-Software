package uno;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Juego {
    private List<Card> mazo = new ArrayList<Card>();
    private Jugador jugadorActual;
    private Card pit;
    private int direccion = 1;
    private int cartasARepartir;


    public Juego(List<Card> mazoInicial, int cartasARepartir, List<String> jugadores) {

        this.pit = mazoInicial.removeFirst();
        this.mazo = mazoInicial;
        this.cartasARepartir = cartasARepartir;
        jugadorActual = new Jugador(jugadores.removeFirst());
        jugadores.forEach(jugador -> {jugadorActual.addNextPlayer(jugador);});

    }

    public Card jugadorTira(Jugador jugador, Card card) {
        return jugador.tirar(card);
    }

    public Card pit() {
        return pit;
    }

    public void repartir() {
        int cantidadCartas = this.cartasARepartir;
        while (cantidadCartas > 0) {
            cantidadCartas--;
            jugadorActual.agarrar(mazo.removeFirst());
            jugadorActual = jugadorActual.getNextPlayer(this.direccion);
        }
    }

    public void avanzarTurno() {
        jugadorActual = jugadorActual.getNextPlayer(this.direccion);
    }

    public void invertirDireccion() {
        direccion *= -1;
    }




}
