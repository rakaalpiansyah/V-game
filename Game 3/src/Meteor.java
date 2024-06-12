import java.awt.*;
import javax.imageio.ImageIO;
import java.io.IOException;

public class Meteor {
    int x, y;
    int speed;
    Image image1;
    Player player;

    public Meteor(int x, int y, int speed, String imagePath) {
        this.x = x;
        this.y = y;
        this.speed = speed;

        try {
            this.image1 = ImageIO.read(getClass().getResourceAsStream("./images/meteor.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        y += speed;
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(image1, x, y, 150, 150, null);
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

}
