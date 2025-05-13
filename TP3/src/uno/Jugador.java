package uno;
import java.util.ArrayList;
import java.util.List;

public class Jugador {
    private List<Card> cards;
    public String nombre;
    private Jugador nextPlayer;
    private Jugador previousPlayer;

    public Jugador(String nombre) {
        this.cards = new ArrayList<>();
        this.nombre = nombre;
        this.nextPlayer = this;
        this.previousPlayer = this;

    }

    public Jugador(String nombre, Jugador nextPlayer, Jugador previousPlayer) {
        this.cards = new ArrayList<>();
        this.nombre = nombre;
        this.nextPlayer = nextPlayer;
        this.previousPlayer = previousPlayer;

    }


    public Jugador getPreviousPlayer() {
        return previousPlayer;
    }

    public Jugador getNextPlayer(int direccion) {
        if (direccion == 1) {
            return nextPlayer;
        }
        return previousPlayer;
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

    public Card tirar(Card aCard) {
        if (!cards.remove(aCard)) {
            throw new RuntimeException("El jugador no tiene esta carta");
        }
        return aCard;
    }

    public void agarrar(Card aCard) {
        cards.add(aCard);
    }

}
