import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

class Tank {
    private int x, y;
    private int cannonLevel = 0;
    private int hullLevel = 0;
    private int tracksLevel = 0;
    private int fireLevel = 0;
    private int health = 100;
    private List<Projectile> projectiles = new ArrayList<>();

    public Tank(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void moveLeft() { x -= 5; }
    public void moveRight() { x += 5; }

    public void shoot() {
        projectiles.add(new Projectile(x + 20, y - 10)); // Spawn projectile at cannon position
    }

    public void updateProjectiles() {
        for (int i = projectiles.size() - 1; i >= 0; i--) {
            Projectile p = projectiles.get(i);
            p.move();
            if (p.isOffScreen()) projectiles.remove(i);
        }
    }

    public void draw(Graphics g) {
        // Draw hull
        g.drawImage(assetsLoader.hulls.get(hullLevel), x, y, null);
        // Draw tracks
        g.drawImage(assetsLoader.tracks.get(tracksLevel), x, y + 20, null);
        // Draw cannon
        g.drawImage(assetsLoader.cannons.get(cannonLevel), x + 10, y - 10, null);
        // Draw fire effect
        g.drawImage(assetsLoader.fireEffects.get(fireLevel), x + 15, y + 30, null);
    }

    public void drawProjectiles(Graphics g) {
        for (Projectile p : projectiles) p.draw(g);
    }

    public List<Projectile> getProjectiles() {
        return projectiles;
    }

    public void takeDamage(int damage) {
        health -= damage;
        if (health < 0) health = 0;
    }

    public int getHealth() { return health; }
    public int getX() { return x; }
    public int getY() { return y; }

    public void upgradeCannon() {
        if (cannonLevel < assetsLoader.cannons.size() - 1) cannonLevel++;
    }

    public void upgradeHull() {
        if (hullLevel < assetsLoader.hulls.size() - 1) hullLevel++;
    }

    public void upgradeTracks() {
        if (tracksLevel < assetsLoader.tracks.size() - 1) tracksLevel++;
    }

    public void upgradeFire() {
        if (fireLevel < assetsLoader.fireEffects.size() - 1) fireLevel++;
    }
}
