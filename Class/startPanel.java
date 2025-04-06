import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class startPanel extends JPanel{
    private JFrame frame;

    public startPanel(JFrame frame) {
        this.frame = frame;
        
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
        titleLabel.setBounds(100, 50, 300, 80);
        bgLabel.add(titleLabel);

        //start
        JButton startButton = new JButton(scaleIcon("assets/PNG/Main_Menu/Start_BTN.png", 200, 60));
        startButton.setBounds(156, 180, 200, 60);
        styleButton(startButton);
        startButton.addActionListener(e -> startGame());
        bgLabel.add(startButton);

        //setting
        JButton settingButton = new JButton(scaleIcon("assets/PNG/Main_Menu/Settings_BTN.png", 180, 50));
        settingButton.setBounds(166, 270, 180, 50);
        styleButton(settingButton);
        settingButton.addActionListener(e -> {
            //utk debug
            System.out.println("Setting clicked....");
        });
        bgLabel.add(settingButton);

        // Exit 
        JButton exitButton = new JButton(scaleIcon("assets/PNG/Main_Menu/Exit_BTN.png", 200, 50));
        exitButton.setBounds(156, 360, 200, 50);
        styleButton(exitButton);
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
        Frame gamePanel = new Frame();
        gamePanel.setPreferredSize(new Dimension(512, 753));
        frame.setContentPane(gamePanel);
        frame.pack(); // resize frame ikutin game panel
        frame.revalidate();
        gamePanel.requestFocusInWindow();
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
