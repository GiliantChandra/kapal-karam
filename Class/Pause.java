import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
public class Pause extends JPanel{
    JFrame pauseFrame = new JFrame("Pause Frame");
    BufferedImage Display;

    Pause(){
        pauseFrame.setUndecorated(true); // Hilangkan title bar
        pauseFrame.setSize(416, 384);
        pauseFrame.setLocationRelativeTo(null);
        pauseFrame.setContentPane(this);
        
        // Supaya latar belakang transparan
        this.setOpaque(false);
        this.setLayout(new BorderLayout());

        try {
            Display = ImageIO.read(new File("assets/PNG/Shop/Prise_BTN_Table.png"));
        } catch (Exception e) {
            System.err.println("Error loading background: " + e.getMessage());
        }

        JButton playAgainButton = createImageButton("assets/PNG/Buttons/BTNs/Play_BTN.png","assets/PNG/Buttons/BTNs_Active/Play_BTN.png", 75, 75);
        JButton quitButton = createImageButton("assets/PNG/Buttons/BTNs/Close_BTN.png","assets/PNG/Buttons/BTNs_Active/Close_BTN.png", 75, 75);
        JButton infoButton = createImageButton("assets/PNG/Buttons/BTNs/Info_BTN.png","assets/PNG/Buttons/BTNs_Active/Info_BTN.png", 75, 75);
        

        // Tambahkan event listener
        playAgainButton.addActionListener(e -> {
            pauseFrame.dispose();
        });

        quitButton.addActionListener(e -> {
            System.exit(0);
        });

        // Panel untuk tombol
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.add(infoButton);
        buttonPanel.add(playAgainButton);
        buttonPanel.add(quitButton);
        
        this.add(buttonPanel, BorderLayout.SOUTH);

        pauseFrame.setVisible(true);
        repaint(); 
        
    }

    private JButton createImageButton(String normalImagePath, String hoverImagePath, int width, int height) {
    // Load gambar normal dan hover
    ImageIcon normalIcon = new ImageIcon(new ImageIcon(normalImagePath).getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
    ImageIcon hoverIcon = new ImageIcon(new ImageIcon(hoverImagePath).getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));

    JButton button = new JButton(normalIcon);
    button.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
    button.setBorderPainted(false);
    button.setContentAreaFilled(false);
    button.setFocusPainted(false);
    button.setOpaque(false);

    // Tambahkan efek hover
    button.addMouseListener(new java.awt.event.MouseAdapter() {
        @Override
        public void mouseEntered(java.awt.event.MouseEvent evt) {
            button.setIcon(hoverIcon); // Ubah ikon saat hover
        }

        @Override
        public void mouseExited(java.awt.event.MouseEvent evt) {
            button.setIcon(normalIcon); // Kembalikan ikon normal saat tidak hover
        }
    });

    return button;
}
}