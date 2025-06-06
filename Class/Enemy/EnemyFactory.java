package Enemy;
import java.util.Random;

public class EnemyFactory {
    private static Random rand = new Random();

    public static Enemy createEnemy() {
        int x = rand.nextInt(512 - 128); 
        int y = -64;

        EnemyAbs[] enemyTypes = {
            new Rocket(),
            new blackThinMeteor(),
            new breadMeteor(),
            new whiteSlimMeteor(),
            new heavyMeteor(),
            new Crystal()
        };

        int randomIndex = rand.nextInt(enemyTypes.length);
        return new Enemy(x, y, enemyTypes[randomIndex]); 
    }
}
