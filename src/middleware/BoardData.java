package middleware;

import java.util.ArrayList;

public class BoardData {
    enum ButtonState {
        LOCKED,
        UNLOCKED,
        ANSWERED,
    }

    public ButtonState buttonStates[][];
    public ArrayList<Player> players;

}
