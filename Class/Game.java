import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game extends JPanel implements ActionListener, KeyListener {
    private Tank playerTank;
    private Timer timer;
    private int score = 0;
    private List<Enemy> enemies = new ArrayList<>();
    private Random random = new Random();

    public Game() {
        playerTank = new Tank(100, 400);
        timer = new Timer(10, this);
        timer.start();
        addKeyListener(this);
        setFocusable(true);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        playerTank.draw(g);
        playerTank.drawProjectiles(g);
        for (Enemy enemy : enemies) {
            enemy.draw(g);
            enemy.drawProjectiles(g);
        }
        g.drawString("Score: " + score, 10, 20);
        g.drawString("Health: " + playerTank.getHealth(), 10, 40);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (playerTank.getHealth() <= 0) {
            timer.stop(); // Stop the game
            System.out.println("Game Over!");
            return;
        }

        score += 1; // Increment score

        // Spawn enemies randomly
        if (random.nextInt(100) < 2) { // 2% chance to spawn an enemy
            int type = random.nextInt(4); // Random enemy type
            int level = random.nextInt(2); // Random level (0 or 1)
            enemies.add(new Enemy(random.nextInt(800), 0, type, level));
        }

        // Update and remove enemies
        for (int i = enemies.size() - 1; i >= 0; i--) {
            Enemy enemy = enemies.get(i);
            enemy.move();
            if (enemy.isOffScreen() || enemy.isDestroyed()) enemies.remove(i);
        }

        // Enemy shooting
        for (Enemy enemy : enemies) {
            if (random.nextInt(100) < 2) { // 2% chance to shoot
                enemy.shoot();
            }
            enemy.updateProjectiles();
        }

        // Check for collisions between player projectiles and enemies
        for (int i = enemies.size() - 1; i >= 0; i--) {
            Enemy enemy = enemies.get(i);
            for (int j = playerTank.getProjectiles().size() - 1; j >= 0; j--) {
                Projectile p = playerTank.getProjectiles().get(j);
                if (p.collidesWith(enemy)) {
                    enemy.takeDamage(10); // Damage the enemy
                    playerTank.getProjectiles().remove(j); // Remove the projectile
                    if (enemy.isDestroyed()) {
                        enemies.remove(i); // Remove the enemy if destroyed
                        score += 10; // Increase score
                    }
                    break;
                }
            }
        }

        // Check for collisions between enemy projectiles and the player
        for (Enemy enemy : enemies) {
            for (int j = enemy.getProjectiles().size() - 1; j >= 0; j--) {
                Projectile p = enemy.getProjectiles().get(j);
                if (p.collidesWith(playerTank)) {
                    playerTank.takeDamage(10); // Damage the player
                    enemy.getProjectiles().remove(j); // Remove the projectile
                    break;
                }
            }
        }

        // Trigger upgrades
        if (score == 100) playerTank.upgradeCannon();
        if (score == 200) playerTank.upgradeHull();
        if (score == 300) playerTank.upgradeTracks();
        if (score == 400) playerTank.upgradeFire();

        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) playerTank.moveLeft();
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) playerTank.moveRight();
        if (e.getKeyCode() == KeyEvent.VK_SPACE) playerTank.shoot();
    }

    @Override
    public void keyReleased(KeyEvent e) {}
    @Override
    public void keyTyped(KeyEvent e) {}

    public static void main(String[] args) {
        JFrame frame = new JFrame("Tank Game");
        Game game = new Game();
        frame.add(game);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}