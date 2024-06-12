import java.awt.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.*;

public class GamePanel extends JPanel implements Runnable {

    int FPS = 60;
    int lebarLayar = 1280;
    int tinggiLayar = 950;
    Tombol tombolY = new Tombol();
    Thread gameThread;
    Meteor meteor;
    boolean gameOver = false;
    Map mp = new Map(this);
    Player player = new Player(this, tombolY, FPS, null, FPS, FPS, FPS, FPS);
    Map map;
    ArrayList<Meteor> boxs = new ArrayList<>();

    public GamePanel() {
        this.setPreferredSize(new Dimension(lebarLayar, tinggiLayar));
        this.setBackground(Color.gray);
        this.setDoubleBuffered(true);
        this.addKeyListener(tombolY);
        this.setFocusable(true);

    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void run() {
        double GambarFPS = 1000 / FPS;
        long WaktuTrakhir = System.currentTimeMillis(); // 1 detik = 1000 mili
        long Waktuskrg;
        long timer = 0;
        int drawCount = 0;
        double delta = 0;
        while (gameThread != null) {
            Waktuskrg = System.currentTimeMillis();
            delta += (Waktuskrg - WaktuTrakhir) / GambarFPS;
            timer += (Waktuskrg - WaktuTrakhir);
            WaktuTrakhir = Waktuskrg;

            if (delta >= 1) {
                delta--;
                drawCount++;
                update();
                repaint();
                if (timer >= 1000) {
                    System.out.println("FPS: " + drawCount);
                    drawCount = 0;
                    timer = 0;
                    LocalDateTime waktu = LocalDateTime.now();
                    System.out.println(waktu);
                    boxs.add(new Meteor((int) (Math.random() * lebarLayar), 0, 15, null));
                }
            }
        }
    }

    public void update() {
        player.update();
        Iterator<Meteor> it = boxs.iterator();
        while (it.hasNext()) {
            Meteor box = it.next();
            box.update();
            if (box.getY() > tinggiLayar) {
                it.remove();
            }
        }

    }

    public void stopGame() {
        gameThread = null;
        System.exit(0);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        mp.draw(g2);
        player.draw(g2);
        for (Meteor box : boxs) {
            box.draw(g2);
        }
        g2.dispose();
    }

    public static void main(String[] args) {
        JFrame window = new JFrame(null, null);
        GamePanel gamePanel = new GamePanel();
        window.setResizable(false);
        window.setTitle("GAME Apa?");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.add(gamePanel);
        window.pack();
        window.setLocationRelativeTo(null);
        gamePanel.startGameThread();
        window.setVisible(true);
    }
}
