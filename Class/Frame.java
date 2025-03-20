import java.awt.Color;
import java.awt.Image;

import javax.swing.*;

public class Frame extends JPanel{
    private int tileSize = 32;
    private int rows = 24;
    private int columns = 32;
    private int width;
    private int height; 
    JFrame frame = new JFrame("Ya main last war lah !!");

    Image tankImage;
    Image enemyImage;
    Image projectileImage;

    
    Frame(){
        width = columns * tileSize;
        height = rows * tileSize;

        frame.setSize(width , height);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setBackground(Color.BLACK);

        tankImage = new ImageIcon(getClass().getResource("assets/PNG/Hulls_Color_D/Hull_08.png")).getImage();
        
        frame.add(this);
        
        frame.setVisible(true);    
    }

    
}