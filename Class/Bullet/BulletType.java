package Bullet;
public abstract class BulletType {
    protected String imagePath;
    protected int damage;
    protected int speed;

    public BulletType(String imagePath, int damage, int speed) {
        this.imagePath = imagePath;
        this.damage = damage;
        this.speed = speed;
    }

    public String getImagePath() { 
        return imagePath; 
    }
    public int getDamage() { 
        return damage; 
    }
    public int getSpeed() { 
        return speed; 
    }
}