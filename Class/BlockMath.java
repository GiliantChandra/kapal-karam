import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class BlockMath extends JLabel {
    private int value;
    private int x, y;
    private int speed = 2; 
    private static final int WIDTH = 64;
    private static final int HEIGHT = 64;
    
    public BlockMath(int startX, int startY) {
        this.x = startX;
        this.y = startY;
        this.value = generateMathValue();
        setBounds(x, y, WIDTH, HEIGHT);
        setOpaque(true);
        setBackground(Color.YELLOW);
        setHorizontalAlignment(SwingConstants.CENTER);
        setFont(new Font("Arial", Font.BOLD, 16));
        setText(String.valueOf(value));
    }
    
    private int generateMathValue() {
        Random rand = new Random();
        int a = rand.nextInt(10) + 1;
        int b = rand.nextInt(10) + 1;
        return a + b; 
    }
    
    public void move() {
        y += speed;
        setLocation(x, y);
    }
    
    public int getValue() {
        return value;
    }
}
