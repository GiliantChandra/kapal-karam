import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class EndGame extends JPanel{
    private BufferedImage Menu;    
    private JFrame gameOverFrame;
    

    public EndGame(Runnable onRestart) {

        try {
            Menu = ImageIO.read(new File("assets/PNG/Setting/Window.png"));
        } catch (Exception e) {
            System.err.println("Error loading background: " + e.getMessage());
        }

        gameOverFrame = new JFrame("Game Over");
        gameOverFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameOverFrame.setSize(384, 512);
        gameOverFrame.setLocationRelativeTo(null); // Supaya di tengah layar

        // this.setBounds(0, 0, 384, 512);
        // gameOverFrame.add(this);

        gameOverFrame.setContentPane(this); 
        this.setLayout(new GridBagLayout());

        JLabel message = new JLabel("Game Over! Ingin bermain lagi?");
        message.setForeground(Color.WHITE); // style
        message.setFont(new Font("Arial", Font.BOLD, 20));

        JButton playAgainButton = new JButton("Play Again");
        JButton quitButton = new JButton("Quit");

        playAgainButton.addActionListener(e -> {
            gameOverFrame.dispose(); // Tutup window game over
            onRestart.run();
        });

        quitButton.addActionListener(e -> {
            System.exit(0); // Keluar dari game
        });

        // j panel for button is better.
        // gameOverFrame.add(message);
        // gameOverFrame.add(playAgainButton);
        // gameOverFrame.add(quitButton);
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(playAgainButton);
        buttonPanel.add(quitButton);
        
        gameOverFrame.setVisible(true);

        
        // try {
        //     Menu = ImageIO.read(new File("assets/PNG/Setting/Window.png"));
            
        // } catch (Exception e) {
        //     System.out.println("Error loading images: " + e.getMessage());
        // }

        // repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        if (Menu != null) {
            g2d.drawImage(Menu, 0, 0, getWidth(), getHeight(), null);
        }

        
    }
}

