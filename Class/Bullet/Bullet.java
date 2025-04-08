package Bullet;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class Bullet {
    private BufferedImage bulletImg; 
    private int bulletX; 
    private int bulletY; 
    private final int bulletWidth = 64;  // tilesize/8 
    private final int bulletHeight = 64; // tilesize/2 
    private int idxBullet = 0;    

    private BulletType[] BulletType = {
        new FlashA4(),
        new FlashA5(),
        new fullBeam(),
        new hollowBeam(),
        new Laser(),
        new Missile(),
        new Plasma(),
        new Shotgun(),
        new Thruster()
    };

    public Bullet(int bulletX, int bulletY) {
        this.bulletX = bulletX;
        this.bulletY = bulletY;
        updateBulletImage();
    }

    public void updateBulletImage() {
        try {
            bulletImg = ImageIO.read(new File(BulletType[idxBullet].getImagePath()));  
        } catch (Exception e) {
            System.out.println("Error loading bullet image: " + e.getMessage());
        }
    }

    public void move() {
        bulletY -= BulletType[idxBullet].getSpeed();
    }

    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        if (bulletImg != null) {
            g2d.drawImage(bulletImg, bulletX - 10, bulletY, bulletWidth, bulletHeight, null);
        }
    }

    // Getters & Setters
    public int getBulletX() { return bulletX; }
    public int getBulletY() { return bulletY; }
    public int getBulletWidth() { return bulletWidth; }
    public int getBulletHeight() { return bulletHeight; }

    public int getBulletDamage() {
        return BulletType[idxBullet].getDamage();
    }

    public int getidxBullet() {
        return this.idxBullet;
    }

    public void setidxBullet(int idx) {
        this.idxBullet = idx;
        updateBulletImage();
    }

    public void incrementIdxBullet() {
        if (this.idxBullet < BulletType.length - 1) {
            this.idxBullet += 1;
            updateBulletImage();
        }
    }
}
