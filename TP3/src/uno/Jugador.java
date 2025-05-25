package uno;

import java.util.ArrayList;
import java.util.List;

public class Jugador {
    public static String CartaInexistente = "El jugador no tiene esta carta";
    private List<Card> cards;
    public String nombre;
    private Jugador nextPlayer;
    private Jugador previousPlayer;
    boolean uno;

    public Jugador(String nombre) {
        this.cards = new ArrayList<>();
        this.nombre = nombre;
        this.nextPlayer = this;
        this.previousPlayer = this;
    }

    public String getNombre() {return nombre;}

    public Jugador(String nombre, Jugador nextPlayer, Jugador previousPlayer) {
        this.cards = new ArrayList<>();
        this.nombre = nombre;
        this.nextPlayer = nextPlayer;
        this.previousPlayer = previousPlayer;
    }

    public void cantarUno() {
        if (cards.size() == 2) {
            this.uno = true;
        }
    }

    public void anularUno() {
        this.uno = false;
    }

    public boolean cantoUno() { return this.uno; }

    public boolean isPlayer(String nombre) {
        return this.nombre.equals(nombre);
    }


    public Jugador getPreviousPlayer() {
        return previousPlayer;
    }

    public Jugador getNextPlayer() {
        return nextPlayer;
    }

    public Jugador addNextPlayer(String nombre) {

        Jugador newPlayer = new Jugador(nombre, this, this.previousPlayer);
        this.previousPlayer.setNextPlayer(newPlayer);
        this.setPreviousPlayer(newPlayer);
        return this;
    }

    public Jugador setPreviousPlayer(Jugador previousPlayer) {
        this.previousPlayer = previousPlayer;
        return this;
    }

    public Jugador setNextPlayer(Jugador nextPlayer) {
        this.nextPlayer = nextPlayer;
        return this;
    }

    public List<Card> getCards() {
        return cards;
    }

    public int getNumCards() {
        return cards.size();
    }

    public void tirar(Card aCard) {

        if (aCard.getClass().equals(ColoredWildCard.class)) {
            aCard = new WildCard();
        }

        if (!cards.remove(aCard)) {
            throw new RuntimeException(CartaInexistente);
        }
    }

    public void agarrar(Card aCard) {
        cards.add(aCard);
    }

}