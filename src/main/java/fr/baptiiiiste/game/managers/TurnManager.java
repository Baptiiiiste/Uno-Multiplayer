package fr.baptiiiiste.game.managers;

import fr.baptiiiiste.game.entities.Card;
import fr.baptiiiiste.game.entities.Player;
import fr.baptiiiiste.game.enums.Color;
import fr.baptiiiiste.game.renderers.LogRenderer;
import fr.baptiiiiste.game.renderers.TerminalCardRenderer;

import java.util.Scanner;

public class TurnManager {

    private final Scanner scanner;
    private TerminalCardRenderer cardRenderer;

    public TurnManager() {
        this.scanner = new Scanner(System.in);
        this.cardRenderer = new TerminalCardRenderer();
    }

    public Card askForCard(Player player, Card topCard) {
        player.displayHand();

        LogRenderer.game("Choose a card (0-" + (player.getHand().size() - 1) + ") or -1 to draw : ");

        int choice = scanner.nextInt();

        if(choice == -1) {
            return null;
        }

        if (choice >= 0 && choice < player.getHand().size()) {
            Card chosenCard = player.getHand().get(choice);

            if (chosenCard.canPlayOn(topCard)) {
                return chosenCard;
            } else {
                LogRenderer.error("Invalid card !");
                return askForCard(player, topCard);
            }
        }

        LogRenderer.error("Invalid choice !");
        return askForCard(player, topCard);
    }

    public Card askToPlayDrawnCard(Card drawnCard, Card topCard) {
        LogRenderer.info("You drew: ");
        this.cardRenderer.drawCard(drawnCard);

        if (drawnCard.canPlayOn(topCard)) {
            LogRenderer.game("Do you want to play this card? (y/n)");
            String response = scanner.next();

            if (response.equalsIgnoreCase("y")) {
                return drawnCard;
            }
        }

        return null;
    }

    public Color askColor() {
        LogRenderer.game("Pick a color (RED, GREEN, BLUE, YELLOW): ");
        String colorInput = scanner.next().toUpperCase();

        try {
            return Color.valueOf(colorInput);
        } catch (IllegalArgumentException e) {
            LogRenderer.error("Invalid color !");
            return askColor();
        }
    }

}
