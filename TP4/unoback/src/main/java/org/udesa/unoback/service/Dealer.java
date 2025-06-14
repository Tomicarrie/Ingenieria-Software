package org.udesa.unoback.service;


import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.udesa.unoback.model.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Component
public class Dealer {

    public List<Card> fullDeck() {
        ArrayList<Card> deck = new ArrayList<>();

        // generar cartas red
        deck.addAll(cardsOn("Red"));
        // generar cartas blue
        deck.addAll(cardsOn("Blue"));
        // generar cartas green
        deck.addAll(cardsOn("Green"));
        // generar cartas yellow
        deck.addAll(cardsOn("Yellow"));

        // mezclar las cartas
        Collections.shuffle(deck);

        return deck;
    }

    private List<Card> cardsOn(String color) {
        return List.of(new WildCard(),
                new SkipCard(color),
                new Draw2Card(color),
                new NumberCard(color, 1),
                new NumberCard(color, 2),
                new NumberCard(color, 3),
                new NumberCard(color, 4),
                new NumberCard(color, 5),
                new NumberCard(color, 6),
                new NumberCard(color, 7),
                new NumberCard(color, 8),
                new NumberCard(color, 9));
    }


}
