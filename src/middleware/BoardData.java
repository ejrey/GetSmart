package middleware;

import server.Question;
import server.Server;

import java.util.ArrayList;
import java.util.Arrays;

public class BoardData {
    public enum ButtonState {
        LOCKED,
        UNLOCKED,
        ANSWERED,

    }
    public ButtonState buttonStates[][]; // Array of status of each button the area
    public ArrayList<ClientData> clients;
    public int column;
    public int row;

    public BoardData() {
        this.buttonStates = new ButtonState[6][5];
        this.clients = Server.GetClientsData();

        for(int column=0; column<6; column++)
            for(int row=0; row<5; row++)
                buttonStates[column][row] = ButtonState.UNLOCKED;

    }



    public void setColumn(int column) {
        this.column = column;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

    public ButtonState[][] getButtonStates() {
        return buttonStates;
    }

    public ArrayList<ClientData> getClients() {
        return clients;
    }

}
