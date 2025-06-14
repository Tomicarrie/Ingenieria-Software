package org.udesa.unoback.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.udesa.unoback.model.Card;
import org.udesa.unoback.model.JsonCard;
import org.udesa.unoback.model.Match;

import java.util.*;


@Service
public class UnoService {

    private Map<UUID, Match> sessions = new HashMap<UUID, Match>();
    @Autowired private Dealer dealer = new Dealer();

    public UUID newMatch(List<String> players) {
        UUID newKey = UUID.randomUUID();
        sessions.put( newKey, Match.fullMatch( dealer.fullDeck(), players));
        return newKey;
    }

    public List<Card> playerHand(UUID matchId) {
        Match match = getMatch(matchId);
        return match.playerHand();
    }

    public void play(UUID matchId, String player, JsonCard card) {
        Match match = getMatch(matchId);
        match.play(player, card.asCard());
    }

    public void drawCard(UUID matchId, String player) {
        Match match = getMatch(matchId);
        match.drawCard(player);
    }

    public JsonCard activeCard(UUID matchId) {
        Match match = getMatch(matchId);
        return match.activeCard().asJson();
    }
    

    private Match getMatch(UUID matchId) {
        Match match = sessions.get(matchId);
        if (match == null) {
            throw new IllegalArgumentException("Unexistent UUID");
        }
        return match;
    }

}
