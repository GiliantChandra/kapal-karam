import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class Pause extends JPanel {
    JFrame pauseFrame = new JFrame("Pause Frame");
    BufferedImage Display;
    boolean pauseDisplay = true;

    Menu menu = new Menu();

    public Pause(PauseListener listener) {
        pauseFrame.setUndecorated(true);
        pauseFrame.setBackground(new Color(0, 0, 0, 0));

        pauseFrame.setSize(416, 384);
        pauseFrame.setLocationRelativeTo(null);
        pauseFrame.setContentPane(this);
        this.setOpaque(false);
        this.setLayout(new BorderLayout());

        try {
            Display = ImageIO.read(new File("assets/PNG/Shop/Prise_BTN_Table.png"));
        } catch (Exception e) {
            System.err.println("Error loading background: " + e.getMessage());
        }

        JButton playAgainButton = menu.createImageButton(
            "assets/PNG/Buttons/BTNs/Play_BTN.png",
            "assets/PNG/Buttons/BTNs_Active/Play_BTN.png", 75, 75
        );

        JButton quitButton = menu.createImageButton(
            "assets/PNG/Buttons/BTNs/Close_BTN.png",
            "assets/PNG/Buttons/BTNs_Active/Replay_BTN.png", 75, 75
        );

        JButton replayButton = menu.createImageButton(
            "assets/PNG/Buttons/BTNs/Replay_BTN.png",
            "assets/PNG/Buttons/BTNs_Active/Replay_BTN.png", 75, 75
        );

        playAgainButton.addActionListener(e -> {
            pauseDisplay = false;
            pauseFrame.dispose();
            listener.onResume(); // Notify frame to resume
        });

        quitButton.addActionListener(e -> System.exit(0));

        replayButton.addActionListener(e -> {
            pauseFrame.dispose();
            listener.onReplay();

        });



        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.add(replayButton);
        buttonPanel.add(playAgainButton);
        buttonPanel.add(quitButton);
        this.add(buttonPanel, BorderLayout.SOUTH);

        pauseFrame.setVisible(true);
        repaint();
    }

    public interface PauseListener {
        void onResume();
        void onReplay();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (Display != null) {
            g.drawImage(Display, 0, 50, getWidth(), getHeight(), this);

        }

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 48));
        g2d.drawString("Game Paused", 50, 190);


        
        

        
       
        
    }

    
}
