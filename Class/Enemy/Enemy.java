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
    private boolean showHealthBar = false;


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
    
            if (showHealthBar) {
                int height;
                int maxHealthBarWidth = enemyType.getEnemyWidth() - 20;
                int healthBarWidth = (int) ((double) health / enemyType.getEnemyHealth() * maxHealthBarWidth);
                height = (enemyType.getEnemyWidth() >= 64) ? 10 : 8;
    
                int barX = x + 10;
                int barY = y - 10;
    
                g2d.setColor(Color.RED);
                g2d.fillRect(barX, barY, healthBarWidth, height);
    
                g2d.setColor(Color.WHITE);
                g2d.drawRect(barX, barY, maxHealthBarWidth, height);
            }
        }
    }

    public boolean isHit(Bullet bullet) {
        Rectangle enemyRect = new Rectangle(x, y, enemyType.getEnemyWidth(), enemyType.getEnemyHeight());
        Rectangle bulletRect = new Rectangle(bullet.getBulletX(), bullet.getBulletY(), bullet.getBulletWidth(), bullet.getBulletHeight());
    
        boolean hit = enemyRect.intersects(bulletRect);
        if (hit) showHealthBar = true;
    
        return hit;
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
