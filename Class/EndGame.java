import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class EndGame extends JPanel {
    private BufferedImage Menu;
    private JFrame gameOverFrame;

    public EndGame(Runnable onRestart) {
        // Load Background
        try {
            Menu = ImageIO.read(new File("assets/PNG/Setting/Window.png"));
        } catch (Exception e) {
            System.err.println("Error loading background: " + e.getMessage());
        }

        gameOverFrame = new JFrame();
        gameOverFrame.setUndecorated(true); // Hilangkan title bar
        gameOverFrame.setSize(384, 512);
        gameOverFrame.setLocationRelativeTo(null);
        gameOverFrame.setContentPane(this);
        
        // Supaya latar belakang transparan
        this.setOpaque(false);
        this.setLayout(new GridBagLayout());

        // Buat tombol dengan gambar
        JButton playAgainButton = createImageButton("assets/PNG/Buttons/BTNs/Play_BTN.png", 150, 150);
        JButton quitButton = createImageButton("assets/PNG/Buttons/BTNs/Quit_BTN.png", 150, 60);

        // Tambahkan event listener
        playAgainButton.addActionListener(e -> {
            gameOverFrame.dispose();
            onRestart.run();
        });

        quitButton.addActionListener(e -> {
            System.exit(0);
        });

        // Panel untuk tombol
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.add(playAgainButton);
        buttonPanel.add(quitButton);
        this.add(buttonPanel);

        gameOverFrame.setVisible(true);
        repaint(); 
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (Menu != null) {
            g.drawImage(Menu, 0, 0, getWidth(), getHeight(), this);
        }
    }

    private JButton createImageButton(String imagePath, int width, int height) {
        ImageIcon icon = new ImageIcon(imagePath);
        
        // Resize gambar sesuai ukuran tombol
        Image resizedImage = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(resizedImage);

        JButton button = new JButton(resizedIcon);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setOpaque(false);
        return button;
    }
}
