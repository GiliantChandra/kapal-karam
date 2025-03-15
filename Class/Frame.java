import javax.swing.*;

public class Frame{
    private int tileSize = 32;
    private int rows = 48;
    private int columns = 32;
    private int width;
    private int height;
    JFrame frame = new JFrame("Ya main last war lah !!");


    Frame(){
        width = columns * tileSize;
        height = rows * tileSize;

        frame.setVisible(true);
        frame.setSize(width , height);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    
}