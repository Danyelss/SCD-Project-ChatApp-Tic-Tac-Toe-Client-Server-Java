package GUI;

import Logic.Logic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChatGameInterface extends JFrame {

    private JPanel outterPanel;
    private JButton topLeftBtn;
    private JButton topMidBtn;
    private JButton topRightBtn;
    private JButton midRightBtn;
    private JButton midMidBtn;
    private JButton midLeftBtn;
    private JButton bottomRightBtn;
    private JButton bottomMidBtn;
    private JButton bottomLeftBtn;
    private JSplitPane customSeparator;
    private JPanel gamePanel;
    private JLabel informationalLabel;
    private JButton sendBtn;
    private JLabel scoreLabel;
    private JEditorPane ChatTextBoxReal;
    private JEditorPane messageTextBoxReal;
    private Logic logic;

    public ChatGameInterface(Logic logic) {
        this.logic = logic;

        setContentPane(outterPanel);
        this.setTitle("X and 0");
        setSize(1200, 600);

        informationalLabel.setText("");
        informationalLabel.setVisible(true);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        ActionsListeners();

    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    public void textToChat(String string) {

        ChatTextBoxReal.setText(ChatTextBoxReal.getText() + "Opponent: " + string + "\n");
    }

    // joc

    public void ActionsListeners() {

        // chat

        sendBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                ChatTextBoxReal.setText(ChatTextBoxReal.getText() + "Me: " + messageTextBoxReal.getText() + "\n");

                logic.fromChat(messageTextBoxReal.getText()); // functia care trimite pe client / server

                messageTextBoxReal.setText("");
            }
        });

        // joc

        topLeftBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (logic.positionClicked(0, 0) && logic.isDoIstart()) {
                    logic.fillPositionInMatrix(0, 0, 1);
                    logic.fromGame("00");
                }
            }
        });

        topMidBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (logic.positionClicked(0, 1) && logic.isDoIstart()) {
                    logic.fillPositionInMatrix(0, 1, 1);
                    logic.fromGame("01");
                }
            }
        });

        topRightBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (logic.positionClicked(0, 2) && logic.isDoIstart()) {
                    logic.fillPositionInMatrix(0, 2, 1);
                    logic.fromGame("02");
                }
            }
        });

        midLeftBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (logic.positionClicked(1, 0) && logic.isDoIstart()) {
                    logic.fillPositionInMatrix(1, 0, 1);
                    logic.fromGame("10");
                }
            }
        });

        midMidBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (logic.positionClicked(1, 1) && logic.isDoIstart()) {
                    logic.fillPositionInMatrix(1, 1, 1);
                    logic.fromGame("11");
                }
            }
        });

        midRightBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (logic.positionClicked(1, 2) && logic.isDoIstart()) {
                    logic.fillPositionInMatrix(1, 2, 1);
                    logic.fromGame("12");
                }
            }
        });

        bottomLeftBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (logic.positionClicked(2, 0) && logic.isDoIstart()) {
                    logic.fillPositionInMatrix(2, 0, 1);
                    logic.fromGame("20");
                }
            }
        });

        bottomMidBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (logic.positionClicked(2, 1) && logic.isDoIstart()) {
                    logic.fillPositionInMatrix(2, 1, 1);
                    logic.fromGame("21");
                }
            }
        });

        bottomRightBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (logic.positionClicked(2, 2) && logic.isDoIstart()) {
                    logic.fillPositionInMatrix(2, 2, 1);
                    logic.fromGame("22");
                }
            }
        });
    }

    public void buttonColor(int i, int j, Color color) {
        switch (i) {
            case 0:
                switch (j) {
                    case 0:
                        topLeftBtn.setBackground(color);
                        break;
                    case 1:
                        topMidBtn.setBackground(color);
                        break;
                    case 2:
                        topRightBtn.setBackground(color);
                        break;

                }
                break;
            case 1:
                switch (j) {
                    case 0:
                        midLeftBtn.setBackground(color);
                        break;
                    case 1:
                        midMidBtn.setBackground(color);
                        break;
                    case 2:
                        midRightBtn.setBackground(color);
                        break;
                }
                break;
            case 2:
                switch (j) {
                    case 0:
                        bottomLeftBtn.setBackground(color);
                        break;
                    case 1:
                        bottomMidBtn.setBackground(color);
                        break;
                    case 2:
                        bottomRightBtn.setBackground(color);
                        break;
                }
                break;
        }
    }

    public void setInformationalLabel(String string){

        informationalLabel.setText(string);
    }

    public void setScoreLabel(String string){

        scoreLabel.setText(string);
    }

}

