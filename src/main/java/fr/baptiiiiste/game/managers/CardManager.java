package fr.baptiiiiste.game.managers;

import fr.baptiiiiste.game.entities.Card;
import fr.baptiiiiste.game.entities.ClassicCard;
import fr.baptiiiiste.game.entities.WildCard;
import fr.baptiiiiste.game.enums.Action;
import fr.baptiiiiste.game.enums.Color;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CardManager {

    private List<Card> drawPile;
    private List<Card> discardPile;

    public CardManager() {
        this.drawPile = new ArrayList<>();
        this.discardPile = new ArrayList<>();
    }

    public void createCards() {

        for (Color color : Color.values()) {
            if (color == Color.WILD) continue;
            this.drawPile.add(new ClassicCard(color, 0));

            for (int i = 1; i < 10; i++) {
                this.drawPile.add(new ClassicCard(color, i));
                this.drawPile.add(new ClassicCard(color, i));
            }

            for (int i = 0; i < 2; i++) {
                this.drawPile.add(new WildCard(color, Action.DRAW_TWO));
                this.drawPile.add(new WildCard(color, Action.REVERSE));
                this.drawPile.add(new WildCard(color, Action.SKIP));
            }
        }

        for (int i = 0; i < 4; i++) {
            this.drawPile.add(new WildCard(Color.WILD, Action.CHOOSE_COLOR));
            this.drawPile.add(new WildCard(Color.WILD, Action.DRAW_FOUR));
        }
    }

    public void shuffleCards() {
        Collections.shuffle(this.drawPile);
    }

    public Card drawCard() {
        if (this.drawPile.isEmpty()) {
            reshuffleDiscardPile();
        }
        return this.drawPile.isEmpty() ? null : this.drawPile.remove(0);
    }

    public void discard(Card card) {
        this.discardPile.add(card);
    }

    private void reshuffleDiscardPile() {
        this.drawPile.addAll(discardPile);
        this.discardPile.clear();
        shuffleCards();
    }

    public Card getTopDiscardedCard() {
        if (this.discardPile.isEmpty()) {
            return null;
        }
        return this.discardPile.get(this.discardPile.size() - 1);
    }

}
