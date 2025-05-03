import javax.swing.JFrame;

public class Main {
    public static void main(String[] args){
        JFrame window = new JFrame("Ya main last war lah !!");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);
        window.setResizable(false);

        startPanel start = new startPanel(window);
        window.setContentPane(start);
        
        window.pack(); //  ikutin size startpanel
        window.setLocationRelativeTo(null);// utk center

        window.setVisible(true);
    }
    
}
