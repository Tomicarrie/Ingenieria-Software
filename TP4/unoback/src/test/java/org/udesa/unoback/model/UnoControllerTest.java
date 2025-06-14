package org.udesa.unoback.model;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.udesa.unoback.service.Dealer;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UnoControllerTest {

    @Autowired private MockMvc mockMvc;

    @MockBean private Dealer dealer;

    @BeforeEach
    public void setUp() {
        when( dealer.fullDeck() ).thenReturn( UnoServiceTest.myDeck );
    }

    @Test void playWrongTurnTest() throws Throwable {

        String uuid = newGame();
        assertNotNull( UUID.fromString( uuid ) );


        String resp = postJsonAndExpectClientError( post( "/play/" + uuid + "/Julio"),
                                                    activeHand( uuid ).getFirst().toString() );

        assertEquals(Player.NotPlayersTurn + "Julio", resp );
    }


    @Test void newMatchTest() throws Exception {

        String resp = performRequestAndExpectOk( post("/newmatch?players=Emilio&players=Julio" ) );
        assertNotNull( UUID.fromString( toPlainText( resp ) ) );
    }


    @Test void newMatchWithoutPlayersTest() throws Exception {
        mockMvc.perform( post("/newmatch") ).andExpect( status().isBadRequest() );
    }

    @Test void activeCardTest() throws Exception {

        String resp = performRequestAndExpectOk( get("/activecard/" + newGame() ) );

        assertNotNull( resp );
        assertNotNull( new ObjectMapper().readValue( resp, JsonCard.class ) );

    }

    @Test void drawCardTest() throws Exception {

        mockMvc.perform( post("/draw/" + newGame() + "/Emilio" ) )
                .andExpect( status().isOk() );
    }

    @Test
    void playerHandTest() throws Exception {

        List<JsonCard> cards = toJsonCardList( performRequestAndExpectOk( get("/playerhand/" + newGame() ) ) );
        assertNotNull( cards );
        assert( !cards.isEmpty() );
    }


    @Test void wrongColorCardTest() throws Exception {

        JsonCard jsonCard = new JsonCard("Orange", 2, "NumberCard", false);
        String resp = postJsonAndExpectClientError( post("/play/" + newGame() + "/Emilio" ),
                                                    toJsonString( jsonCard ));

        assertEquals("Invalid color", resp );

    }
    @Test void wrongNumberCardTest() throws Exception {

        JsonCard jsonCard = new JsonCard("Red", 11, "NumberCard", false );
        String resp = postJsonAndExpectClientError( post("/play/" + newGame() + "/Emilio" ),
                                                    toJsonString( jsonCard ));

        assertEquals("Number must be between 0 and 9", resp);

    }

    @Test void newMatchWithOnePlayerTest() throws Exception {

        String resp = postJsonAndExpectClientError(post("/newmatch?players=Emilio"), "");
        assertEquals("There must be at least 2 players", resp);
    }


    private String toJsonString(JsonCard jsonCard) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(jsonCard);
    }

    private String toPlainText( String json ) throws JsonProcessingException {
        return new ObjectMapper().readTree( json ).asText();
    }

    private List<JsonCard> toJsonCardList(String json) throws JsonProcessingException{
        return new ObjectMapper().readValue( json, new TypeReference<List<JsonCard>>() {} );
    }

    private List<JsonCard> activeHand( String uuid ) throws Exception {
        return toJsonCardList( performRequestAndExpectOk( get( "/playerhand/" + uuid ) ) );

    }

    private String newGame() throws Exception {
        return toPlainText( performRequestAndExpectOk( post("/newmatch?players=Emilio&players=Julio" )));

    }

    private String postJsonAndExpectClientError( MockHttpServletRequestBuilder httpRequest, String content ) throws Exception {
        return mockMvc.perform( httpRequest
                        .contentType( MediaType.APPLICATION_JSON )
                        .content( content ) )
                        .andDo(print())
                        .andExpect( status().is4xxClientError() )
                        .andReturn()
                        .getResponse()
                        .getContentAsString();
    }

    private String performRequestAndExpectOk( MockHttpServletRequestBuilder httpRequest ) throws Exception {
        return mockMvc.perform( httpRequest )
                        .andExpect( status().isOk() )
                        .andReturn()
                        .getResponse()
                        .getContentAsString();
    }


}
