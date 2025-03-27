package Enemy;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import Bullet.Bullet;

public class Enemy extends JPanel {
    private BufferedImage enemyImg;
    protected int x, y;
    protected EnemyAbs enemyType;

    public Enemy(int x, int y, EnemyAbs enemyType) {
        this.x = x;
        this.y = y;
        this.enemyType = enemyType;

        setBounds(x, y, enemyType.getEnemyWidth(), enemyType.getEnemyHeight());
        setOpaque(false);

        try {
            enemyImg = ImageIO.read(new File(enemyType.getEnemyImg()));  
        } catch (Exception e) {
            System.out.println("Error loading images: " + e.getMessage());
        }
    }

    public void move() {
        y += enemyType.getEnemySpeed();
        setLocation(x, y);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        if (enemyImg != null) {
            g2d.drawImage(enemyImg, 0, 0, enemyType.getEnemyWidth(), enemyType.getEnemyHeight(), null);
        }
    }


    public boolean isHit(Bullet bullet) {
        return x < bullet.getBulletX() + bullet.getBulletWidth() &&
               x + enemyType.getEnemyWidth() > bullet.getBulletX() &&
               y < bullet.getBulletY() + bullet.getBulletHeight() &&
               y + enemyType.getEnemyHeight() > bullet.getBulletY();
    }

    public int getEnemyX() { return x; }
    public int getEnemyY() { return y; }
    public int getEnemyWidth() { return enemyType.getEnemyWidth(); }
    public int getEnemyHeight() { return enemyType.getEnemyHeight(); }
}
