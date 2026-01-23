package fr.baptiiiiste.game.entities;

import fr.baptiiiiste.game.enums.Action;
import fr.baptiiiiste.game.enums.Color;

public class WildCard extends Card {
    protected final Action action;

    public WildCard(Color color, Action action) {
        super(color);
        this.action = action;
    }

    @Override
    public boolean canPlayOn(Card other) {
        if (this.getColor().equals(Color.WILD)) return true;

        if (other instanceof WildCard wildCard) {
            return this.action == wildCard.action ||
                   this.getColor() == wildCard.getColor();
        }

        return this.getColor() == other.getColor();
    }

    public Action getAction() {
        return action;
    }
}

