import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;



public class Enemy extends JPanel{
    private BufferedImage enemyImg;
    private int health = 500;
    private int enemyX;
    private int enemyY;
    private int enemyWidth = 64;
    private int enemyHeight = 64;
    private int speed = 2;
    private int idxEnemy = (int) (Math.random() * 9);
    int RandomX, Y;

    public int getEnemyHealth(){
        return this.health;
    }

    public void setEnemyHealth(int subtraction){
        this.health = this.health + subtraction;
    }

    public int getEnemyX(){
        return this.enemyX;
    }

    public int getEnemyY(){
        return this.enemyY;
    }

    public int getEnemyHeight(){
        return this.enemyHeight;
    }

    public int getEnemyWidth(){
        return this.enemyWidth;
    }

    String[] enemyPath = {"assets/PNG/Meteors/Meteor_01.png",
                        "assets/PNG/Meteors/Meteor_02.png",
                        "assets/PNG/Meteors/Meteor_03.png",
                        "assets/PNG/Meteors/Meteor_04.png",
                        "assets/PNG/Meteors/Meteor_05.png",
                        "assets/PNG/Meteors/Meteor_06.png",
                        "assets/PNG/Meteors/Meteor_07.png",
                        "assets/PNG/Meteors/Meteor_08.png",
                        "assets/PNG/Meteors/Meteor_09.png"
                        };

    
    public Enemy(int RandomX, int Y) {
        this.enemyX = RandomX;
        this.enemyY = Y;
        setBounds(enemyX, enemyY, enemyWidth, enemyHeight);
        setOpaque(false);
        try {
            enemyImg = ImageIO.read(new File(enemyPath[idxEnemy]));  
        } catch (Exception e) {
            System.out.println("Error loading images: " + e.getMessage());
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        
        if (enemyImg != null) {
            g2d.drawImage(enemyImg, 0, 0, 64, 64, null);

            g2d.setColor(Color.RED);

            g2d.fillRoundRect(0, 0, health / 8, 8, 20, 20);
        }
    }

    public void move() {
        enemyY += speed;
        setLocation(enemyX, enemyY);        
    }

    public boolean isHit(Bullet bullet) {
        return enemyX < bullet.getBulletX() + bullet.getBulletWidth() &&
               enemyX + enemyWidth > bullet.getBulletX() &&
               enemyY < bullet.getBulletY() + bullet.getBulletHeight() &&
               enemyY + enemyHeight > bullet.getBulletY();
    }


}
