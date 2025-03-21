import java.awt.*;
public class Block {
    int x, y, length, width;
    Image img;
    public Block(int x , int y, int length , int width, Image img){
        this.x = x;
        this.y = y;
        this.length = length;
        this.width = width;
        this.img = img;
    }
}
