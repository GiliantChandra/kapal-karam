import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;

import javax.swing.*;


import Bullet.Bullet;
import Enemy.Enemy;
//import Enemy.EnemyAbs;
import Enemy.EnemyFactory;

import java.awt.image.BufferedImage;



public class Frame extends JPanel implements ActionListener {
    private int tileSize = 32;
    private int rows = 24;
    private int columns = 16;
    private int width;
    private int height;
    private static int highscore = 0;
    
    private int enemySpawnInterval = 2500; 


    private Image backgroundImage; 
    private String[] latar = {"assets/PNG/Space Background (2).png", "assets/PNG/Main_UI/BG.png"};

    private boolean isPaused = false;
    private Pause pausePanel;


    Menu menu = new Menu();

    private int score = 0;
    int targetScore = 20;
    
    Timer gameLoop;
    Timer enemySpawnTimer;
    Timer blockSpawnTimer;
    // JFrame frame = new JFrame("Ya main last war lah !!");

    TankAssembler tanks = new TankAssembler();
    private ArrayList<Enemy> enemies = new ArrayList<>();

    private ArrayList<Bullet> bullets = new ArrayList<>();
    Bullet bullet = new Bullet(tanks.getTankX(), tanks.getTankY());

    private ArrayList<BlockMath> blockMath = new ArrayList<>();

    //for gerak sambil serang
    private boolean leftPressed = false;
    private boolean rightPressed = false;
    private boolean spacePressed = false;

    int playerX;
    int playerY;

    
    // utk explosion
    private ArrayList<Explosion> explosions = new ArrayList<>();
    private ArrayList<Explosion> explosionsBase = new ArrayList<>();
    private ArrayList<Explosion> levelUp = new ArrayList<>();

    private BufferedImage[] explosionFrames;
    private BufferedImage[] explosionBase;
    private BufferedImage[] LevelUp;


    

    Frame() {
        width = columns * tileSize;
        height = rows * tileSize;

        playerX = tileSize * columns / 2 - 40; 
        playerY = height - 118; 

       
        backgroundImage = new ImageIcon(latar[0]).getImage();

        audioManager.loadSound("enemydead", new File("assets/music/explosion-312361 (online-audio-converter.com).wav").getAbsolutePath());
        audioManager.loadSound("explode", new File("assets/music/a-bomb-139689 (online-audio-converter.com).wav").getAbsolutePath());
        audioManager.loadSound("lepelup", new File("assets/music/electric-impact-37128 (online-audio-converter.com).wav").getAbsolutePath());
        
        setLayout(null); 

        
        tanks.setBounds(playerX, playerY, 40, 64);
        add(tanks);

        
        // audio
        String audioPath = "assets/music/video-game-tank-metal-220562 (online-audio-converter.com).wav";
        System.out.println("Loading audio from: " + audioPath); // Debug

        //load audio
        audioManager.loadSound("background", new File(audioPath).getAbsolutePath());
    

        //audio play
        audioManager.play("background", true);

        gameLoop = new Timer(1000 / 60, this);
        gameLoop.start();

        // explosion
        explosionFrames = loadExplosionFrames();
        explosionBase = loadExplosionBase();
        LevelUp = loadLevelUp();


        enemySpawnTimer = new Timer(enemySpawnInterval, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                spawnEnemy();
                System.out.println("Spawning enemy, with interval : " + enemySpawnInterval);
            }
        });
        enemySpawnTimer.start();
        spawnEnemy();


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
            togglePause();
            leftPressed = false;
            rightPressed = false;
            spacePressed = false;
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
            tanks.setSpeedWhenBulletSpawn();
        }
        else if(!spacePressed){
            tanks.setpSpeedWhenNotBulletSpawn();
        }

        upgradeBullet(); 

        for (Bullet b : bullets) {
            b.move();
        }

        for (Enemy enemy : enemies) {
            enemy.move();
        }

        for (BlockMath block : blockMath) {
            block.move();
        }

    // Deteksi tabrakan
    checkCollisions();
    checkCollisionsTank();
    checkLose();
    checkHighScore();
    checkPosition();
    

    repaint();
}


   

    

    public void checkPosition(){
        for (int i = enemies.size() - 1; i >= 0; i--) {
            Enemy enemy = enemies.get(i);
            if (enemy.getEnemyY() > 660) {
                enemies.remove(i);
                tanks.damaged();
                Explosion explosion = new Explosion(enemy.getEnemyX() + (enemy.getEnemyWidth()/2) - 300, 400, 400, 600 ,explosionBase);

    

                //audio play
                audioManager.play("explode", false);
                explosionsBase.add(explosion); 
            }
        }

        for (int i = bullets.size() - 1; i >= 0; i--) {
            Bullet bullet = bullets.get(i);
            if (bullet.getBulletY() < 0) {
                bullets.remove(i);
            }
        }

        for(int i = 0 ; i<blockMath.size(); i++){
            BlockMath block = blockMath.get(i);
            if(block.getBlockY() > 768){
                blockMath.remove(i);
            }
        }
    }


    private void adjustEnemySpawnRate() {
        int newInterval = 2500 - (score / 50) * 200; 
        if (newInterval < 500) {
            newInterval = 500; 
        }
    
        if (newInterval != enemySpawnInterval) {
            enemySpawnInterval = newInterval;
            enemySpawnTimer.setDelay(enemySpawnInterval);
        }
    }
    
    

    public void togglePause() {
        if (!isPaused) {
            gameLoop.stop();
            enemySpawnTimer.stop();
            blockSpawnTimer.stop();
    
            isPaused = true;
    
            pausePanel = new Pause(new Pause.PauseListener() {
                @Override
                public void onResume() {
                    togglePause();
                    requestFocusInWindow();
                }
    
                @Override
                public void onReplay() {
                    resetGame();
                    togglePause();
                    requestFocusInWindow();
                }
            });
        } else {
            gameLoop.start();
            enemySpawnTimer.start();
            blockSpawnTimer.start();
    
            isPaused = false;
    
            if (pausePanel != null) {
                pausePanel.pauseFrame.dispose();
            }
    
            requestFocusInWindow();
        }
    }
    
    


    private void spawnEnemy() { 
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
            Bullet newBullet = new Bullet(tanks.getTankX(), tanks.getTankY());
            
            String audioPath = "assets/music/laser-45816 (online-audio-converter.com).wav";

            //load audio
            audioManager.loadSound("shoot", new File(audioPath).getAbsolutePath());
    

            //audio play
            audioManager.play("shoot", false);

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
        

    }

    


    public void checkLose(){
        if(tanks.getTankHealth() <= 0){
            gameLoop.stop();
            enemySpawnTimer.stop();
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
                        // explosion
                        int explosionWidth = 200;
                        int explosionHeight = 200;

                        int centerX = enemy.getEnemyX() + (enemy.getEnemyWidth() / 2);
                        int centerY = enemy.getEnemyY() + (enemy.getEnemyHeight() / 2);

                        int explosionX = centerX - (explosionWidth / 2);
                        int explosionY = centerY - (explosionHeight / 2);

                        Explosion explosion = new Explosion(explosionX, explosionY, explosionWidth, explosionHeight, explosionFrames);
                        explosions.add(explosion); 
                

                        //audio play
                        audioManager.play("enemydead", false);

                        //
                        enemies.remove(j);
                        score += 10;
                        adjustEnemySpawnRate();
                    }
                    break;
                }
            }
        }
        


        for (int i = 0; i < blockMath.size(); i++) {
            BlockMath block = blockMath.get(i);
            if (!block.isCollided() && block.isHit(tanks)) {  
                block.setCollided(true);
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
                for(int j = 0; j< blockMath.size(); j++){
                    if(blockMath.get(j) != block){
                        remove(blockMath.get(j));
                        blockMath.remove(j);
                    }
                }
                adjustEnemySpawnRate();
            
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
            Explosion levelup = new Explosion(tanks.getX()+20-250, 650+32-250, 500, 500 ,LevelUp);
            
            //audio play
            audioManager.play("lepelup", false);

            levelUp.add(levelup); 
            bullet.incrementIdxBullet();
            targetScore *= 2;

            tanks.setIdx();
            
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

        // explosion
        for (int i = 0; i < explosions.size(); i++) {
            Explosion exp = explosions.get(i);
            exp.draw(g);
            if (exp.isFinished()) {
                explosions.remove(i);
                i--; 
            }
        }

        for (int i = 0; i < explosionsBase.size(); i++) {
            Explosion exp = explosionsBase.get(i);
            exp.draw(g);
            if (exp.isFinished()) {
                explosionsBase.remove(i);
                i--; 
            }
        }

        for (int i = 0; i < levelUp.size(); i++) {
            Explosion exp = levelUp.get(i);
            exp.draw(g);
            if (exp.isFinished()) {
                levelUp.remove(i);
                i--; 
            }
        }

        int barWidth = 384;
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
        enemySpawnTimer.stop();
        enemySpawnInterval = 2500;
        enemySpawnTimer.setDelay(enemySpawnInterval);
        enemySpawnTimer.setInitialDelay(enemySpawnInterval);
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
        blockSpawnTimer.start();
        this.enemySpawnInterval = 2500;

        leftPressed = false;
        rightPressed = false;
        spacePressed = false;

        tanks.setBounds(playerX, playerY, 1200, 84);

    
    }

    public void checkHighScore(){
        if(this.score > this.highscore){
            this.highscore = this.score;
        }
    }

    public int getScore() {
        return score;
    }

    // utk explosion

    private BufferedImage[] loadExplosionFrames() {
        BufferedImage[] frames = new BufferedImage[10];
        
        try {
            for (int i = 1; i < 11; i++) {
                frames[i-1] = javax.imageio.ImageIO.read(new File("assets/PNG/Explosion_1/Explosion_" + (i) + ".png"));
            }
        
        } catch (Exception e) {
            e.printStackTrace();
        }
            return frames;
        }

    private BufferedImage[] loadExplosionBase() {
            BufferedImage[] frames = new BufferedImage[10];
            
            try {
                for (int i = 1; i < 11; i++) {
                    frames[i-1] = javax.imageio.ImageIO.read(new File("assets/PNG/Explosion_3/Explosion_" + (i) + ".png"));
                }
            
            } catch (Exception e) {
                e.printStackTrace();
            }
                return frames;
            }

    private BufferedImage[] loadLevelUp(){
        BufferedImage[] frames = new BufferedImage[10];

        try{
            for(int i = 1; i<11; i++){
                frames[i-1] = javax.imageio.ImageIO.read(new File("assets/PNG/Explosion_5/Explosion_" + (i) + ".png"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return frames;
    }

    


}
