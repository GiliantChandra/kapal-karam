import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.*;

public class Frame extends JPanel implements ActionListener {
    private int tileSize = 32;
    private int rows = 24;
    private int columns = 16;
    private int width;
    private int height; 
    Timer gameLoop;
    JFrame frame = new JFrame("Ya main last war lah !!");

    TankAssembler tanks = new TankAssembler();

    Frame() {
        width = columns * tileSize;
        height = rows * tileSize;

        int playerX = tileSize * columns / 2 - 32; 
        int playerY = height - 110; 

        frame.setSize(width , height);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setBackground(Color.BLACK);
        setLayout(null); 

        
        tanks.setBounds(playerX, playerY, 64, 64);
        add(tanks);

        frame.add(this);
        setFocusable(true);
        requestFocusInWindow(); 
        frame.setVisible(true);

        gameLoop = new Timer(1000 / 60, this);
        gameLoop.start();

        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    tanks.moveLeft();
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    tanks.moveRight();
                }
            }
        });

        frame.setFocusable(true);
        frame.requestFocus();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }
}
