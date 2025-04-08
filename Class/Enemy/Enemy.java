package Enemy;

import java.io.File;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import Bullet.Bullet;

public class Enemy {
    private BufferedImage enemyImg;
    protected int x, y;
    protected EnemyAbs enemyType;
    private int health;

    public Enemy(int x, int y, EnemyAbs enemyType) {
        this.x = x;
        this.y = y;
        this.enemyType = enemyType;
        this.health = enemyType.getEnemyHealth();

        try {
            enemyImg = ImageIO.read(new File(enemyType.getEnemyImg()));  
        } catch (Exception e) {
            System.out.println("Error loading enemy image: " + e.getMessage());
        }
    }

    public void move() {
        y += enemyType.getEnemySpeed();
    }

    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        if (enemyImg != null) {
           
            g2d.drawImage(enemyImg, x, y, enemyType.getEnemyWidth(), enemyType.getEnemyHeight(), null);

            
            int maxHealthBarWidth = 50;
            int healthBarWidth = (int) ((double) health / enemyType.getEnemyHealth() * maxHealthBarWidth);

            g2d.setColor(Color.RED);
            g2d.fillRoundRect(x, y - 10, healthBarWidth, 6, 10, 10);  // Health bar di atas musuh
        }
    }

    public boolean isHit(Bullet bullet) {
        Rectangle enemyRect = new Rectangle(x, y, enemyType.getEnemyWidth(), enemyType.getEnemyHeight());
        Rectangle bulletRect = new Rectangle(bullet.getBulletX(), bullet.getBulletY(), bullet.getBulletWidth(), bullet.getBulletHeight());
        
        return enemyRect.intersects(bulletRect);
    }
    

    public void setEnemyHealth(int damage) {
        health -= damage;
    }

    
    public int getEnemyX() { return x; }
    public int getEnemyY() { return y; }
    public int getEnemyWidth() { return enemyType.getEnemyWidth(); }
    public int getEnemyHeight() { return enemyType.getEnemyHeight(); }
    public int getEnemyHealth() { return health; }
}
