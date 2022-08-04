package middleware;

import java.util.ArrayList;

public class BoardData {
    enum ButtonState {
        LOCKED,
        UNLOCKED,
        ANSWERED,
    }

    public ButtonState buttonStates[][]; // Array of status of each button the area
    public ArrayList<Player> players;
}
