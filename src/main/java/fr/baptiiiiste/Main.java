package fr.baptiiiiste;

import fr.baptiiiiste.game.Game;

public class Main {
    static void main() {

        try {
            Game game = new Game();
            game.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
