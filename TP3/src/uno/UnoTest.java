package uno;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class UnoTest {
    public List<Card> mazoSimple;
    public List<String> jugadores;

    @BeforeEach
    public void setUp() {
        mazoSimple = new ArrayList<>();
        mazoSimple.add(new NumberedCard("rojo", 2));
        mazoSimple.add(new NumberedCard("verde", 2));
        mazoSimple.add(new NumberedCard("verde", 4));
        mazoSimple.add(new NumberedCard("azul", 5));
        mazoSimple.add(new NumberedCard("verde", 4));
        mazoSimple.add(new NumberedCard("azul", 5));
        mazoSimple.add(new NumberedCard("amarillo", 4));

        jugadores = new ArrayList<>();
        jugadores.add("tomas");
        jugadores.add("delfina");
        jugadores.add("emilio");
    }

    @Test
    public void testPitVacio() {
        assertThrows(NoSuchElementException.class, () -> new Juego(new ArrayList<>(), 0, jugadores).pit());
    }

    @Test
    public void testPitInicial() {
        assertEquals(new NumberedCard("rojo", 2), new Juego(mazoSimple, 0, jugadores).pit());
    }

    @Test
    public void testTirarUnaCartaValida() {
        assertEquals(new NumberedCard("verde", 2),
                new Juego(mazoSimple, 1, jugadores)
                        .repartir().tirar("tomas", new NumberedCard("verde", 2)).pit());

    }

    @Test public void testJugadorSinCarta() {
        assertThrowsLike("El jugador no tiene esta carta", () -> new Juego(mazoSimple, 1, jugadores)
                .repartir().tirar("tomas", new NumberedCard("rojo", 4)));
    }

    @Test public void testTurnoEquivocado() {
        assertThrowsLike("No es el turno del jugador", () -> new Juego(mazoSimple, 1, jugadores)
                .repartir().tirar("delfina", new NumberedCard("verde", 4)));
    }

    @Test public void testTirarDosCartas() {
        assertEquals(new NumberedCard("verde", 4),
                new Juego(mazoSimple, 1, jugadores)
                        .repartir().tirar("tomas", new NumberedCard("verde", 2))
                        .tirar("delfina", new NumberedCard("verde", 4)).pit());
    }


    @Test public void testTirarCartaInvalida() {
        assertThrowsLike("No es una carta valida",
                () -> new Juego(mazoSimple, 2, jugadores)
                        .repartir().tirar("tomas", new NumberedCard("verde", 4)));
    }

    @Test public void testJugadorGana() { // QUE HACER CUANDO UN JUGDOR GANA?? TIRAR ERROR SI SE QUIERE SEGUIR JUGANDO? NUEVA CLASE DE JUEGO TERMINADO QUE TIRE EXCEPCIONES?
        assertEquals(new NumberedCard("verde", 4), new Juego(mazoSimple, 2, jugadores)
                        .repartir().tirar("tomas", new NumberedCard("verde", 2))
                        .tirar("delfina", new NumberedCard("verde", 4))
                        .tirar("emilio", new NumberedCard("amarillo", 4))
                        .tirar("tomas", new NumberedCard("verde", 4)).pit());
    }


    private void assertThrowsLike(String exceptionMessage, Executable executable) {
        assertEquals(exceptionMessage,
                assertThrows(Exception.class, executable).getMessage());
    }

    /*
    * Funcionalidad de las cartas
    * Tests
    * Sacar el If de la direccion
    * Como cantar uno
    * Consultar el double dispatch
    * Terminar juego
    * Se puede cuando se crea el jugador, decirle cuantas cartas agarrar y pasarle el mazo al crearlo.
    * Temas de checkeo de la cantidad de cartas a repartir menor a las cartas en el mazo y esas cosas.
    * Como repartir.
    */
}