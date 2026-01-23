package fr.baptiiiiste.game.models;

import fr.baptiiiiste.game.enums.Color;

public class ClassicCard extends Card {
    private final int value;

    public ClassicCard(Color color, int value) {
        super(color);
        this.value = value;
    }


    public int getValue() {
        return value;
    }

    @Override
    public boolean canPlayOn(Card other) {
        if (other instanceof ClassicCard classic) {
            return this.getColor() == classic.getColor() ||
                    this.value == classic.value;
        } else if(other instanceof  WildCard wildCard) {
            return this.getColor() == wildCard.getColor();
        }
        return false;
    }
}
