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

        this.mazo = new ArrayList<>(mazoInicial);
        this.pit = this.mazo.removeFirst();
        this.cartasARepartir = cartasPorJugador * jugadores.size();

        List<String> copiaJugadores = new ArrayList<>(jugadores);
        jugadorActual = new Jugador(copiaJugadores.removeFirst());
        copiaJugadores.forEach(jugador -> {jugadorActual.addNextPlayer(jugador);});
    }

    public Jugador getJugadorActual() {return jugadorActual;}

    public Juego tirar(String nombre, ColoredCard card) {

        if (!jugadorActual.isPlayer(nombre)) {
            throw new RuntimeException("No es el turno del jugador");
        }

        if (card.getClass().equals(ColoredWildCard.class)) {
            jugadorActual.tirar(new WildCard());
        } else {
            if (!card.accepts(pit)) {
                throw new RuntimeException("No es una carta valida");
            }
            jugadorActual.tirar(card);
        }

        if ((jugadorActual.getCards().size() == 1) && !jugadorActual.cantoUno() ){
            String nombreActual = jugadorActual.getNombre();
            this.agarrar(nombreActual);
            this.agarrar(nombreActual);
        }

        this.pit = card;
        this.pit.actionOn(this);
        avanzarTurno();
        return this;

    }

    public Juego tirarYCantarUno(String nombre, ColoredCard card) {
        jugadorActual.cantarUno();
        this.tirar(nombre, card);
        return this;
    }

    public Card pit() {
        return pit;
    }

    public Juego agarrar(String nombre){
        if (!jugadorActual.isPlayer(nombre)) {
            throw new RuntimeException("No es el turno del jugador");
        }
        jugadorActual.anularUno();
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

    public void comportamientoReverse() {
        invertirDireccion();
    }

    public void comportamientoDrawTwo() {
        avanzarTurno();
        jugadorActual.agarrar(mazo.removeFirst());
        jugadorActual.agarrar(mazo.removeFirst());
    }

    public void comportamientoSkip() {
        avanzarTurno();
    }





}