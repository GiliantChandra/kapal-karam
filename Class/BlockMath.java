import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class BlockMath extends JPanel {
    Random rand = new Random();
    private boolean isCollided = false;
    private String value;
    private int x, y;
    private int speed = 2; 
    private int WIDTH = 256;
    private int HEIGHT = 64;
    private String operator[] = {"+" , "-", "/", "*"};
    int a, b, c;
    String opr0 = operator[rand.nextInt(2)];

    String opr1 = operator[rand.nextInt(4)];
    String opr2 = operator[rand.nextInt(4)];
    
    public BlockMath(int startX, int startY) {
        this.x = startX;
        this.y = startY;
        this.value = generateMathValue();
        setBounds(x, y, WIDTH, HEIGHT);
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;
    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    // Warna transparan: Hijau dengan Alpha = 150
    Color transparentColor = new Color(0, 255, 0, 50);
    g2d.setColor(transparentColor);

    // Aktifkan transparansi
    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f)); // 0.0 - 1.0 (Semakin rendah semakin transparan)

    // Gambar blok dengan warna transparan
    g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);

    // Reset transparansi agar teks tidak ikut transparan
    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
    
    // Gambar angka di tengah blok
    g2d.setColor(Color.WHITE);
    g2d.setFont(new Font("Arial", Font.BOLD, 16));
    FontMetrics metrics = g2d.getFontMetrics();
    int x = (getWidth() - metrics.stringWidth(String.valueOf(value))) / 2;
    int y = (getHeight() - metrics.getHeight()) / 2 + metrics.getAscent();
    g2d.drawString(String.valueOf(value), x, y);
}



    
    private String generateMathValue() {
        Random rand = new Random();

        a = rand.nextInt(10) + 1;
        b = rand.nextInt(10) + 1;
        c = rand.nextInt(10) + 1;
        return opr0 + " ( " + a + " " + opr1 + " " + b + " " + opr2 + " " + c + " ) "; 
    }
    
    public void move() {
        y += speed;
        setLocation(x, y);
    }
    
    public int getValue() {
        int hirarkiOpr1 = 0, hirarkiOpr2 = 0;
        switch(opr1){
            case "+" : case "-":
                hirarkiOpr1 = 2;
                break;
            case "*" : case "/":
                hirarkiOpr1 = 1;
                break;
        }
        switch(opr2){
            case "+" : case "-":
                hirarkiOpr2 = 2;
                break;
            case "*" : case "/":
                hirarkiOpr2 = 1;
                break;
        }
        int hasil = 0;

        if(hirarkiOpr1 <= hirarkiOpr2){
            switch(opr1){
            case "+":
                hasil = a + b;
                break;
            case "-":
                hasil = a - b;
                break;
            case "*":
                hasil = a * b;
                break;
            case "/":
                hasil = a / b;
                break;
            }
            switch(opr2){
            case "+":
                hasil += c;
                break;
            case "-":
                hasil -= c;
                break;
            case "*":
                hasil *= c;
                break;
            case "/":
                if (hasil == 0) {
                    hasil = 1;
                }
                hasil/=c;
                break;
            }
        }else{
            switch(opr2){
                case "*":
                    hasil = b * c ;
                    break;
                case "/":
                    hasil = b / c;
                    break;
        }
            switch(opr1){
                case "+":
                    hasil = a+ hasil;
                    break;
                case "-":
                    hasil = a - hasil;
                    break;
                case "*":
                    hasil = a * hasil;
                    break;
                case "/":
                    if(hasil == 0){
                        hasil = 1;
                    }
                    hasil = a / hasil;

                    break;
            }
        }
        return hasil;
    }
    

    public boolean isHit(TankAssembler tank) {
        int tankX = tank.getTankX();
        int tankY = tank.getTankY();
        int tankWidth = tank.getTankWidth();
        int tankHeight = tank.getTankHeight();
    
        int blockX = getX();
        int blockY = getY();
        int blockWidth = getWidth();
        int blockHeight = getHeight();
    
        return (tankX < blockX + blockWidth &&
                tankX + tankWidth > blockX &&
                tankY < blockY + blockHeight &&
                tankY + tankHeight > blockY);
        
        
    }

    public boolean isCollided(){
        return this.isCollided;
    }

    public void setCollided(boolean b){
        this.isCollided = b;
    }
    
    public int getBlockY(){
        return this.y;
    }
}
