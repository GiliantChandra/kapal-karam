import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.*;

public class Frame extends JPanel implements ActionListener {
    private int tileSize = 32;
    private int rows = 24;
    private int columns = 16;
    private int width;
    private int height; 
    Timer gameLoop;
    Timer enemySpawnTimer;
    Timer enemyMoveTimer;
    Timer BulletTimer;
    JFrame frame = new JFrame("Ya main last war lah !!");

    TankAssembler tanks = new TankAssembler();
    private ArrayList<Enemy> enemies = new ArrayList<>();

    private ArrayList<Bullet> bullets = new ArrayList<>();
    Bullet bullet = new Bullet(tanks.tankX, tanks.tankY);


    Frame() {
        width = columns * tileSize;
        height = rows * tileSize;

        int playerX = tileSize * columns / 2 - 32; 
        int playerY = height - 110; 


        frame.setSize(width , height);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setBackground(Color.BLACK);
        setLayout(null); 

        
        tanks.setBounds(playerX, playerY, 40, 64);
        add(tanks);

        frame.add(this);
        setFocusable(true);
        requestFocusInWindow(); 
        frame.setVisible(true);

        gameLoop = new Timer(1000 / 60, this);
        gameLoop.start();

        enemySpawnTimer = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                spawnEnemy();
            }
        });
        enemySpawnTimer.start();


        enemyMoveTimer = new Timer(60, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                move();
            }
        });
        enemyMoveTimer.start();

        BulletTimer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (Bullet b : bullets) {
                    b.move();
                }
                repaint();
            }
        });
        BulletTimer.start();

        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    tanks.moveLeft();
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    tanks.moveRight();
                } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    spawnBullet();
                }
            }
        });

        frame.setFocusable(true);
        frame.requestFocus();
    }


    private void spawnEnemy() {
        int randomX = (int) (Math.random() * 512); 
        Enemy enemy = new Enemy(randomX, 0); 
        enemies.add(enemy);
        add(enemy);
        enemy.setBounds(randomX, 0, 64, 64);
        repaint();
    }

    private void move() {
        for (Enemy enemy : enemies) {
            enemy.move();
        }
        repaint();
    }  

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    public void spawnBullet () {
        Bullet newBullet = new Bullet(tanks.tankX + 10, tanks.tankY - 30);
        bullets.add(bullet);
        newBullet.setBounds(tanks.tankX + 10, tanks.tankY - 30, 20, 30);
        repaint();
    }
}
