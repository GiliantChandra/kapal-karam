// import java.awt.event.ActionEvent;
// import java.awt.event.ActionListener;
// import java.awt.*;
// import java.awt.event.KeyAdapter;
// import java.awt.event.KeyEvent;
// import java.util.ArrayList;
// import java.util.Iterator;
// import java.util.Random;

// import javax.swing.*;

// import Bullet.BulletType;  // Ganti Bullet ke BulletAbs (Abstract Class)
// import Bullet.Laser;       // Contoh peluru
// import Enemy.Enemy;
// import Enemy.EnemyFactory;

// public class Frame extends JPanel implements ActionListener {
//     private int tileSize = 32;
//     private int rows = 24;
//     private int columns = 16;
//     private int width;
//     private int height; 

//     private int score = 0;
//     private int targetScore = 20;
    
//     Timer gameLoop;
//     Timer enemySpawnTimer;
//     Timer enemyMoveTimer;
//     Timer BulletTimer;
//     JFrame frame = new JFrame("Ya main last war lah !!");

//     TankAssembler tanks = new TankAssembler();
//     private ArrayList<Enemy> enemies = new ArrayList<>();
//     private ArrayList<BulletType> bullets = new ArrayList<>();

//     private Random random = new Random(); // 🔴 Tambahkan Random untuk posisi spawn musuh

//     Frame() {
//         width = columns * tileSize;
//         height = rows * tileSize;

//         int playerX = tileSize * columns / 2 - 32; 
//         int playerY = height - 110; 

//         frame.setSize(width, height);
//         frame.setLocationRelativeTo(null);
//         frame.setResizable(false);
//         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

//         setBackground(Color.BLACK);
//         setLayout(null); 

//         tanks.setBounds(playerX, playerY - 8, 1200, 84);
//         add(tanks);

//         frame.add(this);
//         setFocusable(true);
//         requestFocusInWindow(); 
//         frame.setVisible(true);

//         gameLoop = new Timer(1000 / 60, this);
//         gameLoop.start();

//         enemySpawnTimer = new Timer(500, e -> spawnEnemy());
//         enemySpawnTimer.start();

//         enemyMoveTimer = new Timer(60, e -> moveEnemies());
//         enemyMoveTimer.start();

//         BulletTimer = new Timer(10, e -> moveBullets());
//         BulletTimer.start();

//         frame.addKeyListener(new KeyAdapter() {
//             @Override
//             public void keyPressed(KeyEvent e) {
//                 if (e.getKeyCode() == KeyEvent.VK_LEFT) {
//                     tanks.moveLeft();
//                 } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
//                     tanks.moveRight();
//                 } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
//                     spawnBullet();
//                 }
//             }
//         });

//         frame.setFocusable(true);
//         frame.requestFocus();
//     }

//     private void spawnEnemy() {
//         int randomX = random.nextInt(width - 64); // 🔴 Posisi spawn musuh di area layar
//         Enemy newEnemy = EnemyFactory.createEnemy();
//         newEnemy.setBounds(randomX, 0, 64, 64);
//         enemies.add(newEnemy);
//         add(newEnemy);
//         repaint();
//     }

//     private void moveEnemies() {
//         for (Enemy enemy : enemies) {
//             enemy.move();
//         }
//         repaint();
            
//     }
        

//     private void moveBullets() {
//         Iterator<BulletType> bulletIter = bullets.iterator();
//         while (bulletIter.hasNext()) {
//             BulletAbs bullet = bulletIter.next();
//             bullet.move();

//             if (bullet.getBulletY() < 0) {
//                 remove(bullet);
//                 bulletIter.remove();
//             }
//         }
//         repaint();
//     }

//     public void spawnBullet() {
//         BulletAbs newBullet = new Laser(tanks.getTankX(), tanks.getTankY() - 22); // 🔴 Gunakan subclass BulletAbs
//         bullets.add(newBullet);
//         add(newBullet);
//         newBullet.setBounds(tanks.getTankX(), tanks.getTankY() - 30, 64, 64);
//         repaint();
//     }

//     @Override
//     public void actionPerformed(ActionEvent e) {
//         upgradeBullet();
//         checkCollisions();
//         checkCollisionsTank();
//         repaint();
//     }

//     private void checkCollisions() {
//         Iterator<BulletAbs> bulletIter = bullets.iterator();
//         while (bulletIter.hasNext()) {
//             BulletAbs bullet = bulletIter.next();

//             Iterator<Enemy> enemyIter = enemies.iterator();
//             while (enemyIter.hasNext()) {
//                 Enemy enemy = enemyIter.next();
                
//                 if (enemy.isHit(bullet)) {
//                     remove(bullet);
//                     bulletIter.remove();

//                     remove(enemy);
//                     enemyIter.remove();

//                     score += 10;
//                     break;
//                 }
//             }
//         }
//     }

//     private void checkCollisionsTank() {
//         Iterator<Enemy> enemyIter = enemies.iterator();
//         while (enemyIter.hasNext()) {
//             Enemy enemy = enemyIter.next();

//             if (tanks.isHit(enemy)) {
//                 tanks.healthSubtractionAfterCollisionWithTank();
//                 remove(enemy);
//                 enemyIter.remove();
//             } else if (enemy.getEnemyY() > height) {
//                 tanks.damaged();
//                 remove(enemy);
//                 enemyIter.remove();
//             }
//         }
//     }

//     public int upgradeBullet() {
//         if (score > targetScore) {
//             targetScore *= 2;
//         }
//         return 0;
//     }

//     @Override
//     protected void paintComponent(Graphics g) {
//         super.paintComponent(g);
//         g.setColor(Color.WHITE);
//         g.drawString("Score: " + score, 10, 20);
//         g.drawString("HP: " + tanks.getTankHealth(), 10, 40);
//     }
// }
