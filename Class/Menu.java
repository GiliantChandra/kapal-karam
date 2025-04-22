import javax.swing.*;
import java.awt.*;
public class Menu extends JPanel{
    public JButton createImageButton(String normalImagePath, String hoverImagePath, int width, int height) {
    // Load gambar normal dan hover
    ImageIcon normalIcon = new ImageIcon(new ImageIcon(normalImagePath).getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
    ImageIcon hoverIcon = new ImageIcon(new ImageIcon(hoverImagePath).getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));

    JButton button = new JButton(normalIcon);
    button.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
    button.setBorderPainted(false);
    button.setContentAreaFilled(false);
    button.setFocusPainted(false);
    button.setOpaque(false);

    // Tambahkan efek hover
    button.addMouseListener(new java.awt.event.MouseAdapter() {
        @Override
        public void mouseEntered(java.awt.event.MouseEvent evt) {
            button.setIcon(hoverIcon); // Ubah ikon saat hover
        }

        @Override
        public void mouseExited(java.awt.event.MouseEvent evt) {
            button.setIcon(normalIcon); // Kembalikan ikon normal saat tidak hover
        }
    });

    return button;
}

    
}