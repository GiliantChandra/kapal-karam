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
            "assets/PNG/Buttons/BTNs_Active/Close_BTN.png", 75, 75
        );

        JButton infoButton = menu.createImageButton(
            "assets/PNG/Buttons/BTNs/Info_BTN.png",
            "assets/PNG/Buttons/BTNs_Active/Info_BTN.png", 75, 75
        );

        playAgainButton.addActionListener(e -> {
            pauseDisplay = false;
            pauseFrame.dispose();
            listener.onResume(); // Notify frame to resume
        });

        quitButton.addActionListener(e -> System.exit(0));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.add(infoButton);
        buttonPanel.add(playAgainButton);
        buttonPanel.add(quitButton);
        this.add(buttonPanel, BorderLayout.SOUTH);

        pauseFrame.setVisible(true);
        repaint();
    }

    public interface PauseListener {
        void onResume();
    }
    
}
