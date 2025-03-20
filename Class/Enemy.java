// import java.awt.Graphics;
// import java.awt.image.BufferedImage;
// import java.util.ArrayList;
// import java.util.List;

// class Enemy {
//     private int x, y;
//     private int type; // 0 = Drone, 1 = Scout, 2 = Tank, 3 = Boss
//     private int level; // Upgrade level
//     private int health = 100;
//     private List<Projectile> projectiles = new ArrayList<>();

//     public Enemy(int x, int y, int type, int level) {
//         this.x = x;
//         this.y = y;
//         this.type = type;
//         this.level = level;
//     }

//     public void move() {
//         y += 2; // Move downward
//     }

//     public void shoot() {
//         projectiles.add(new Projectile(x + 10, y + 20)); // Spawn projectile
//     }

//     public void updateProjectiles() {
//         for (int i = projectiles.size() - 1; i >= 0; i--) {
//             Projectile p = projectiles.get(i);
//             p.move();
//             if (p.isOffScreen()) projectiles.remove(i);
//         }
//     }

//     public void draw(Graphics g) {
//         BufferedImage image = null;
//         switch (type) {
//             case 0: image = assetsLoader.drones.get(level); break; // Drone
//             case 1: image = assetsLoader.scouts.get(level); break; // Scout
//             case 2: image = assetsLoader.tanks.get(level); break;  // Tank
//             case 3: image = assetsLoader.bosses.get(level); break; // Boss
//         }
//         if (image != null) g.drawImage(image, x, y, null);
//     }

//     public void drawProjectiles(Graphics g) {
//         for (Projectile p : projectiles) p.draw(g);
//     }

//     public boolean isOffScreen() { return y > 600; } // Screen height
//     public void takeDamage(int damage) { health -= damage; }
//     public boolean isDestroyed() { return health <= 0; }
//     public int getX() { return x; }
//     public int getY() { return y; }
//     public List<Projectile> getProjectiles() { return projectiles; }

// }