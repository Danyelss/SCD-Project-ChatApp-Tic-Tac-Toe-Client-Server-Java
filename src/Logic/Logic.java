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
    private boolean doIstart;

    public boolean isDoIstart() {
        return doIstart;
    }

    private ChatGameInterface userInterface;

    public Logic(Server server) {
        this.type = "server";
        this.server = server;
        this.doIstart = true;

        this.userInterface = new ChatGameInterface(this);

        new EndChecker().start();

    }

    public Logic(Client client) {
        this.type = "client";
        this.client = client;
        this.doIstart = false;

        this.userInterface = new ChatGameInterface(this);

        new EndChecker().start();

    }

    private void decode(String string) {
        switch (string.charAt(0)) {
            case '@':
                toChat(string.substring(1));
                break;
            case '$':
                decodeMove(string.substring(1));
                doIstart = true;
                break;

        }
    }

    // Data transmission

    private void sendData(String string) {
        switch (type) {
            case "server":
                server.getSender().transmitData(string);
                break;
            case "client":
                client.getSender().transmitData(string);
                break;
        }
    }

    public void getData(String string) {
        decode(string);
    }

    // Chat

    public void fromChat(String string) {
        sendData("@" + string);
    }

    private void toChat(String string) {

        userInterface.textToChat(string);
    }

    // Joc

    public void fromGame(String string) {
        sendData("$" + string);
        doIstart = false;
    }

    public boolean positionClicked(int i, int j) {

        if (gameMatrix[i][j] == 0) {
            return true;
        }

        return false;
    }

    public void fillPositionInMatrix(int i, int j, int user) {
        gameMatrix[i][j] = user;
        userInterface.buttonColor(i, j, user == 1 ? Color.GREEN : Color.ORANGE);
        totalMoves++;
    }

    private void decodeMove(String string) {
        int i = Character.getNumericValue(string.charAt(0));
        int j = Character.getNumericValue(string.charAt(1));

        fillPositionInMatrix(i, j, 2);
    }

    private void decideWinner() {

        int winner = -1;

        for (int i = 0; i < 3; i++) {
            if (gameMatrix[i][0] == gameMatrix[i][1] && gameMatrix[i][0] == gameMatrix[i][2] ||
                    gameMatrix[0][i] == gameMatrix[1][i] && gameMatrix[0][i] == gameMatrix[2][i]) {

                if ((gameMatrix[i][0] == gameMatrix[i][1] && gameMatrix[i][0] == gameMatrix[i][2])
                        && gameMatrix[i][0] != 0 && gameMatrix[i][1] != 0 && gameMatrix[i][2] != 0)
                    winner = gameMatrix[i][0];

                if ((gameMatrix[0][i] == gameMatrix[1][i] && gameMatrix[0][i] == gameMatrix[2][i])
                        && gameMatrix[0][i] != 0 && gameMatrix[1][i] != 0 && gameMatrix[2][i] != 0)
                    winner = gameMatrix[0][i];

                if (winner > 0) {
                    displayWinner(winner);

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    newGame();

                    return;
                }
            }
        }

        if (gameMatrix[0][0] == gameMatrix[1][1] && gameMatrix[0][0] == gameMatrix[2][2] ||
                gameMatrix[0][2] == gameMatrix[1][1] && gameMatrix[0][2] == gameMatrix[2][0]) {

            if ((gameMatrix[0][0] == gameMatrix[1][1] && gameMatrix[0][0] == gameMatrix[2][2])
                    && gameMatrix[0][0] != 0 && gameMatrix[1][1] != 0 && gameMatrix[2][2] != 0)
                winner = gameMatrix[0][0];

            if ((gameMatrix[0][2] == gameMatrix[1][1] && gameMatrix[0][2] == gameMatrix[2][0])
                    && gameMatrix[0][2] != 0 && gameMatrix[1][1] != 0 && gameMatrix[2][0] != 0)
                winner = gameMatrix[0][0];

            if (winner > 0) {

                displayWinner(winner);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                newGame();
                return;
            }
        }

        if (totalMoves == 9) {
            displayWinner(0);
            try {
                Thread.sleep(2500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            newGame();
        }
    }

    private void newGame() {

        if (doIstart) {
            userInterface.setInformationalLabel("Choose!");
        } else {
            userInterface.setInformationalLabel("Wait!");
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                fillPositionInMatrix(i, j, 0);
                userInterface.buttonColor(i, j, Color.WHITE);
                totalMoves = 0;

            }
        }
    }

    private void displayWinner(int user) {
        if (user == 0) {
            userInterface.setInformationalLabel("Draw!");
        }
        if (user == 1) {
            userInterface.setInformationalLabel("You win!");
            userPoints++;
        }
        if (user == 2) {
            userInterface.setInformationalLabel("You lose!");
            opponentPoints++;
        }

        userInterface.setScoreLabel(opponentPoints + " - " + userPoints);

    }

    private class EndChecker extends Thread {

        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (totalMoves > 4)
                    decideWinner();

                System.out.println("endChecker");
                System.out.println(gameMatrix[0][0] + " " + gameMatrix[0][1] + " " + gameMatrix[0][2]);
                System.out.println(gameMatrix[1][0] + " " + gameMatrix[1][1] + " " + gameMatrix[1][2]);
                System.out.println(gameMatrix[2][0] + " " + gameMatrix[2][1] + " " + gameMatrix[2][2]);
            }
        }
    }
}

