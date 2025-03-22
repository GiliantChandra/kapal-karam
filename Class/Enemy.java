// import java.awt.Graphics;
// import java.awt.image.BufferedImage;
// import java.util.ArrayList;
// import java.util.List;

// class Enemy {
//     private int x, y;
//     private int type; // 0 = Drone, 1 = Scout, 2 = Tank, 3 = Boss
//     private int level; // Upgrade level
//     private int health = 100;
//     private List<Projectile> projectiles = new ArrayList<>();

//     public Enemy(int x, int y, int type, int level) {
//         this.x = x;
//         this.y = y;
//         this.type = type;
//         this.level = level;
//     }

//     public void move() {
//         y += 2; // Move downward
//     }

//     public void shoot() {
//         projectiles.add(new Projectile(x + 10, y + 20)); // Spawn projectile
//     }

//     public void updateProjectiles() {
//         for (int i = projectiles.size() - 1; i >= 0; i--) {
//             Projectile p = projectiles.get(i);
//             p.move();
//             if (p.isOffScreen()) projectiles.remove(i);
//         }
//     }

//     public void draw(Graphics g) {
//         BufferedImage image = null;
//         switch (type) {
//             case 0: image = assetsLoader.drones.get(level); break; // Drone
//             case 1: image = assetsLoader.scouts.get(level); break; // Scout
//             case 2: image = assetsLoader.tanks.get(level); break;  // Tank
//             case 3: image = assetsLoader.bosses.get(level); break; // Boss
//         }
//         if (image != null) g.drawImage(image, x, y, null);
//     }

//     public void drawProjectiles(Graphics g) {
//         for (Projectile p : projectiles) p.draw(g);
//     }

//     public boolean isOffScreen() { return y > 600; } // Screen height
//     public void takeDamage(int damage) { health -= damage; }
//     public boolean isDestroyed() { return health <= 0; }
//     public int getX() { return x; }
//     public int getY() { return y; }
//     public List<Projectile> getProjectiles() { return projectiles; }

// }

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;



public class Enemy extends JPanel{
    private BufferedImage enemyImg;
    private int enemyX;
    private int enemyY;
    private int speed = 2;
    private int idxEnemy = (int) (Math.random() * 9);
    int RandomX, Y;

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
        setBounds(enemyX, enemyY, 64, 64);
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
        }
    }

    public void move() { 
        enemyY += speed;
        setLocation(enemyX, enemyY);
        repaint();
        
    }


}