package fr.baptiiiiste.game.managers;

import fr.baptiiiiste.game.entities.Player;

import java.util.ArrayList;
import java.util.List;

public class PlayerManager {
    private List<Player> players;
    private int currentPlayerIndex;
    private boolean clockwise;

    public PlayerManager() {
        this.players = new ArrayList<>();
        this.currentPlayerIndex = 0;
        this.clockwise = true;
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    public void nextPlayer() {
        if (clockwise) {
            currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        } else {
            currentPlayerIndex = (currentPlayerIndex - 1 + players.size()) % players.size();
        }
    }

    public void reverseDirection() {
        clockwise = !clockwise;
    }

    public List<Player> getPlayers() {
        return players;
    }
}
