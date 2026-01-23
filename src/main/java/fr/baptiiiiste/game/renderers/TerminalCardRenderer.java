package fr.baptiiiiste.game.renderers;

import fr.baptiiiiste.game.models.Card;
import fr.baptiiiiste.game.models.ClassicCard;
import fr.baptiiiiste.game.models.WildCard;
import fr.baptiiiiste.game.enums.Color;

public class TerminalCardRenderer {

    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String BLUE = "\u001B[34m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String WHITE = "\u001B[37m";

    private String getWildCardSymbol(WildCard card) {
        return switch (card.getAction()) {
            case SKIP -> "🚫";
            case REVERSE -> "🔄";
            case DRAW_TWO -> "+2";
            case DRAW_FOUR -> "+4";
            case CHOOSE_COLOR -> "🌈";
            default -> "?";
        };
    }

    private String getColorCode(Color color) {
        return switch (color) {
            case RED -> RED;
            case BLUE -> BLUE;
            case GREEN -> GREEN;
            case YELLOW -> YELLOW;
            case WILD -> WHITE;
        };
    }

    private String getSymbol(Card card) {
        if (card instanceof ClassicCard classicCard) {
            String val = String.valueOf(classicCard.getValue());
            return val.length() == 1 ? val + " " : val;
        } else if (card instanceof WildCard wildCard) {
            return getWildCardSymbol(wildCard);
        }
        return "?";
    }

    public String[] getCardLines(Card card) {
        String color = getColorCode(card.getColor());
        String symbol = getSymbol(card);

        return new String[]{
                color + "┌────────┐" + RESET,
                color + "│        │" + RESET,
                color + "│   " + symbol + "   │" + RESET,
                color + "│        │" + RESET,
                color + "└────────┘" + RESET
        };
    }

    public void drawCard(Card card) {
        for (String line : getCardLines(card)) {
            System.out.println(line);
        }
    }
}
