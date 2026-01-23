package fr.baptiiiiste.game;

import fr.baptiiiiste.game.entities.Card;
import fr.baptiiiiste.game.entities.ClassicCard;
import fr.baptiiiiste.game.entities.Player;
import fr.baptiiiiste.game.entities.WildCard;
import fr.baptiiiiste.game.enums.Color;
import fr.baptiiiiste.game.managers.CardManager;
import fr.baptiiiiste.game.managers.PlayerManager;
import fr.baptiiiiste.game.managers.TurnManager;
import fr.baptiiiiste.game.renderers.LogRenderer;
import fr.baptiiiiste.game.renderers.TerminalCardRenderer;

import java.io.IOException;

public class Game {

    private static final int INITIAL_HAND_SIZE = 7;

    private final CardManager cardManager;
    private final PlayerManager playerManager;
    private final TurnManager turnManager;
    private final TerminalCardRenderer terminalCardRenderer;

    public Game() {
        this.cardManager = new CardManager();
        this.playerManager = new PlayerManager();
        this.turnManager = new TurnManager();
        this.terminalCardRenderer = new TerminalCardRenderer();
    }

    private void init() {
        cardManager.createCards();
        cardManager.shuffleCards();

        playerManager.addPlayer(new Player("Baptiste"));
        playerManager.addPlayer(new Player("Vincent"));

        distributeCards();
        cardManager.discard(cardManager.drawCard());
        while (this.cardManager.getTopDiscardedCard() instanceof WildCard) {
            cardManager.discard(cardManager.drawCard());
        }
    }

    public void start() throws IOException {
        init();

        while (checkForWinner() == null) {
            playTurn();
        }

        Player winner = checkForWinner();
        LogRenderer.game("The game is over ! " + winner.getName() + " wins !");
    }

    private void playTurn() {
        clearConsole();

        Player currentPlayer = playerManager.getCurrentPlayer();
        Card topCard = cardManager.getTopDiscardedCard();

        displayTopCard(topCard);

        Card chosenCard = turnManager.askForCard(currentPlayer, topCard);

        if (chosenCard != null) {
            playCard(currentPlayer, chosenCard);
        } else {
            handleCardDraw(currentPlayer, topCard);
        }
    }

    private void displayTopCard(Card topCard) {
        LogRenderer.game("Top card:");
        terminalCardRenderer.drawCard(topCard);
    }

    private void playCard(Player player, Card card) {
        LogRenderer.game(player.getName() + " plays: ");
        this.terminalCardRenderer.drawCard(card);
        player.removeCard(card);
        cardManager.discard(card);
        applyCardEffect(card);
    }

    private void handleCardDraw(Player currentPlayer, Card topCard) {
        Card drawnCard = cardManager.drawCard();
        LogRenderer.game(currentPlayer.getName() + " draws a card.");
        currentPlayer.addCard(drawnCard);

        Card cardToPlay = turnManager.askToPlayDrawnCard(drawnCard, topCard);

        if (cardToPlay != null) {
            playCard(currentPlayer, cardToPlay);
        } else {
            LogRenderer.game(currentPlayer.getName() + " cannot play and skips their turn.");
            playerManager.nextPlayer();
        }
    }


    private Player checkForWinner() {
        return playerManager.getPlayers().stream()
                .filter(player -> player.getHand().isEmpty())
                .findFirst()
                .orElse(null);
    }

    private void distributeCards() {
        for (Player player : playerManager.getPlayers()) {
            for (int i = 0; i < INITIAL_HAND_SIZE; i++) {
                Card card = cardManager.drawCard();
                if (card != null) {
                    player.addCard(card);
                }
            }
        }
    }

    private void applyCardEffect(Card card) {
        if (card instanceof ClassicCard) {
            playerManager.nextPlayer();
        } else if (card instanceof WildCard wildCard) {
            applyWildCardEffect(wildCard);
        }
    }

    private void applyWildCardEffect(WildCard wildCard) {
        switch (wildCard.getAction()) {
            case SKIP -> handleSkip();
            case REVERSE -> handleReverse();
            case DRAW_TWO -> handleDrawTwo();
            case DRAW_FOUR -> handleDrawFour();
            case CHOOSE_COLOR -> handleChooseColor();
        }
    }

    private void handleSkip() {
        LogRenderer.game("Next player is skipped!");
        playerManager.nextPlayer();
        playerManager.nextPlayer();
    }

    private void handleReverse() {
        LogRenderer.game("Play direction is reversed!");
        playerManager.reverseDirection();
        if(playerManager.getPlayers().size() == 2) {
            playerManager.nextPlayer();
        }
        playerManager.nextPlayer();
    }

    private void handleDrawTwo() {
        playerManager.nextPlayer();
        LogRenderer.game(playerManager.getCurrentPlayer() + " draws 2 cards!");
        drawCardsForPlayer(playerManager.getCurrentPlayer(), 2);
        playerManager.nextPlayer();
    }

    private void handleDrawFour() {
        playerManager.nextPlayer();
        LogRenderer.game(playerManager.getCurrentPlayer() + " draws 4 cards!");
        drawCardsForPlayer(playerManager.getCurrentPlayer(), 4);
        Color askedColor = this.turnManager.askColor();
        this.cardManager.getTopDiscardedCard().setColor(askedColor);
        playerManager.nextPlayer();
    }

    private void handleChooseColor() {
        Color askedColor = this.turnManager.askColor();
        this.cardManager.getTopDiscardedCard().setColor(askedColor);
        playerManager.nextPlayer();
    }

    private void drawCardsForPlayer(Player player, int amount) {
        for (int i = 0; i < amount; i++) {
            Card drawnCard = cardManager.drawCard();
            if (drawnCard != null) {
                player.addCard(drawnCard);
            }
        }
    }

    private void clearConsole() {
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }

}
