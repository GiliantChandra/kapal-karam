
// import javax.swing.*;
// import java.awt.*;
// import java.awt.image.BufferedImage;
// import java.io.File;     
// import javax.imageio.ImageIO;

// public class Bullet extends JPanel{
//     public BufferedImage bulletImg; 
//     private int bulletX; 
//     private int bulletY; 
//     int bulletWidth = 64; // tilesize/8 
//     int bulletHeight = 64; //tilesize/2 
//     int bulletVelocityY = -10; //bullet moving speed
//     private int idxBullet = 0;    


//     String[] BulletPath = {"assets/PNG/Effects/Plasma.png", 
//                             "assets/PNG/Effects/Laser.png", 
//                             "assets/PNG/Effects/Shotgun_Shells.png",
//                             "assets/PNG/Props/Explosion_02.png",
//                             "assets/PNG/Props/Rocket_Effect_01.png",
//                             "assets/PNG/Props/Explosion_01.png",
//                             "assets/PNG/Props/Missile_03.png",
//                             "assets/PNG/Effects/Flash_A_05.png",
//                             "assets/PNG/Effects/Flash_A_04.png",
//                         };

//     int[] damage = {-50 , -150, -300 , -450 , -500, -1000 , -2000, -2500, -3000};

//     public int getidxBullet(){
//         return this.idxBullet;
//     }

//     public void setidxBullet(int idx){
//         this.idxBullet = idx;
//         updateBulletImage();
//     }

//     public void incrementIdxBullet(){
//         if(this.idxBullet < 8){
//             this.idxBullet = this.idxBullet + 1;
//         }
        
//     }

    

//     public int getBulletX() {
//         return bulletX;
//     }

//     public int getBulletY() {
//         return bulletY;
//     }

//     public int getBulletWidth() {
//         return bulletWidth;
//     }

//     public int getBulletHeight() {
//         return bulletHeight;
//     }

    
//     public Bullet(int bulletX, int bulletY) {
//         this.bulletX = bulletX;
//         this.bulletY = bulletY;
//         setBounds(bulletX, bulletY, bulletWidth, bulletHeight);
//         setOpaque(false);
//     }

//     public void updateBulletImage() {
//         try {
//             bulletImg = ImageIO.read(new File(BulletPath[idxBullet]));  
//             repaint(); 
//         } catch (Exception e) {
//             System.out.println("Error loading bullet image: " + e.getMessage());
//         }
//     }
    

//     public void move () {
//         bulletY += bulletVelocityY;
//         setLocation(bulletX, bulletY);
//         repaint();
//     }

//     @Override  
//     protected void paintComponent(Graphics g) {
//         super.paintComponent(g);
//         Graphics2D g2d = (Graphics2D) g;
        
//         if (bulletImg != null) {
//             g2d.drawImage(bulletImg, -10, 0, bulletWidth , bulletHeight, null);
//         }
//     }   
// }
