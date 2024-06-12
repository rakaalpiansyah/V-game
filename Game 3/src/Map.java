import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;

public class Map {
    BufferedImage image1;
    BufferedImage image2;
    GamePanel gp;
    int[] koordinatTile = {
        20, 600, 70, 20,
        80, 810, 70, 20,
        250, 650, 70, 20,
        500, 750, 70, 20,
        1200, 840, 70, 20,
        800, 780, 70, 20,
        1000, 650, 70, 20,
        200, 930, 70, 20, 
        650, 930, 70, 20, 
        950, 930, 70, 20 };
            
    public Map(GamePanel gp) {
        this.gp = gp;
        getMap(gp);
    }

    public void getMap(GamePanel gp) {
        try {
            image1 = ImageIO.read(getClass().getResourceAsStream("./images/5.png"));
            image2 = ImageIO.read(getClass().getResourceAsStream("./images/Tile_22.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(image1, 0, 0, 1280, 950, null);
        
        for (int i = 0; i < koordinatTile.length; i += 2) {
            g2.drawImage(image2, koordinatTile[i], koordinatTile[i + 1], 70, 20, null);
        }
    }
}