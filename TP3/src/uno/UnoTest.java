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
    public List<Card> mazoChico;
    public List<Card> mazoComplejo;
    public List<String> jugadores;

    @BeforeEach
    public void setUp() {
        mazoSimple = new ArrayList<>(List.of(
                new NumberedCard("rojo", 2),
                new NumberedCard("verde", 2),
                new NumberedCard("verde", 4),
                new NumberedCard("azul", 5),
                new NumberedCard("verde", 4),
                new NumberedCard("azul", 5),
                new NumberedCard("amarillo", 4),
                new NumberedCard("azul", 5),
                new NumberedCard("amarillo", 4),
                new NumberedCard("azul", 5),
                new NumberedCard("amarillo", 4),
                new NumberedCard("azul", 5),
                new NumberedCard("amarillo", 4)
        ));
        mazoChico= new ArrayList<>(List.of(
                new NumberedCard("rojo", 2),
                new ReverseCard("rojo"),
                new NumberedCard("verde", 4),
                new ReverseCard("rojo"),
                new NumberedCard("verde", 4),
                new NumberedCard("azul", 5),
                new NumberedCard("amarillo", 4),
                new NumberedCard("azul", 5),
                new NumberedCard("amarillo", 4),
                new NumberedCard("amarillo", 4)
        ));
        jugadores = new ArrayList<>(List.of("tomas", "delfina", "emilio"));
        mazoComplejo = new ArrayList<>(List.of(
                new NumberedCard("amarillo", 3),
                new SkipCard("amarillo"),
                new NumberedCard("azul", 2),
                new SkipCard("amarillo"),
                new DrawTwoCard("amarillo"),
                new NumberedCard("amarillo", 3),
                new ReverseCard("rojo"),
                new WildCard(),
                new NumberedCard("amarillo", 5),
                new NumberedCard("amarillo", 2),
                new ReverseCard("amarillo"),
                new NumberedCard("amarillo", 5),
                new NumberedCard("rojo", 4),
                new DrawTwoCard("amarillo"),
                new NumberedCard("rojo", 4),
                new NumberedCard("verde", 1),
                new NumberedCard("azul", 7),
                new NumberedCard("rojo", 8)

        ));
        // chequear: funcionalidad, apoyar un mismo simbolo es valido
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

    @Test public void testAvanzarTurno() {
        assertEquals("delfina", new Juego(mazoSimple, 2, jugadores)
                .repartir().tirar("tomas", new NumberedCard("verde", 2)).getJugadorActual().getNombre());
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


    @Test public void testWildCard() {
        Juego juego = new Juego(mazoComplejo, 4, jugadores).repartir().tirar("tomas", new WildCard().asignarColor("rojo"));
        assertEquals("delfina", juego.getJugadorActual().getNombre());
        assertEquals("rojo", juego.pit().getColor());

    }

    @Test public void testSkipCard() {
        assertEquals("emilio",new Juego(mazoComplejo, 4, jugadores).repartir().tirar("tomas", new SkipCard("amarillo")).getJugadorActual().getNombre());
    }

    @Test public void testReverseCard() {
        Juego juego =  new Juego(mazoComplejo, 4, jugadores).repartir().tirar("tomas", new ReverseCard("amarillo"));
        assertEquals("emilio", juego.getJugadorActual().getNombre());
        juego.tirar("emilio", new ReverseCard("rojo"));
        assertEquals("tomas", juego.getJugadorActual().getNombre());
    }

    @Test public void testDrawTwoCard() {
        Juego juego = new Juego(mazoComplejo, 4, jugadores).repartir().tirar("tomas", new DrawTwoCard("amarillo"))
                                                                            .tirar("emilio", new SkipCard("amarillo"));
        assertEquals(6, juego.getJugadorActual().getCards().size());
        juego.tirar("delfina", new DrawTwoCard("amarillo")); // tira una que levanta del mazo
    }


    private void assertThrowsLike(String exceptionMessage, Executable executable) {
        assertEquals(exceptionMessage,
                assertThrows(Exception.class, executable).getMessage());
    }

    @Test public void testCantarUno() {

        Juego juego =  new Juego(mazoChico, 2, jugadores).repartir().tirarYCantarUno("tomas", new ReverseCard("rojo"))
                                                                                    .tirar("emilio", new ReverseCard("rojo"));
        assertTrue(juego.getJugadorActual().cantoUno());
    }



    /*
     * Tests
     * Terminar juego
     * Temas de checkeo de la cantidad de cartas a repartir menor a las cartas en el mazo y esas cosas.
     */
}