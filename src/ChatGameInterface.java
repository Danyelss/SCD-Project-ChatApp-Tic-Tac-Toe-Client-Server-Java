import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

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
    private JTextArea textArea1;
    private JTextArea textArea2;

    public ChatGameInterface() {
        setContentPane(outterPanel);
        this.setTitle("Welcome");
        setSize(1100, 600);
        //mageIcon imgThisImg = new ImageIcon("src/logo.jpg");
        //imgThisImg.getImage().flush();
        //informationalLabel.setIcon(imgThisImg);
        informationalLabel.setText("");
        informationalLabel.setVisible(true);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    public static void main(String[] args) {
        ChatGameInterface myFrame = new ChatGameInterface();
    }
}

