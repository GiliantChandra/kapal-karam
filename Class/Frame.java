import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
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
    private static int highscore = 0;

    private Image backgroundImage;
    private String[] latar = {"assets/PNG/Space Background (2).png", "assets/PNG/Main_UI/BG.png"};

    private boolean isPaused = false;
    private Pause pausePanel;


    Menu menu = new Menu();

    Random random = new Random();

    private int score = 0;
    int targetScore = 20;
    
    Timer gameLoop;
    Timer enemySpawnTimer;
    Timer enemyMoveTimer;
    Timer BulletTimer;
    Timer BlockTimer;
    Timer blockSpawnTimer;
    // JFrame frame = new JFrame("Ya main last war lah !!");

    TankAssembler tanks = new TankAssembler();
    private ArrayList<Enemy> enemies = new ArrayList<>();

    private ArrayList<Bullet> bullets = new ArrayList<>();
    Bullet bullet = new Bullet(tanks.tankX, tanks.tankY);

    BlockMath blockmath = new BlockMath(0, 0);
    private ArrayList<BlockMath> blockMath = new ArrayList<>();

    //for gerak sambil serang
    private boolean leftPressed = false;
    private boolean rightPressed = false;
    private boolean spacePressed = false;

    Frame() {
        width = columns * tileSize;
        height = rows * tileSize;

        int playerX = tileSize * columns / 2 - 32; 
        int playerY = height - 110; 

       
        backgroundImage = new ImageIcon(latar[0]).getImage();

        
        setLayout(null); 

        
        tanks.setBounds(playerX, playerY - 8, 1200, 84);
        add(tanks);

        gameLoop = new Timer(1000 / 60, this);
        gameLoop.start();

        // audio
        String audioPath = System.getProperty("user.dir") + "/assets/music/BGM.WAV";
        System.out.println("Loading audio from: " + audioPath); // Debug

        //load audio
        audioManager.loadSound("background", new File(audioPath).getAbsolutePath());
    

        //audio play
        audioManager.play("background", true);

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

        BulletTimer = new Timer(10, new ActionListener() {
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

        blockSpawnTimer = new Timer(10000, new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                spawnBlock();
            }
        });
        blockSpawnTimer.start();



        addKeyListener(new KeyAdapter() {
            @Override 
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    leftPressed = true;
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    rightPressed = true;
                } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    spacePressed = true;
                }
            }
        
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    leftPressed = false;
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    rightPressed = false;
                } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    spacePressed = false;
                }
            }
        });   

        

        JButton pauseButton = menu.createImageButton("assets/PNG/Buttons/BTNs/Pause_BTN.png", "assets/PNG/Buttons/BTNs_Active/Pause_BTN.png", 50, 50);
        pauseButton.setBounds(width - 60, 10, 50,50);
        add(pauseButton);
        pauseButton.setVisible(true);

        pauseButton.addActionListener(e -> {
            gameLoop.stop();
            enemySpawnTimer.stop();
            enemyMoveTimer.stop();
            BulletTimer.stop();
            BlockTimer.stop();
            blockSpawnTimer.stop();

            Pause pauseFrame = new Pause(() -> {
                
                gameLoop.start();
                enemySpawnTimer.start();
                enemyMoveTimer.start();
                BulletTimer.start();
                BlockTimer.start();
                blockSpawnTimer.start();

                requestFocusInWindow();
            });
            
            
        });

        




        // frame.setFocusable(true);
        // frame.requestFocus();

        setFocusable(true);
        requestFocusInWindow();
    }
    

    

    public void togglePause() {
        if (!isPaused) {
            gameLoop.stop();
            enemySpawnTimer.stop();
            enemyMoveTimer.stop();
            BulletTimer.stop();
            BlockTimer.stop();
            blockSpawnTimer.stop();
    
            isPaused = true;
    
            pausePanel = new Pause(() -> {
                togglePause(); // Kalau klik tombol resume
            });
        } else {
            gameLoop.start();
            enemySpawnTimer.start();
            enemyMoveTimer.start();
            BulletTimer.start();
            BlockTimer.start();
            blockSpawnTimer.start();
    
            isPaused = false;
    
            if (pausePanel != null) {
                pausePanel.pauseFrame.dispose();
            }
        }
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

    // utk bullet cooldown.
    private long lastShotTime = 0;
    private long shootingCooldown = 200; // milliseconds

    public void spawnBullet() {
        long now = System.currentTimeMillis();
        if (now - lastShotTime >= shootingCooldown) {
            Bullet newBullet = new Bullet(tanks.getTankX(), tanks.getTankY() - 22);
            newBullet.setidxBullet(upgradeBullet());
            bullets.add(newBullet);
            add(newBullet);
            newBullet.setBounds(tanks.getTankX(), tanks.getTankY() - 30, 64, 64);
            lastShotTime = now;
            repaint();
        }
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
        if (leftPressed) {
            tanks.moveLeft();
        }
        if (rightPressed) {
            tanks.moveRight();
        }
        if (spacePressed) {
            spawnBullet();
        }

        upgradeBullet();
        checkCollisions();
        checkCollisionsTank();
        checkLose();
        repaint();
        checkHighScore();
    }


    public void checkLose(){
        if(tanks.getTankHealth() <= 0){
            gameLoop.stop();
            enemySpawnTimer.stop();
            enemyMoveTimer.stop();
            BulletTimer.stop();
            BlockTimer.stop();
            blockSpawnTimer.stop();

            // new EndGame(this.score);
            // frame.setVisible(true);
            // resetGame();

            new EndGame(() -> {
                resetGame(); // Reset game state
                // frame.setVisible(true); // Show game window
            }, score, highscore);
        };
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
                        if(block.getValue() < 0){
                            score += Math.abs(block.getValue());
                        }
                        else{
                            score -= block.getValue();  
                        }                        
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
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);

        g.setColor(Color.WHITE);
        g.drawString("Score: " + score, 10, 20);
        
        g.drawString("HP: "  + tanks.getTankHealth(), 10, 40);
    }

    // validasi game over.
    // reset game
    public void resetGame() {
        score = 0;
        targetScore = 20;

        tanks.resetTank();

        bullet.setidxBullet(0);

        for (Enemy enemy : enemies) {
            remove(enemy);
        }
        for (Bullet bullet : bullets) {
            remove(bullet);
        }
        for (BlockMath block : blockMath) {
            remove(block);
        }
        enemies.clear();
        bullets.clear();
        blockMath.clear();

        gameLoop.start();
        enemySpawnTimer.start();
        enemyMoveTimer.start();
        BulletTimer.start();
        BlockTimer.start();
        blockSpawnTimer.start();

        repaint();
    }

    public void checkHighScore(){
        if(this.score > this.highscore){
            this.highscore = this.score;
        }
    }

    public int getScore() {
        return score;
    }

}
