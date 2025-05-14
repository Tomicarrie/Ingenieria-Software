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


    public Juego(List<Card> mazoInicial, int cartasPorJugador, List<String> jugadores) {

        this.pit = mazoInicial.removeFirst();
        this.mazo = mazoInicial;
        this.cartasARepartir = cartasPorJugador * jugadores.size();
        jugadorActual = new Jugador(jugadores.removeFirst());
        jugadores.forEach(jugador -> {jugadorActual.addNextPlayer(jugador);});

    }

    public Juego tirar(String nombre, Card card) {

        if (!jugadorActual.isPlayer(nombre)) {
            throw new RuntimeException("No es el turno del jugador");
        }
        if (!card.accepts(pit)) {
            throw new RuntimeException("No es una carta valida");
        }

        jugadorActual.tirar(card);
        this.pit = card;
        jugadorActual = jugadorActual.getNextPlayer(this.direccion);
        return this;

    }

    public Card pit() {
        return pit;
    }

    public Juego agarrar(String nombre, int numero){
        if (!jugadorActual.isPlayer(nombre)) {
            throw new RuntimeException("No es el turno del jugador");
        }
        jugadorActual.agarrar(mazo.removeFirst());
        return this;
    }

    public Juego repartir() {
        int cantidadCartas = this.cartasARepartir;
        while (cantidadCartas > 0) {
            cantidadCartas--;
            jugadorActual.agarrar(mazo.removeFirst());
            jugadorActual = jugadorActual.getNextPlayer(this.direccion);
        }
        return this;
    }

    public void avanzarTurno() {
        jugadorActual = jugadorActual.getNextPlayer(this.direccion);
    }

    public void invertirDireccion() {
        direccion *= -1;
    }


}
