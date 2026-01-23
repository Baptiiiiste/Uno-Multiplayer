package fr.baptiiiiste.game.models;

import fr.baptiiiiste.game.enums.Color;

public abstract class Card {
    private Color color;

    public Card (Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color ){
        this.color = color;
    }

    public abstract boolean canPlayOn(Card other);
}
