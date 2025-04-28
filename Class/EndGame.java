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

        infoButton.addActionListener(e -> {
            // Buat frame baru untuk info
           JFrame infoFrame = new JFrame();
           infoFrame.setUndecorated(true);
           infoFrame.setBackground(new Color(0, 0, 0, 0));
           infoFrame.setSize(384, 512);
           infoFrame.setLocationRelativeTo(gameOverFrame);
           
           JPanel infoPanel = new JPanel() {
               @Override
               protected void paintComponent(Graphics g) {
                   super.paintComponent(g);
                   try {
                       BufferedImage bgImage = ImageIO.read(new File("assets/PNG/Setting/Window.png"));
                       g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);

                       Graphics2D g2d = (Graphics2D) g;
                       g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                       g2d.setColor(Color.WHITE);
                       g2d.setFont(new Font("Arial", Font.BOLD, 24));
                       g2d.drawString("Informasi Game", 104, 35);
                       
                       g2d.setFont(new Font("Arial", Font.PLAIN, 16));
                       String[] infoText = {
                           "Cara Bermain:",
                           "• A atau <- untuk ke kiri",
                           "• D atau -> untuk ke kanan",
                           "• W atau spasi untuk menembak",
                           "• Hindari serangan",
                           "• Ambil math untuk menambah score",
                           "• Jangan sampai ada musuh yang melewati",
                           "  / menabrak tank",
                           "",
                           "• Dibuat oleh: Karl Ferdianan, Giliant Chandra",
                           "   , Leonardo Cendra, Venedion Uvandy",
                           "• Versi: 1.0(BETA)"
                       };
                       
                       int y = 100;
                       for (String line : infoText) {
                           g2d.drawString(line, 50, y);
                           y += 30;
                       }
                   } catch (Exception ex) {
                       System.err.println("Error loading info panel: " + ex.getMessage());
                   }
               }
            };
   
               infoPanel.setOpaque(false);
               infoPanel.setLayout(new BorderLayout());
               
               // Buat tombol untuk close info panel
               JButton closeInfoButton = menu.createImageButton(
                   "assets/PNG/Buttons/BTNs/Close_BTN.png",
                   "assets/PNG/Buttons/BTNs_Active/Close_BTN.png", 
                   75, 75
               );
               
               closeInfoButton.addActionListener(event -> {
                   infoFrame.dispose();
               });
   
               // Panel untuk tombol close
               JPanel closeButtonPanel = new JPanel();
               closeButtonPanel.setOpaque(false);
               closeButtonPanel.add(closeInfoButton);
               
               infoPanel.add(closeButtonPanel, BorderLayout.SOUTH);
               infoFrame.setContentPane(infoPanel);
               infoFrame.setVisible(true);
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
