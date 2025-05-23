package uno;
import java.util.ArrayList;
import java.util.List;

public class JuegoEnCurso extends Juego {

    private List<Card> mazo = new ArrayList<Card>();
    private Jugador jugadorActual;
    private Card pit;
    private Direccionador direccion;
    private int cartasARepartir;


    public JuegoEnCurso(List<Card> mazoInicial, int cartasPorJugador, List<String> jugadores) {

        this.mazo = new ArrayList<>(mazoInicial);
        this.pit = this.mazo.removeFirst();
        this.cartasARepartir = cartasPorJugador * jugadores.size();
        this.direccion = new DireccionadorDerecha();

        List<String> copiaJugadores = new ArrayList<>(jugadores);
        jugadorActual = new Jugador(copiaJugadores.removeFirst());

        copiaJugadores.forEach(jugador -> {jugadorActual.addNextPlayer(jugador);});

        repartir();

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

            if (jugadorActual.getNumCards() == 0) {
                return new JuegoTerminado(jugadorActual);
            }
        }

        if ((jugadorActual.getNumCards() == 1) && !jugadorActual.cantoUno() ){
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

        if (jugadorActual.getNumCards() == 0) {
            return new JuegoTerminado(jugadorActual);
        }

        return this;
    }

    public Card pit() {
        return pit;
    }

    public JuegoEnCurso agarrar(String nombre){
        if (!jugadorActual.isPlayer(nombre)) {
            throw new RuntimeException("No es el turno del jugador");
        }
        jugadorActual.anularUno();
        jugadorActual.agarrar(mazo.removeFirst());
        return this;
    }

    private JuegoEnCurso repartir() {
        int cantidadCartas = this.cartasARepartir;
        if (this.cartasARepartir > mazo.size()) {
            throw new RuntimeException("No hay suficientes cartas en el mazo para repartir");
        }

        while (cantidadCartas > 0) {
            cantidadCartas--;
            jugadorActual.agarrar(mazo.removeFirst());
            avanzarTurno();
        }
        return this;
    }

    private void avanzarTurno() {
        jugadorActual = direccion.getNextPlayer(jugadorActual);
    }

    private void invertirDireccion() {
        this.direccion = this.direccion.invertirDireccion();
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