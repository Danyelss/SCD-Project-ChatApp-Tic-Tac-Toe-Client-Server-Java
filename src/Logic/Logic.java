package Logic;

import Client.Client;
import GUI.ChatGameInterface;
import Server.Server;

import java.awt.*;

public class Logic {

    private Server server;
    private Client client;
    private String type = "none";

    private int[][] gameMatrix = {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}};

    private int totalMoves = 0;
    private int userPoints = 0;
    private int opponentPoints = 0;

    private ChatGameInterface myFrame;

    public Logic(Server server) {
        this.type = "server";
        this.server = server;

        this.myFrame = new ChatGameInterface(this);
    }

    public Logic(Client client) {
        this.type = "client";
        this.client = client;

        this.myFrame = new ChatGameInterface(this);
    }

    private void decode(String string) {
        switch (string.charAt(0)) {
            case '@':
                toChat(string.substring(1));
                break;
            case '$':
                decodeMove(string.substring(1));
                break;
            case '%':

                break;
        }
    }

    // Data transmission

    private void sendData(String string) {
        switch (type) {
            case "server":
                server.transmitData(string);
                break;
            case "client":
                client.transmitData(string);
                break;
        }
    }

    public void getData(String string) {
        decode(string);
    }

    // Chat

    public void fromChat(String string) {
        sendData("@" + string + "\n");
    }

    private void toChat(String string) {

        myFrame.textToChat(string);
    }

    // Joc

    public void fromGame(String string) {
        sendData("$" + string + "\n");
    }

    public boolean positionClicked(int i, int j) {

        if (gameMatrix[i][j] == 0) {
            return true;
        }

        return false;
    }

    public void fillPositionInMatrix(int i, int j, int user) {
        gameMatrix[i][j] = user;
        myFrame.buttonColor(i, j, user == 1 ? Color.GREEN : Color.ORANGE);
        totalMoves++;

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (totalMoves == 9) {
            decideWinner();
        }
    }

    private void decodeMove(String string) {
        int i = Character.getNumericValue(string.charAt(0));
        int j = Character.getNumericValue(string.charAt(1));

        fillPositionInMatrix(i, j, 2);
    }

    private void decideWinner() {
        int winner = 0;

        for (int i = 0; i < 3; i++) {
            if (gameMatrix[i][0] == gameMatrix[i][1] && gameMatrix[i][0] == gameMatrix[i][2] ||
                    gameMatrix[0][i] == gameMatrix[1][i] && gameMatrix[0][i] == gameMatrix[2][i]) {

                if (gameMatrix[i][0] == gameMatrix[i][1] && gameMatrix[i][0] == gameMatrix[i][2])
                    winner = gameMatrix[i][0];

                if (gameMatrix[0][i] == gameMatrix[1][i] && gameMatrix[0][i] == gameMatrix[2][i])
                    winner = gameMatrix[0][i];

                displayWinner(winner);

                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                newGame();

                return;
            }
        }

        if (gameMatrix[0][0] == gameMatrix[1][1] && gameMatrix[0][0] == gameMatrix[2][2] ||
                gameMatrix[0][2] == gameMatrix[1][1] && gameMatrix[0][2] == gameMatrix[2][0]) {

            if (gameMatrix[0][0] == gameMatrix[1][1] && gameMatrix[0][0] == gameMatrix[2][2])
                winner = gameMatrix[0][0];

            if (gameMatrix[0][2] == gameMatrix[1][1] && gameMatrix[0][2] == gameMatrix[2][0])
                winner = gameMatrix[0][0];

            displayWinner(winner);

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            newGame();
            return;
        }

        if (winner == 0) {
            displayWinner(0);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            newGame();
        }
    }

    private void newGame() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                fillPositionInMatrix(i, j, 0);
                myFrame.buttonColor(i, j, Color.WHITE);
                totalMoves = 0;
            }
        }
    }

    private void displayWinner(int user) {
        if (user == 0) {
            myFrame.setInformationalLabel("Draw!");
        }
        if (user == 1) {
            myFrame.setInformationalLabel("You win!");
            userPoints++;
        }
        if (user == 2) {
            myFrame.setInformationalLabel("You lose!");
            opponentPoints++;
        }

        myFrame.setScoreLabel(opponentPoints + " - " + userPoints);

    }
}

