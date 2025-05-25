package uno;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class UnoTest {

    public static String Verde = "verde";
    public static String Amarillo = "amarillo";
    public static String Azul = "azul";
    public static String Rojo = "rojo";

    public List<Card> mazoSimple;
    public List<Card> mazoChico;
    public List<Card> mazoComplejo;
    public List<String> jugadores;

    @BeforeEach
    public void setUp() {
        mazoSimple = new ArrayList<>(List.of(
                new NumberedCard(Rojo, 2),
                new NumberedCard(Verde, 2),
                new NumberedCard(Verde, 4),
                new NumberedCard(Azul, 5),
                new NumberedCard(Verde, 4),
                new NumberedCard(Azul, 5),
                new NumberedCard(Amarillo, 4),
                new NumberedCard(Azul, 2),
                new NumberedCard(Amarillo, 4),
                new NumberedCard(Azul, 5),
                new NumberedCard(Amarillo, 4),
                new NumberedCard(Azul, 5),
                new NumberedCard(Amarillo, 4)
        ));
        mazoChico= new ArrayList<>(List.of(
                new NumberedCard(Rojo, 2),
                new ReverseCard(Rojo),
                new NumberedCard(Verde, 4),
                new ReverseCard(Rojo),
                new NumberedCard(Verde, 4),
                new NumberedCard(Azul, 5),
                new NumberedCard(Amarillo, 4),
                new NumberedCard(Azul, 5),
                new NumberedCard(Amarillo, 4),
                new NumberedCard(Amarillo, 4)
        ));
        jugadores = new ArrayList<>(List.of("tomas", "delfina", "emilio"));
        mazoComplejo = new ArrayList<>(List.of(
                new NumberedCard(Amarillo, 3),
                new SkipCard(Amarillo),
                new NumberedCard(Azul, 2),
                new SkipCard(Amarillo),
                new DrawTwoCard(Amarillo),
                new NumberedCard(Amarillo, 3),
                new ReverseCard(Rojo),
                new WildCard(),
                new NumberedCard(Rojo, 5),
                new WildCard(),
                new ReverseCard(Amarillo),
                new NumberedCard(Amarillo, 5),
                new NumberedCard(Rojo, 4),
                new DrawTwoCard(Amarillo),
                new NumberedCard(Rojo, 4),
                new NumberedCard(Verde, 1),
                new NumberedCard(Azul, 7),
                new NumberedCard(Rojo, 8)
        ));
    }

    @Test public void cartaColorInvalido() {
        assertThrowsLike(ColoredCard.ColorInvalidoException, () -> new SkipCard("violeta"));
        assertThrowsLike(ColoredCard.ColorInvalidoException, () -> new ColoredWildCard("blanco"));
        assertThrowsLike(ColoredCard.ColorInvalidoException, () -> new NumberedCard("negro", 0));

    }

    @Test public void cartaNumeroInvalido() {
        assertThrowsLike(NumberedCard.NumeroInvalidoException, () -> new NumberedCard("verde", 10));
        assertThrowsLike(NumberedCard.NumeroInvalidoException, () -> new NumberedCard("verde", (-1)));
    }

    @Test
    public void testPitVacio() {
        assertThrows(NoSuchElementException.class, () -> new Juego(new ArrayList<>(), 0, jugadores).pit());
    }

    @Test public void testCartasInsuficientes() {
        assertThrowsLike(Juego.CartasInsuficientes, () -> new Juego(mazoSimple, 10, jugadores));
    }

    @Test
    public void testPitInicial() {
        assertEquals(new NumberedCard(Rojo, 2), new Juego(mazoSimple, 0, jugadores).pit());
    }

    @Test
    public void testTirarUnaCartaValida() {
        assertEquals(new NumberedCard(Verde, 2),
                new Juego(mazoSimple, 2, jugadores)
                        .tirar("tomas", new NumberedCard(Verde, 2)).pit());

    }

    @Test
    public void testJuegoFinalizado() {
        assertTrue(getJuegoTerminado().estaTerminado());
    }

    @Test public void testAvanzarTurno() {
        assertEquals("delfina", new Juego(mazoSimple, 2, jugadores)
                                                .tirar("tomas", new NumberedCard(Verde, 2))
                                                .getJugadorActual().getNombre());
    }

    @Test public void testJugadorSinCarta() {
        assertThrowsLike(Jugador.CartaInexistente, () -> new Juego(mazoSimple, 1, jugadores)
                .tirar("tomas", new NumberedCard(Rojo, 4)));
    }

    @Test public void testJugadorAgarraDelMazo() {
        Juego juego = new Juego(mazoSimple, 1, jugadores)
                        .agarrar("tomas")
                        .agarrar("delfina")
                        .agarrar("emilio");

        assertEquals("tomas", juego.getJugadorActual().getNombre());

        assertEquals(2, juego.getJugadorActual().getNumCards());
        assertThrowsLike(Juego.CartaInvalida, () -> juego.tirar("tomas", new NumberedCard(Verde, 4)));
        assertEquals(new NumberedCard(Verde, 2), juego.tirar("tomas", new NumberedCard(Verde, 2)).pit());

    }


    @Test public void testJugadorAgarraYTira() {
        assertEquals(new NumberedCard(Azul, 2), new Juego(mazoSimple, 2, jugadores)
                .agarrarYTirar("tomas", new NumberedCard(Azul, 2)).pit());
    }

    @Test public void testTurnoEquivocado() {
        assertThrowsLike(Juego.TurnoIncorrecto, () -> new Juego(mazoSimple, 1, jugadores)
                .tirar("delfina", new NumberedCard(Verde, 4)));
    }

    @Test public void testTirarDosCartas() {
        assertEquals(new NumberedCard(Verde, 4),
                new Juego(mazoSimple, 2, jugadores)
                        .tirar("tomas", new NumberedCard(Verde, 2))
                        .tirar("delfina", new NumberedCard(Verde, 4)).pit());
    }

    @Test public void testTirarCartaInvalida() {
        Juego juego = new Juego(mazoSimple, 2, jugadores);

        assertThrowsLike(Juego.CartaInvalida,
                () -> juego.tirar("tomas", new NumberedCard(Verde, 4)));

        assertEquals("tomas", juego.getJugadorActual().getNombre()); // no avanza el turno
        assertEquals(2, juego.getJugadorActual().getNumCards()); // no tiro nada
    }

    @Test public void testRondaCompleta() {

        assertEquals(new NumberedCard(Verde, 4), new Juego(mazoSimple, 3, jugadores)
                .tirar("tomas", new NumberedCard(Verde, 2))
                .tirar("delfina", new NumberedCard(Verde, 4))
                .tirar("emilio", new NumberedCard(Amarillo, 4))
                .tirar("tomas", new NumberedCard(Verde, 4)).pit());
    }

    @Test public void testWildCard() {
        Juego juego = new Juego(mazoComplejo, 4, jugadores)
                .tirar("tomas", new WildCard().asRed());

        assertEquals(Rojo, juego.pit().getColor());
        assertThrowsLike(Juego.CartaInvalida, () -> {juego.tirar("delfina", new NumberedCard(Azul, 2));});
        assertEquals("delfina", juego.getJugadorActual().getNombre());
        assertEquals(new NumberedCard(Rojo, 5), juego.tirar("delfina", new NumberedCard(Rojo, 5)).pit());
        assertEquals(Amarillo, juego.tirar("emilio", new WildCard().asYellow()).pit().getColor());
    }

    @Test public void testSkipCard() {
        assertEquals("emilio",new Juego(mazoComplejo, 4, jugadores)
                                        .tirar("tomas", new SkipCard(Amarillo))
                                        .getJugadorActual().getNombre());
    }

    @Test public void testReverseCard() {
        Juego juego =  new Juego(mazoComplejo, 4, jugadores)
                        .tirar("tomas", new ReverseCard(Amarillo));

        assertEquals("emilio", juego.getJugadorActual().getNombre());
        juego.tirar("emilio", new ReverseCard(Rojo));
        assertEquals("tomas", juego.getJugadorActual().getNombre());
    }

    @Test public void testDrawTwoCard() {

        Juego juego = new Juego(mazoComplejo, 4, jugadores).tirar("tomas", new DrawTwoCard(Amarillo))
                .tirar("emilio", new SkipCard(Amarillo));

        assertEquals(6, juego.getJugadorActual().getCards().size());
        assertEquals(new DrawTwoCard(Amarillo), juego.tirar("delfina", new DrawTwoCard(Amarillo)).pit()); // tira una que levanta del mazo
    }

    @Test public void testCantarUno() {
        Juego juego =  new Juego(mazoChico, 2, jugadores)
                        .tirarYCantarUno("tomas", new ReverseCard(Rojo))
                        .tirar("emilio", new ReverseCard(Rojo));

        assertTrue(juego.getJugadorActual().cantoUno());
    }

    @Test public void testFalsoUno() {

        Juego juego =  new Juego(mazoChico, 3, jugadores).tirarYCantarUno("tomas", new ReverseCard(Rojo))
                .tirar("emilio", new ReverseCard(Rojo));
        assertFalse(juego.getJugadorActual().cantoUno());
    }

    @Test
    public void testNoSePuedeTirarLuegoDeGanar() {
        assertThrowsLike(Juego.JuegoFinalizado, () -> getJuegoTerminado().tirar("delfina", new NumberedCard(Verde, 4)));
    }

    @Test public void testNoSePuedeAgarrarLuegoDeGanar() {
        assertThrowsLike(Juego.JuegoFinalizado, () -> getJuegoTerminado().agarrar("tomas"));
    }

    private Juego getJuegoTerminado() {
        return new Juego(mazoSimple, 1, jugadores)
                .tirar("tomas", new NumberedCard(Verde, 2));
    }

    private void assertThrowsLike(String exceptionMessage, Executable executable) {
        assertEquals(exceptionMessage,
                assertThrows(Exception.class, executable).getMessage());
    }
}