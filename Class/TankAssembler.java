import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class TankAssembler extends JPanel {
    private BufferedImage base, turret;

    public TankAssembler() {
        try {
            // Load images from files
            base = ImageIO.read(new File("assets/PNG/Hulls_Color_D/Hull_08.png"));  
            turret = ImageIO.read(new File("assets/PNG/Weapon_Color_D/Gun_05_A.png")); // Includes the gun barrel
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (base != null && turret != null) {
            int x = (getWidth() - base.getWidth()) / 2;
            int y = (getHeight() - base.getHeight()) / 2;

            // Draw body and turret
            g.drawImage(base, x, y, null); 
            g.drawImage(turret, x + 115 , y + 140, null);  // Adjust position if needed
        }
    }
    
    public void saveImage() {
        if (base == null || turret == null) {
            System.out.println("Error: Some images are missing.");
            return;
        }

        try {
            BufferedImage combined = new BufferedImage(base.getWidth(), base.getHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics g = combined.getGraphics();
            
            g.drawImage(base, 0, 0, null);
            g.drawImage(turret, 30, 15, null);

            ImageIO.write(combined, "PNG", new File("final_tank.png"));
            System.out.println("Tank image saved as final_tank.png!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Tank Assembly");
        TankAssembler panel = new TankAssembler();

        frame.add(panel);
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Save the image after a short delay
        new Timer(1000, e -> panel.saveImage()).start();
    }
}
