import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;



public class EndGame extends JPanel {
    private BufferedImage Menu, Score, Record, YouLose;
    private JFrame gameOverFrame;
    private int score, highscore;
    Menu menu = new Menu();
    
    

    public EndGame(Runnable onRestart , int score, int highscore) {

        this.score = score;
        this.highscore = highscore;
        // Load Background
        try {
            Menu = ImageIO.read(new File("assets/PNG/Setting/Window.png"));
            Score = ImageIO.read(new File("assets/PNG/You_Lose/Score.png"));
            Record = ImageIO.read(new File("assets/PNG/You_Lose/Record.png"));
            YouLose = ImageIO.read(new File("assets/PNG/You_Lose/Header.png"));

        } catch (Exception e) {
            System.err.println("Error loading background: " + e.getMessage());
        }

        gameOverFrame = new JFrame();
        gameOverFrame.setUndecorated(true); // Hilangkan title bar
        gameOverFrame.setBackground(new Color(0, 0, 0, 0));

        gameOverFrame.setSize(384, 512);
        gameOverFrame.setLocationRelativeTo(null);
        gameOverFrame.setContentPane(this);
        
        // Supaya latar belakang transparan
        this.setOpaque(false);
        this.setLayout(new BorderLayout());
        

        // Buat tombol dengan gambar
        JButton playAgainButton = menu.createImageButton("assets/PNG/Buttons/BTNs/Play_BTN.png","assets/PNG/Buttons/BTNs_Active/Play_BTN.png", 75, 75);
        JButton quitButton = menu.createImageButton("assets/PNG/Buttons/BTNs/Close_BTN.png","assets/PNG/Buttons/BTNs_Active/Close_BTN.png", 75, 75);
        JButton infoButton = menu.createImageButton("assets/PNG/Buttons/BTNs/Info_BTN.png","assets/PNG/Buttons/BTNs_Active/Info_BTN.png", 75, 75);
        

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
        buttonPanel.add(infoButton);
        buttonPanel.add(playAgainButton);
        buttonPanel.add(quitButton);
        
        this.add(buttonPanel, BorderLayout.SOUTH);

        gameOverFrame.setVisible(true);
        repaint(); 
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (Menu != null) {
            g.drawImage(Menu, 0, 0, getWidth(), getHeight(), this);
            g.drawImage(YouLose,90, 12 ,200, 28 , this);
            g.drawImage(Score,20, 125 , 150, 28, this);
            g.drawImage(Record,20, 250, 150, 28,  this);

        }

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);


        
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 32));
        g2d.drawString(String.valueOf(score), 210, 150);
        g2d.drawString(String.valueOf(highscore), 210, 275);

        
       
        
    }


    
    

}
