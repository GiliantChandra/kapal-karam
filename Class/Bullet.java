import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;     
import javax.imageio.ImageIO;

public class Bullet extends JPanel{
    public BufferedImage bulletImg;
    private int bulletX;
    private int bulletY;
    int bulletWidth = 20; // tilesize/8
    int bulletHeight = 30; //tilesize/2
    int bulletVelocityY = -1; //bullet moving speed
    private int idxBullet = 0;


    String[] BulletPath = {"assets/PNG/Effects/Heavy_Shell.png"};

    
    public Bullet(int bulletX, int bulletY) {
        this.bulletX = bulletX;
        this.bulletY = bulletY;
        setBounds(bulletX, bulletY, bulletWidth, bulletHeight);
        setOpaque(false);
        try {
            bulletImg = ImageIO.read(new File(BulletPath[idxBullet]));  
        } catch (Exception e) {
            System.out.println("Error loading images: " + e.getMessage());
        }
    }

    public void move () {
        bulletY += bulletVelocityY;
        setLocation(bulletX, bulletY);
        System.out.println("Bullet moving to: " + bulletX + ", " + bulletY); // debug
        repaint();
    }

    @Override  
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        
        if (bulletImg != null) {
            g2d.drawImage(bulletImg, 0, 0, bulletWidth, bulletHeight, null);
        }
    }   
}
