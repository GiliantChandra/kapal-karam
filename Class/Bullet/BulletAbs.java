package Bullet;
import javax.swing.JPanel;

public abstract class BulletAbs extends JPanel {
    protected int x, y, width, height, speed;

    public BulletAbs(int x, int y, int width, int height, int speed) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speed = speed;
        setBounds(x, y, width, height);
        setOpaque(false);
    }

    public void move() {
        y -= speed;  
        setLocation(x, y);
    }

    public int getBulletX() {
         return x;
    }

    public int getBulletY() {
        return y; 
    }

    public int getBulletWidth() { 
        return width; 
    }

    public int getBulletHeight() {
         return height;
    }
}

