import javax.swing.*;

//import Enemy.EnemyAbs;
import Enemy.Enemy;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class TankAssembler extends JPanel {
    private int health = 1000;
    private BufferedImage base, turret, track;
    private int frameWidth = 512;
    private int tankX = 216;
    private int tankY = 650;
    private int tankWidth = 40;
    private int tankHeight = 64;
    private int speed = 12;
    private int idx = 0;

    String[] BasePath = {"assets/PNG/Hulls_Color_D/Hull_08.png", 
                         "assets/PNG/Hulls_Color_D/Hull_07.png",
                         "assets/PNG/Hulls_Color_D/Hull_06.png",
                         "assets/PNG/Hulls_Color_D/Hull_01.png",
                         "assets/PNG/Hulls_Color_D/Hull_04.png",
                         "assets/PNG/Hulls_Color_D/Hull_03.png",
                         "assets/PNG/Hulls_Color_D/Hull_05.png",
                         "assets/PNG/Hulls_Color_D/Hull_02.png"};

    String[] turretPath = {"assets/PNG/Weapon_Color_D/Gun_08.png",
                           "assets/PNG/Weapon_Color_D/Gun_07.png",
                           "assets/PNG/Weapon_Color_D/Gun_06.png",
                           "assets/PNG/Weapon_Color_D/Gun_01.png",
                           "assets/PNG/Weapon_Color_D/Gun_04.png",
                           "assets/PNG/Weapon_Color_D/Gun_03.png",
                           "assets/PNG/Weapon_Color_D/Gun_05.png",
                           "assets/PNG/Weapon_Color_D/Gun_02.png"};

    String[] trackPath = {"assets/PNG/Tracks/Track_1_A.png",
                          "assets/PNG/Tracks/Track_1_B.png",
                          "assets/PNG/Tracks/Track_2_A.png",
                          "assets/PNG/Tracks/Track_2_B.png",
                          "assets/PNG/Tracks/Track_3_A.png",
                          "assets/PNG/Tracks/Track_3_B.png",
                          "assets/PNG/Tracks/Track_4_A.png",
                          "assets/PNG/Tracks/Track_4_B.png"};


    public TankAssembler() {
        setPreferredSize(new Dimension(tankWidth, tankHeight));
        setOpaque(false);
        loadTankImages();
    }

    private void loadTankImages() {
        try {
            base = ImageIO.read(new File(BasePath[idx]));
            turret = ImageIO.read(new File(turretPath[idx]));
            track = ImageIO.read(new File(trackPath[idx]));
        } catch (Exception e) {
            System.out.println("Error loading images: " + e.getMessage());
        }
    }
    

    public int getTankHealth(){
        return this.health;
    }

    public int getTankWidth(){
        return this.tankWidth;
    }

    public int getTankHeight(){
        return this.tankHeight;
    }

    public void healthSubtractionAfterCollisionWithTank(){
        this.health -= 1000;
        repaint();
    }

    public void damaged(){
        this.health -= 100;
        repaint();
    }

    public void setIdx(){
        this.idx++;
        if (idx >= BasePath.length) {
            idx = BasePath.length - 1; 
        }
        loadTankImages();  
              
    }

    public int getIdx(){
        return this.idx;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        
        if (track != null) {
            g2d.drawImage(track, 2, 0, 12, 64, null);
        }
        if (track != null) {
            g2d.drawImage(track, 27, 0, 12, 64, null);
        }
        if (base != null) {
            g2d.drawImage(base, -12, 0, 64, 64, null);
        }
        if (turret != null) {
            g2d.drawImage(turret, 5, -20, 32, 64, null);
        }

        
        
    }

    public int getTankX() {
        return tankX;
    }

    public int getTankY() {
        return tankY;
    }

    public void moveLeft() {
        if (tankX - speed >= 0) { 
            tankX -= speed;
            setLocation(tankX, tankY);
        }
    }

    public void moveRight() {
        if (tankX + speed + 40 <= frameWidth) { 
            tankX += speed;
            setLocation(tankX, tankY);
        }
    }

    public void setSpeedWhenBulletSpawn(){
        this.speed = 6;
    }
    public void setpSpeedWhenNotBulletSpawn(){
        this.speed = 12;
    }

    public boolean isHit(Enemy enemy) {
        return tankX < enemy.getEnemyX() + enemy.getEnemyWidth() &&
           tankX + tankWidth > enemy.getEnemyX() &&
           tankY < enemy.getEnemyY() + enemy.getEnemyHeight() &&
           tankY + tankHeight > enemy.getEnemyY();
    }

    public void resetTank() {
        this.health = 1000;
        this.tankX = 224;
        this.idx = 0;
        loadTankImages();
        setpSpeedWhenNotBulletSpawn();

        repaint();
    }
}
