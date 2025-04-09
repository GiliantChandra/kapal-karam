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
        ImageIcon title = new ImageIcon("assets/PNG/Main_Menu/Header.png");
        Image scaledTitle = title.getImage().getScaledInstance(300, 80, Image.SCALE_SMOOTH);
        JLabel titleLabel = new JLabel(new ImageIcon(scaledTitle));
        titleLabel.setBounds(106, 110, 300, 80);
        bgLabel.add(titleLabel);

        //start
        JButton startButton = menu.createImageButton(
            "assets/PNG/Main_Menu/Start_BTN.png",
            "assets/PNG/Main_Menu/Start_BTN_Hover.png",
            256, 178
        );
        startButton.setBounds(126, 280, 256, 158);
        startButton.addActionListener(e -> startGame());
        bgLabel.add(startButton);

        //setting
        JButton settingButton = menu.createImageButton(
            "assets/PNG/Main_Menu/Settings_BTN.png",
            "assets/PNG/Buttons/BTNs_Active/Settings_BTN.png",
            40, 40
        );
        settingButton.setBounds(452, 698, 40, 40);
        settingButton.addActionListener(e -> {
            System.out.println("Settings clicked....");
        });
        bgLabel.add(settingButton);

        // Exit 
        JButton exitButton = menu.createImageButton(
            "assets/PNG/Main_Menu/Exit_BTN.png",
            "assets/PNG/Main_Menu/Exit_BTN_Hover.png",
            256, 128
        );
        exitButton.setBounds(126, 385, 256, 128);
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
        gamePanel.setPreferredSize(new Dimension(512, 753));
        frame.setContentPane(gamePanel);
        frame.pack(); // resize frame ikutin game panel
        frame.revalidate();
        gamePanel.requestFocusInWindow();
    }

    public Frame getGamePanel(){
        return gamePanel;
    }

    private void styleButton(JButton button) {
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
    }

    private ImageIcon scaleIcon(String path, int width, int height) {
        ImageIcon icon = new ImageIcon(path);
        Image scaledImage = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }
}
