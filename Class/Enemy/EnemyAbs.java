package Enemy;
public abstract class EnemyAbs {
    protected int health;
    protected int enemyWidth;
    protected int enemyHeight;
    protected int speed;
    protected String enemyImg; 

    public EnemyAbs(int health, int enemyWidth, int enemyHeight, int speed, String enemyImg) {
        this.health = health;
        this.enemyWidth = enemyWidth;
        this.enemyHeight = enemyHeight;
        this.speed = speed;
        this.enemyImg = enemyImg;
    }

    public int getEnemyHealth() {
        return health;
    }

    public int getEnemyWidth() {
        return enemyWidth;
    }

    public int getEnemyHeight() {
        return enemyHeight;
    }

    public int getEnemySpeed() {
        return speed;
    }

    public String getEnemyImg() {
        return enemyImg;
    }
}
