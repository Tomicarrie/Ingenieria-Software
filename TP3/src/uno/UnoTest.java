package uno;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class UnoTest {
    public List<Card> mazo;
    public List<String> jugadores;
    @BeforeEach
    public void setUp() {
        mazo = new ArrayList<>();
        mazo.add(new NumberedCard("rojo", 12));
        mazo.add(new NumberedCard("verde", 13));

        jugadores = new ArrayList<>();
        jugadores.add("tomas");
        jugadores.add("julio");
        jugadores.add("emilio");
    }

    @Test public void testPitVacio() {
        assertThrows(NoSuchElementException.class, () -> new Juego(new ArrayList<>(), 0, new ArrayList<>()));
    }

    @Test public void testPitEsPrimerCarta() {
        assertEquals(new NumberedCard("rojo", 12),  new Juego(mazo, 0, jugadores).pit());
    }

    @Test public void testPitEsLaQueTiraElJugador() {
        // assertEquals(new NumberedCard("verde", 13),  new Juego(mazo, 2, jugadores).repartir().tirar());

    }



    /*
    @Test public void test1() {

        Card carta1 = new NumberedCard("rojo", 12);
        Card carta2 = new NumberedCard("verde", 12);
        Card carta3 = new NumberedCard("rojo", 10);

        assertTrue(carta1.isValid(carta2));
        assertTrue(carta1.isValid(carta3));
        assertFalse(carta2.isValid(carta3));

        Card carta4 = new DrawTwoCard("rojo");
        Card carta5 = new DrawTwoCard("verde");
        Card carta6 = new ReverseCard("azul");

        assertTrue(carta4.isValid(carta3));
        assertTrue(carta4.isValid(carta5));
        assertFalse(carta4.isValid(carta2));
        assertFalse(carta6.isValid(carta5));
    }

    @Test public void test2() {

        Card carta1 = new NumberedCard("rojo", 12);
        Card carta2 = new NumberedCard("verde", 12);
        Card carta3 = new NumberedCard("rojo", 10);

        List<Card> cards = new ArrayList<Card>();
        cards.add(carta1);
        cards.add(carta2);
        cards.add(carta3);

        Jugador jugador = new Jugador(cards, "tomas");
        assertEquals(carta1, jugador.tirar(new NumberedCard("rojo", 12)));
        assertEquals(2, jugador.getCards().size());

        assertThrows(RuntimeException.class, () -> jugador.tirar(new NumberedCard("verde", 10)));

    }
    */

}
