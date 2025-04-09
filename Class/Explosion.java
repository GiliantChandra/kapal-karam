import java.awt.Graphics;
import java.awt.image.BufferedImage;


public class Explosion {
    private int x, y, width, height;
    private BufferedImage[] frames;
    private int currentFrame = 0;
    private int frameDelay = 5;
    private int frameCounter = 0;

    public Explosion(int x, int y,int height, int width,  BufferedImage[] frames) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.frames = frames;
    }

    public void draw(Graphics g) {
        if (currentFrame < frames.length) {
            g.drawImage(frames[currentFrame], x, y,width, height, null);
            frameCounter++;
            if (frameCounter >= frameDelay) {
                currentFrame++;
                frameCounter = 0;
            }
        }
    }

    public boolean isFinished() {
        return currentFrame >= frames.length;
    }
}
