import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class TankAssembler extends JPanel {
    private BufferedImage base, turret , track;

    String[] BasePath = {"assets/PNG/Hulls_Color_D/Hull_08.png", 
                         "assets/PNG/Hulls_Color_D/Hull_07.png",
                         "assets/PNG/Hulls_Color_D/Hull_06.png",
                         "assets/PNG/Hulls_Color_D/Hull_01.png",
                         "assets/PNG/Hulls_Color_D/Hull_04.png",
                         "assets/PNG/Hulls_Color_D/Hull_03.png",
                         "assets/PNG/Hulls_Color_D/Hull_05.png",
                         "assets/PNG/Hulls_Color_D/Hull_02.png"};

                         
    String[] turretPath = {"assets/PNG/Weapon_Color_D/Gun_08.png",
                           "assets/PNG/Weapon_Color_D/Gun_07.png",
                           "assets/PNG/Weapon_Color_D/Gun_06.png",
                           "assets/PNG/Weapon_Color_D/Gun_01.png",
                           "assets/PNG/Weapon_Color_D/Gun_04.png",
                           "assets/PNG/Weapon_Color_D/Gun_03.png",
                           "assets/PNG/Weapon_Color_D/Gun_05.png",
                           "assets/PNG/Weapon_Color_D/Gun_02.png" 
                           };
                           
    String[] trackPath = {"/assets/PNG/Tracks/Track_1_A.png",
                          "/assets/PNG/Tracks/Track_1_B.png",
                          "/assets/PNG/Tracks/Track_2_A.png",
                          "/assets/PNG/Tracks/Track_2_B.png",
                          "/assets/PNG/Tracks/Track_3_A.png",
                          "/assets/PNG/Tracks/Track_3_B.png",
                          "/assets/PNG/Tracks/Track_4_A.png",
                          "/assets/PNG/Tracks/Track_4_B.png"};

    public TankAssembler() {
        try {
            //ni try catch apa    gtw jirr kekny g prna di mention deh
            //ya tpi keknya w blm ads gambaran gmn cara buat itu
            // brti sini siapin path byk" senjata, sesuai level
            // ganti nama variabel jadi baselvl1m baselvl2, trs buat if else (cek ada brp poin), yang nnti diassign ke base
            //harusnya gitu
            //ohh bs jg
            base = ImageIO.read(new File(BasePath[0]));  
            turret = ImageIO.read(new File(turretPath[0])); // Includes the gun barrel
            track = ImageIO.read(new File(trackPath[0]));
        } catch (Exception e) {
            System.out.println("error");
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (base != null && turret != null) {
            int x = (getWidth() - base.getWidth()) / 2;
            int y = (getHeight() - base.getHeight()) / 2;

            // Draw body and turret
            g.drawImage(track,x + 55, y + 10 , null);
            g.drawImage(track,x + 160, y + 10 , null);

            g.drawImage(base, x, y, null); 
            g.drawImage(turret, x + 115 , y + 30, null);  // Adjust position if needed
        }
    }
    // brrti run di lu jir, tdi w pake terminal, java TankAssembler
    
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
