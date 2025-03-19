import java.awt.Color;
import java.awt.Graphics;

class Projectile {
    private int x, y;
    private int speed;

    public Projectile(int x, int y) {
        this.x = x;
        this.y = y;
        this.speed = 5; // Speed of the projectile
    }

    public void move() {
        y -= speed; // Move upward (player projectiles)
    }

    public void draw(Graphics g) {
        g.setColor(Color.YELLOW);
        g.fillRect(x, y, 5, 10); // Simple projectile design
    }

    public boolean collidesWith(Enemy enemy) {
        // Projectile bounding box
        int projectileLeft = x;
        int projectileRight = x + 5; // Projectile width = 5
        int projectileTop = y;
        int projectileBottom = y + 10; // Projectile height = 10
    
        // Enemy bounding box
        int enemyLeft = enemy.getX();
        int enemyRight = enemy.getX() + 30; // Enemy width = 30
        int enemyTop = enemy.getY();
        int enemyBottom = enemy.getY() + 30; // Enemy height = 30
    
        // Check for overlap
        return projectileRight > enemyLeft &&
               projectileLeft < enemyRight &&
               projectileBottom > enemyTop &&
               projectileTop < enemyBottom;
    }

    // Collision detection with Tank
    public boolean collidesWith(Tank tank) {
        // Projectile bounding box
        int projectileLeft = x;
        int projectileRight = x + 5; // Projectile width = 5
        int projectileTop = y;
        int projectileBottom = y + 10; // Projectile height = 10

        // Tank bounding box
        int tankLeft = tank.getX();
        int tankRight = tank.getX() + 50; // Tank width = 50
        int tankTop = tank.getY();
        int tankBottom = tank.getY() + 30; // Tank height = 30

        // Check for overlap
        return projectileRight > tankLeft &&
               projectileLeft < tankRight &&
               projectileBottom > tankTop &&
               projectileTop < tankBottom;
    }

    public boolean isOffScreen() {
        return y < 0; // Projectile is off-screen
    }
}