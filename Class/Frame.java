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

    int playerX;
    int playerY;

    Frame() {
        width = columns * tileSize;
        height = rows * tileSize;

        playerX = tileSize * columns / 2 - 32; 
        playerY = height - 110; 

       
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

        BulletTimer = new Timer(60, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (Bullet b : bullets) {
                    b.move();
                }
                
            }
        });
        BulletTimer.start();

        BlockTimer = new Timer(50, new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                for(BlockMath block : blockMath){
                    block.move();
                }
                
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
                if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
                    leftPressed = true;
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
                    rightPressed = true;
                } else if (e.getKeyCode() == KeyEvent.VK_SPACE || e.getKeyCode() == KeyEvent.VK_W) {
                    spacePressed = true;
                }
            }
        
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
                    leftPressed = false;
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
                    rightPressed = false;
                } else if (e.getKeyCode() == KeyEvent.VK_SPACE || e.getKeyCode() == KeyEvent.VK_W) {
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
    

    public void checkPosition(){
        for(Enemy enemy : enemies){
            if(enemy.getEnemyY() > 768){
                enemies.remove(enemy);
            }
        }
        for(Bullet bullet : bullets){
            if(bullet.getBulletY() < 0){
                bullets.remove(bullet);
            }
        }
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
       
    }

    private void move() {
        for (Enemy enemy : enemies) {
            enemy.move();
        }
       
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
            lastShotTime = now;
            
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
                    
                    if(enemy.getEnemyHealth() <= 0){
                        enemies.remove(j);
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
                
            }
        }

    }

    private void checkCollisionsTank(){
        for (int i = 0; i<enemies.size(); i++) {
            Enemy enemy = enemies.get(i);
            if (tanks.isHit(enemy)){
                tanks.healthSubtractionAfterCollisionWithTank(); 
                enemies.remove(enemy);
                
            } 
            else if(enemy.getEnemyY() > 768){
                tanks.damaged();
                enemies.remove(enemy);
                
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
        Graphics2D g2d = (Graphics2D) g;
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);

        g.setColor(Color.WHITE);
        g.drawString("Score: " + score, 10, 20);
        
        g.drawString("HP: "  + tanks.getTankHealth(), 10, 40);

        for (Enemy e : enemies) {
            e.draw(g);
        }

        for (Bullet bullet : bullets) {
            bullet.draw(g);
        }

        int barWidth = 380;
        int barHeight = 20;
        int barX = (512 - barWidth) / 2;
        int barY = 728;

        int currentBarWidth = (int) ((double) tanks.getTankHealth() / 1000 * barWidth);

        g2d.setColor(Color.DARK_GRAY);
        g2d.fillRect(barX, barY, barWidth, barHeight);

        g2d.setColor(Color.RED);
        g2d.fillRect(barX, barY, currentBarWidth, barHeight);

        g2d.setColor(Color.WHITE);
        g2d.drawRect(barX, barY, barWidth, barHeight);
        
        
    }

    // validasi game over.
    // reset game
    public void resetGame() {
        score = 0;
        targetScore = 20;

        tanks.resetTank();

        bullet.setidxBullet(0);

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

        leftPressed = false;
        rightPressed = false;
        spacePressed = false;

        tanks.setBounds(playerX, playerY - 8, 1200, 84);

    
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
