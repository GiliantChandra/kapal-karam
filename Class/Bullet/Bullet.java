package Bullet;


import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;     
import javax.imageio.ImageIO;

public class Bullet extends JPanel{
    public BufferedImage bulletImg; 
    private int bulletX; 
    private int bulletY; 
    private int bulletWidth = 64; // tilesize/8 
    private int bulletHeight = 64; //tilesize/2 
    private int idxBullet = 0;    


    BulletAbs BulletType[] = {
                            new FlashA4() ,
                            new FlashA5(),
                            new fullBeam(),
                            new hollowBeam(),
                            new Laser(),
                            new Missile(),
                            new Plasma(),
                            new Shotgun(),
                            new Thruster()
                        };

    

    public int getidxBullet(){
        return this.idxBullet;
    }

    public void setidxBullet(int idx){
        this.idxBullet = idx;
        updateBulletImage();
    }

    public void incrementIdxBullet(){
        if(this.idxBullet < 8){
            this.idxBullet = this.idxBullet + 1;
        }
        
    }

    

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
    }

    public void updateBulletImage() {
        try {
            bulletImg = ImageIO.read(new File(BulletType[idxBullet].getImagePath));  
            repaint(); 
        } catch (Exception e) {
            System.out.println("Error loading bullet image: " + e.getMessage());
        }
    }
    

    public void move () {
        bulletY += BulletType[idxBullet].getspeed();
        setLocation(bulletX, bulletY);
        repaint();
    }

    @Override  
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        
        if (bulletImg != null) {
            g2d.drawImage(bulletImg, -10, 0, bulletWidth , bulletHeight, null);
        }
    }   
}

