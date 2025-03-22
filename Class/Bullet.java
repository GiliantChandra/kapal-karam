import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;     
import javax.imageio.ImageIO;

public class Bullet extends JPanel{
    public BufferedImage bulletImg;
    private int bulletX;
    private int bulletY;
    int bulletWidth = 64; // tilesize/8
    int bulletHeight = 64; //tilesize/2
    int bulletVelocityY = -10; //bullet moving speed
    private int idxBullet = 0;


    String[] BulletPath = {"assets/PNG/Effects/Plasma.png"};

    public int getBulletX() {
        return bulletX;
    }

    public int getBulletY() {
        return bulletY;
    }

    public int getBulletWidth() {
        return bulletWidth;
    }

    public int getBulletHeight() {
        return bulletHeight;
    }

    
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
        repaint();
    }

    @Override  
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        
        if (bulletImg != null) {
            g2d.drawImage(bulletImg, -10, 0, bulletWidth, bulletHeight, null);
        }
    }   
}
