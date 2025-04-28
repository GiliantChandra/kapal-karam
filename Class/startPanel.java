import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class startPanel extends JPanel{
    private JFrame frame;
    public Frame gamePanel;

    public startPanel(JFrame frame) {
        this.frame = frame;

        Menu menu = new Menu();
        
        setLayout(null);

        //size window fixed
        int panelWidth = 512;
        int panelHeight = 768;

        //background
        ImageIcon bgIcon = new ImageIcon("assets/PNG/Main_Menu/BG.png");
        Image scaledBG = bgIcon.getImage().getScaledInstance(panelWidth, panelHeight, Image.SCALE_SMOOTH);
        JLabel bgLabel = new JLabel(new ImageIcon(scaledBG));
        bgLabel.setBounds(0, 0, panelWidth, panelHeight);
        add(bgLabel);

        //title
        ImageIcon title = new ImageIcon("assets/PNG/Main_Menu/Kapal-karam.png");
        Image scaledTitle = title.getImage().getScaledInstance(512, 512, Image.SCALE_SMOOTH);
        JLabel titleLabel = new JLabel(new ImageIcon(scaledTitle));
        titleLabel.setBounds(
                0, -100, 512, 512);
        bgLabel.add(titleLabel);

        //start
        JButton startButton = menu.createImageButton(
            "assets/PNG/Main_Menu/Start_BTN.png",
            "assets/PNG/Main_Menu/Start_BTN_Hover.png",
            256, 158
        );
        startButton.setBounds(126, 280, 256, 75);
        startButton.addActionListener(e -> startGame());
        bgLabel.add(startButton);


        // Exit 
        JButton exitButton = menu.createImageButton(
            "assets/PNG/Main_Menu/Exit_BTN.png",
            "assets/PNG/Main_Menu/Exit_BTN_Hover.png",
            256, 128
        );
        exitButton.setBounds(126, 385, 256, 75);
        exitButton.addActionListener(e -> System.exit(0));
        bgLabel.add(exitButton);

        setPreferredSize(new Dimension(panelWidth, panelHeight));
        setFocusable(true);
        requestFocusInWindow();

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    startGame();
                }
            }
        });

        // setPreferredSize(new Dimension(bgIcon.getIconWidth(), bgIcon.getIconHeight()));

    }

    private void startGame() {
        gamePanel = new Frame();
        gamePanel.setPreferredSize(new Dimension(512, 768));
        frame.setContentPane(gamePanel);
        frame.pack(); // resize frame ikutin game panel
        frame.revalidate();
        gamePanel.requestFocusInWindow();
    }

    public Frame getGamePanel(){
        return gamePanel;
    }

    
}
