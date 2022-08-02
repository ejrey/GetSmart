package middleware;

import server.Question;
import server.Server;

import java.util.ArrayList;

public class BoardData {
    public enum ButtonState {
        LOCKED,
        UNLOCKED,
        ANSWERED,

    }
    public ButtonState buttonStates[][]; // Array of status of each button the area
    public ArrayList<ClientData> clients;

    public BoardData() {
        this.buttonStates = new ButtonState[6][5];
        this.clients = Server.GetClientsData();

        for(int column=0; column<6; column++)
            for(int row=0; row<5; row++)
                buttonStates[column][row] = ButtonState.UNLOCKED;

    }
}
