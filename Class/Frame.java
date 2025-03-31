import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.*;
import java.util.Random;

import Bullet.Bullet;
import Enemy.Enemy;
//import Enemy.EnemyAbs;
import Enemy.EnemyFactory;


public class Frame extends JPanel implements ActionListener {
    private int tileSize = 32;
    private int rows = 24;
    private int columns = 16;
    private int width;
    private int height;
    Random random = new Random();

    private int score = 0;
    int targetScore = 20;
    
    Timer gameLoop;
    Timer enemySpawnTimer;
    Timer enemyMoveTimer;
    Timer BulletTimer;
    Timer BlockTimer;
    Timer blockSpawnTimer;
    JFrame frame = new JFrame("Ya main last war lah !!");

    TankAssembler tanks = new TankAssembler();
    private ArrayList<Enemy> enemies = new ArrayList<>();

    private ArrayList<Bullet> bullets = new ArrayList<>();
    Bullet bullet = new Bullet(tanks.tankX, tanks.tankY);

    BlockMath blockmath = new BlockMath(0, 0);
    private ArrayList<BlockMath> blockMath = new ArrayList<>();



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

        
        tanks.setBounds(playerX, playerY - 8, 1200, 84);
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

        BulletTimer = new Timer(50, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (Bullet b : bullets) {
                    b.move();
                }
                repaint();
            }
        });
        BulletTimer.start();

        BlockTimer = new Timer(50, new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                for(BlockMath block : blockMath){
                    block.move();
                }
                repaint();
            }
        });
        BlockTimer.start();

        blockSpawnTimer = new Timer(2000, new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                spawnBlock();
            }
        });
        blockSpawnTimer.start();



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
        int randomX = random.nextInt(width-64);
        int randomY = (random.nextInt(50)) - 300; 
        Enemy newEnemy = EnemyFactory.createEnemy();
        enemies.add(newEnemy);
        add(newEnemy);
        newEnemy.setBounds(randomX, randomY, 200, 200);
        repaint();
    }

    private void move() {
        for (Enemy enemy : enemies) {
            enemy.move();
        }
        repaint();
    }  

    public void spawnBullet () {
        Bullet newBullet = new Bullet(tanks.getTankX() , tanks.getTankY() - 22);
        newBullet.setidxBullet(upgradeBullet());
        bullets.add(newBullet);
        add(newBullet);
        newBullet.setBounds(tanks.getTankX(), tanks.getTankY() - 30, 64, 64);
        
        repaint();
    }

    public void spawnBlock(){
        BlockMath newBlockMathLeft = new BlockMath(0, 0);
        blockMath.add(newBlockMathLeft);
        BlockMath newBlockMathRight = new BlockMath(256, 0);
        blockMath.add(newBlockMathRight);
        add(newBlockMathLeft);
        add(newBlockMathRight);
        newBlockMathLeft.setBounds(0, 0 , 250, 100);
        newBlockMathRight.setBounds(0, 0 , 256, 100);


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        upgradeBullet();
        checkCollisions();
        checkCollisionsTank();
        repaint();
    }

    private void checkCollisions() {
        for (int i = 0; i < bullets.size(); i++) {
            Bullet bullet = bullets.get(i);
            for (int j = 0; j < enemies.size(); j++) {
                Enemy enemy = enemies.get(j);
                if (enemy.isHit(bullet)) {
                    bullets.remove(i);
                    enemy.setEnemyHealth(bullet.getBulletDamage());
                    remove(bullet);
                    
                    if(enemy.getEnemyHealth() <= 0){
                        enemies.remove(j);
                        remove(enemy);
                        score += 10;
                    }
                    break;
                }
            }
        }

        for (int i = 0; i < blockMath.size(); i++) {
            BlockMath block = blockMath.get(i);
            if (block.isHit(tanks)) {  
                switch(block.opr0){
                    case "+":
                        score += block.getValue(); 
                        break;
                    case "-":
                        score -= block.getValue();  
                        if(score <= 0){
                            score = 0;
                        }
                        break;
                }
                remove(block);
                blockMath.remove(i);
                repaint();
            }
        }

    }

    private void checkCollisionsTank(){
        for (int i = 0; i<enemies.size(); i++) {
            Enemy enemy = enemies.get(i);
            if (tanks.isHit(enemy)){
                // game over
                tanks.healthSubtractionAfterCollisionWithTank(); 
                remove(enemy);
                enemies.remove(enemy);
                repaint();
            } 
            else if(enemy.getEnemyY() > 768){
                tanks.damaged();
                remove(enemy);
                enemies.remove(enemy);
                repaint();
            }  
        }
    }

    

    
    public int upgradeBullet(){
        if(score > targetScore){
            bullet.incrementIdxBullet();
            targetScore *= 2;
        }
        return bullet.getidxBullet();
    }
    

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.WHITE);
        g.drawString("Score: " + score, 10, 20);
        
        g.drawString("HP: "  + tanks.getTankHealth(), 10, 40);
    }

    // validasi game over.

}
