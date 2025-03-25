package Enemy;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import Bullet.Bullet;

public class Enemy extends JPanel{
    BufferedImage enemyImg;
    protected int x, y;
    private int idxEnemy = (int) (Math.random() * 6);
    EnemyAbs enemy;

    public int getEnemyX(){
        return this.x;
    }

    public int getEnemyY(){
        return this.y;
    }

    public int getEnemyWidth(){
        return enemy.enemyWidth;
    }

    public int getEnemyHeight(){
        return enemy.enemyHeight;
    }



    public Enemy(int RandomX, int y) {

        EnemyAbs[] enemyArray = new EnemyAbs[] {
            new blackThinMeteor(),
            new breadMeteor(),
            new whiteSlimMeteor(),
            new heavyMeteor(),
            new Crystal(),
            new Rocket()
        };

        enemy = enemyArray[idxEnemy];

        this.x = RandomX;
        this.y = y;
        setBounds(x, y, enemy.enemyWidth, enemy.enemyHeight);
        setOpaque(false);
        try {
            enemyImg = ImageIO.read(new File(enemy.enemyImg));  
        } catch (Exception e) {
            System.out.println("Error loading images: " + e.getMessage());
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        
        if (enemyImg != null) {
            g2d.drawImage(enemyImg, 0, 0, enemy.enemyWidth, enemy.enemyHeight, null);

            g2d.setColor(Color.RED);

            g2d.fillRoundRect(0, 0, enemy.health / 8, 8, 20, 20);
        }
    }


    public void move() {
        y += enemy.speed;
        setLocation(x, y);        
    }

    public boolean isHit(Bullet bullet) {
        return x < bullet.getBulletX() + bullet.getBulletWidth() &&
               x + enemy.enemyWidth > bullet.getBulletX() &&
               y < bullet.getBulletY() + bullet.getBulletHeight() &&
               y + enemy.enemyHeight > bullet.getBulletY();
    }

    

}