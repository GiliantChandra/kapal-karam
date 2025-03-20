import java.awt.Graphics;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

class Tank {
    private int x, y;
    private int health = 100;  

    public Tank(int x, int y) {
        this.x = x;
        this.y = y;
    }   

    public void move(int dx, int dy) {
        x += dx;
        y += dy;
    }

    public void draw(Graphics g) {
        g.fillRect(x, y, 32, 32);
    }

    public void takeDamage(int damage) {
        health -= damage;
    }

    public boolean isDestroyed() {
        return health <= 0;
    }
    public int getX() { return x; } 
    public int getY() { return y; }

    public void shoot() {
        // Spawn projectile
    }

    public void updateProjectiles() {
        // Move projectiles
    }

    public void drawProjectiles(Graphics g) {
        // Draw projectiles
    }

    public boolean isOffScreen() {
        return y > 600; // Screen height
    }

    private BufferedImage base, turret, track;
    
    String[] BasePath = {
        "assets/PNG/Hulls_Color_D/Hull_08.png", 
        "assets/PNG/Hulls_Color_D/Hull_07.png",
        "assets/PNG/Hulls_Color_D/Hull_06.png",
        "assets/PNG/Hulls_Color_D/Hull_01.png",
        "assets/PNG/Hulls_Color_D/Hull_04.png",
        "assets/PNG/Hulls_Color_D/Hull_03.png",
        "assets/PNG/Hulls_Color_D/Hull_05.png",
        "assets/PNG/Hulls_Color_D/Hull_02.png"
    };

    String[] turretPath = {
        "assets/PNG/Weapon_Color_D/Gun_08.png",
        "assets/PNG/Weapon_Color_D/Gun_07.png",
        "assets/PNG/Weapon_Color_D/Gun_06.png",
        "assets/PNG/Weapon_Color_D/Gun_01.png",
        "assets/PNG/Weapon_Color_D/Gun_04.png",
        "assets/PNG/Weapon_Color_D/Gun_03.png",
        "assets/PNG/Weapon_Color_D/Gun_05.png",
        "assets/PNG/Weapon_Color_D/Gun_02.png" 
    };

    String[] trackPath = {
        "assets/PNG/Tracks/Track_1_A.png",
        "assets/PNG/Tracks/Track_1_B.png",
        "assets/PNG/Tracks/Track_2_A.png",
        "assets/PNG/Tracks/Track_2_B.png",
        "assets/PNG/Tracks/Track_3_A.png",
        "assets/PNG/Tracks/Track_3_B.png",
        "assets/PNG/Tracks/Track_4_A.png",
        "assets/PNG/Tracks/Track_4_B.png"
    };

    public TankAssembler() {
        try {
            base = ImageIO.read(new File(BasePath[0]));  
            turret = ImageIO.read(new File(turretPath[0]));
            track = ImageIO.read(new File(trackPath[0]));
        } catch (Exception e) {
            System.out.println("Error loading images");
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (base != null && turret != null) {
            int x = (getWidth() - base.getWidth()) / 2;
            int y = (getHeight() - base.getHeight()) / 2;

            // Draw body and turret
            g.drawImage(track, x + 55, y + 10 , null);
            g.drawImage(track, x + 160, y + 10 , null);

            g.drawImage(base, x, y, null); 
            g.drawImage(turret, x + 115 , y + 30, null);
        }
    }

    // Method to update the tank parts dynamically
    public void setTankParts(int baseIndex, int turretIndex, int trackIndex) {
        try {
            base = ImageIO.read(new File(BasePath[baseIndex]));  
            turret = ImageIO.read(new File(turretPath[turretIndex]));  
            track = ImageIO.read(new File(trackPath[trackIndex]));
            repaint(); // Update the frame
        } catch (Exception e) {
            System.out.println("Error updating images");
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Tank Assembler");
        TankAssembler tankPanel = new TankAssembler();
        
        frame.add(tankPanel);
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        
        // Example: Change tank parts dynamically
        tankPanel.setTankParts(1, 2, 3);
    }

}