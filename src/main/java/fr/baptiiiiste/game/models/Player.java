package fr.baptiiiiste.game.models;

import fr.baptiiiiste.game.renderers.LogRenderer;
import fr.baptiiiiste.game.renderers.TerminalCardRenderer;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Player {

    private UUID id;
    private String name;
    private List<Card> hand;

    public Player(String name) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.hand = new ArrayList<>();
    }

    public List<Card> getHand() {
        return hand;
    }

    public String getName() {
        return name;
    }

    public void addCard(Card card) {
        hand.add(card);
    }

    public void removeCard(Card card) {
        hand.remove(card);
    }

    public void displayHand() {
        LogRenderer.info("=== " + name +"'s hand" + " (" + hand.size() + " cards) ===");

        TerminalCardRenderer renderer = new TerminalCardRenderer();

        String[][] allCards = new String[hand.size()][];
        for (int i = 0; i < hand.size(); i++) {
            allCards[i] = renderer.getCardLines(hand.get(i));
        }

        for (int line = 0; line < 5; line++) {
            for (int card = 0; card < allCards.length; card++) {
                System.out.print(allCards[card][line]);
                System.out.print("  ");
            }
            System.out.println();
        }

        for (int i = 0; i < hand.size(); i++) {
            System.out.print("   [" + i + "]   ");
            System.out.print("   ");
        }

        System.out.println();
    }
}
