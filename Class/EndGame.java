import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class EndGame{

    

    public EndGame(Runnable onRestart) {
        JFrame gameOverFrame = new JFrame("Game Over");
        gameOverFrame.setSize(300, 200);
        gameOverFrame.setLayout(new FlowLayout());
        gameOverFrame.setLocationRelativeTo(null); // Supaya di tengah layar

        JLabel message = new JLabel("Game Over! Ingin bermain lagi?");
        JButton playAgainButton = new JButton("Play Again");
        JButton quitButton = new JButton("Quit");

        playAgainButton.addActionListener(e -> {
            gameOverFrame.dispose(); // Tutup window game over
            onRestart.run();
        });

        quitButton.addActionListener(e -> {
            System.exit(0); // Keluar dari game
        });

        gameOverFrame.add(message);
        gameOverFrame.add(playAgainButton);
        gameOverFrame.add(quitButton);
        
        gameOverFrame.setVisible(true);
}
}

