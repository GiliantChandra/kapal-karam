// import javax.imageio.ImageIO;
// import java.awt.image.BufferedImage;
// import java.io.File;
// import java.io.IOException;
// import java.util.ArrayList;
// import java.util.List;

// public class assetsLoader {
//     // Tank assets
//     public static List<BufferedImage> cannons = new ArrayList<>();
//     public static List<BufferedImage> hulls = new ArrayList<>();
//     public static List<BufferedImage> tracks = new ArrayList<>();
//     public static List<BufferedImage> fireEffects = new ArrayList<>();

//     // Enemy assets
//     public static List<BufferedImage> drones = new ArrayList<>();
//     public static List<BufferedImage> scouts = new ArrayList<>();
//     public static List<BufferedImage> tanks = new ArrayList<>();
//     public static List<BufferedImage> bosses = new ArrayList<>();

//     static {
//         try {
//             // Load tank images
//             cannons.add(ImageIO.read(new File("assets/PNG/Weapon_Color_D/Gun_08.png")));
//             cannons.add(ImageIO.read(new File("assets/PNG/Weapon_Color_D/Gun_07.png")));
//             cannons.add(ImageIO.read(new File("assets/PNG/Weapon_Color_D/Gun_06.png")));

//             hulls.add(ImageIO.read(new File("assets/PNG/Hulls_Color_D/Hull_08.png")));
//             hulls.add(ImageIO.read(new File("assets/PNG/Hulls_Color_D/Hull_07.png")));
//             hulls.add(ImageIO.read(new File("assets/PNG/Hulls_Color_D/Hull_06.png")));

//             tracks.add(ImageIO.read(new File("assets/PNG/Tracks/Track_2_B.png")));
//             tracks.add(ImageIO.read(new File("assets/PNG/Tracks/Track_3_A.png")));
//             tracks.add(ImageIO.read(new File("assets/PNG/Tracks/Track_4_B.png")));

//             fireEffects.add(ImageIO.read(new File("assets/Effects/Flame_C.png")));
//             fireEffects.add(ImageIO.read(new File("assets/Effects/Flame_D.png")));
//             fireEffects.add(ImageIO.read(new File("assets/Effects/Flame_E.png")));

//             // Load enemy images
//             drones.add(ImageIO.read(new File("assets/PNG/Sprites/Missile/Missile_1_Flying_001.png")));

//             scouts.add(ImageIO.read(new File("assets/PNG/Sprites/Missile/Missile_2_Flying_001.png")));

//             tanks.add(ImageIO.read(new File("assets/PNG/Sprites/Missile/Missile_3_Flying_001.png")));

//             bosses.add(ImageIO.read(new File("assets/PNG/Sprites/Missile/Missile_3_Flying_000.png")));

//             System.out.println("Cannons: " + cannons.size());
//             System.out.println("Hulls: " + hulls.size());
//             System.out.println("Tracks: " + tracks.size());
//             System.out.println("Fire Effects: " + fireEffects.size());
//             System.out.println("Drones: " + drones.size());
//             System.out.println("Scouts: " + scouts.size());
//             System.out.println("Tanks: " + tanks.size());
//             System.out.println("Bosses: " + bosses.size());
            
//         } catch (IOException e) {
//             e.printStackTrace();
//         }
//     }
// }