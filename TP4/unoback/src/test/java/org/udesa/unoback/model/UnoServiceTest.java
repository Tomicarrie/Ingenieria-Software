package org.udesa.unoback.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import org.udesa.unoback.service.Dealer;
import org.udesa.unoback.service.UnoService;
import org.udesa.unoback.model.UnoTest;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UnoServiceTest {

    @MockBean Dealer dealer;
    List<String> players = List.of("Delfina", "Tomas");
    @Autowired private UnoService unoService;

    public static List<Card> myDeck = List.of(new NumberCard("Blue", 3),
                                new SkipCard("Blue"),
                                new NumberCard("Red", 1),
                                new NumberCard("Green", 2),
                                new Draw2Card("Blue"),
                                new NumberCard("Green", 2),
                                new NumberCard("Yellow", 9),
                                new NumberCard("Green", 2),
                                new NumberCard("Red", 1),
                                new NumberCard("Green", 2),
                                new NumberCard("Green", 3),
                                new NumberCard("Blue", 2),
                                new NumberCard("Yellow", 2),
                                new NumberCard("Green", 2),
                                new NumberCard("Red", 5),
                                new NumberCard("Red", 7));


    @BeforeEach
    public void setup() {
        when( dealer.fullDeck() ).thenReturn( myDeck );
    }

    @Test
    public void newMatchTest() {
        assertNotNull( unoService.newMatch( players ) );
    }

    @Test
    public void invalidUUIDTest() {
        assertThrowsLike("Unexistent UUID", () -> { unoService.playerHand( UUID.randomUUID() ); });
    }

    @Test public void playerHandTest() {
        assertEquals(myDeck.subList( 1, 8 ), unoService.playerHand( unoService.newMatch(players) ));
    }



    @Test
    public void activeCardTest() {
        JsonCard activeCard = unoService.activeCard( unoService.newMatch( players ) );
        assertEquals("Blue", activeCard.getColor());
        assertEquals(3, ( (NumberCard) activeCard.asCard()).number() );
    }

    @Test
    public void playCardTest() {
        UUID id = unoService.newMatch( players );
        assertDoesNotThrow(() -> unoService.play( id, "Delfina", unoService.playerHand( id ).getFirst().asJson()));
    }

    @Test
    public void drawCardTest() {

        UUID id = unoService.newMatch(players);

        assertEquals(7, unoService.playerHand( id ).size());
        unoService.drawCard(id, "Delfina");
        assertEquals(8, unoService.playerHand( id ).size());
    }

    private void assertThrowsLike( String exceptionMessage, Executable executable ) {
        assertEquals( exceptionMessage,
                assertThrows( Exception.class, executable ).getMessage() );
    }

}
