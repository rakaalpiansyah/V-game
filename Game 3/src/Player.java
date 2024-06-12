import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.*;

public class Player {

    BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    int x, y;
    int speed;
    int sp1 = 0, sp2 = 1;
    GamePanel gp;
    Tombol tombol;
    String gerakan;
    Map tile;
    Hp hp;
    int lebarTile = 70;
    int tinggiTile = 20;
    boolean melompat = false;
    int lebarChar = 70, tinggiChar = 70;
    Hp health;
    int gravitasi = 1;
    int kecepatnY = 0;
    boolean gameOver = false;

    public Player(GamePanel gp, Tombol tombolY, int maxHp, Hp health, int playerWidth, int playerHeight, int x, int y) {
        this.gp = gp;
        this.tombol = tombolY;
        GambarChar();
        this.hp = new Hp(100);
        this.health = health;
        this.x = x;
        this.y = y;
        speed = 4;
        this.gerakan = "0";
        this.tile = gp.mp;
    }

    public Hp getHealth() {
        return this.health;
    }

    public void GambarChar() {
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("./images/idle.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("./images/idle.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("./images/idle.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("./images/idle.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("./images/RunK1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("./images/RunK2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("./images/Run.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("./images/Run2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getBounds() {
        y += kecepatnY;
        kecepatnY += gravitasi;

        if (x <= 0) {
            x = 0;
        } else if (x + 70 > gp.lebarLayar) {
            x = gp.tinggiLayar - 70;
        }
        if (y <= 0) {
            y = 0;
        } else if (y + 70 > gp.lebarLayar) {
            gp.stopGame();
        }
    }

    private void cekCollisionTile() {
        int playerX = x;
        int playerY = y;
        int lebarChar = 70;
        int tinggiChar = 70;

        for (int i = 0; i < tile.koordinatTile.length; i += 2) {
            int tileX = tile.koordinatTile[i];
            int tileY = tile.koordinatTile[i + 1];

            int centerX = playerX + lebarChar / 2;
            int centerY = playerY + tinggiChar / 2;

            int tileCenterX = tileX + lebarTile / 2;
            int tileCenterY = tileY + tinggiTile / 2;

            int jarakX = Math.abs(centerX - tileCenterX);
            int jarakY = Math.abs(centerY - tileCenterY);

            if (jarakX < (lebarChar / 2 + lebarTile / 2) && jarakY < (tinggiChar / 2 + tinggiTile / 2)) {
                if (playerY + tinggiChar > tileY && playerY < tileY + tinggiTile) {
                    y = tileY - tinggiChar;
                    kecepatnY = 0;
                    melompat = false;
                } else if (playerY < tileY + tinggiTile && playerY + tinggiChar > tileY) {
                    y = tileY + tinggiTile;
                    kecepatnY = 0;
                    melompat = false;
                } else if (playerX + lebarChar > tileX && playerX < tileX + lebarTile) {
                    x = tileX - lebarChar;
                } else if (playerX < tileX + lebarTile && playerX + lebarChar > tileX) {
                    x = tileX + lebarTile;
                }
            }
        }
    }

    public void update() {
        updatePos();
        getBounds();
        cekCollision();
        cekCollisionTile();
    }

    private void cekCollision() {
        List<Meteor> toRemove = new ArrayList<>();
        for (Meteor box : gp.boxs) {
            if (isColliding(box)) {
                hp.hpBerkurang(25);
                toRemove.add(box);
                cekHp();
                gameOver = true;
            }
        }
        gp.boxs.removeAll(toRemove);
    }

    public boolean isGameOver() {
        return gameOver;
    }

    private boolean isColliding(Meteor box) {
        int playerX = x;
        int playerY = y;
        int lebarChar = 70;
        int tinggiChar = 70;

        int boxX = box.getX();
        int boxY = box.getY();
        int lebarBox = 100;
        int tingiBox = 100;

        if (playerX + lebarChar > boxX && playerX < boxX + lebarBox &&
                playerY + tinggiChar > boxY && playerY < boxY + tingiBox) {
            return true;
        }
        return false;
    }

    private void cekHp() {
        if (hp.getSisaHp() <= 0) {
            gp.gameOver = true;
            gp.stopGame();
        }
    }

    private void updatePos() {
        if (tombol.TekanLoncat == true || tombol.TekanBawah == true || tombol.TekanKiri == true
                || tombol.TekanKanan == true) {
            sp1++;
            if (tombol.TekanLoncat == true && !melompat) {
                kecepatnY = -20;
                gerakan = "up";
                melompat = true;
            } else if (tombol.TekanBawah == true) {
                y += speed;
                gerakan = "down";
            } else if (tombol.TekanKiri == true) {
                x -= speed;
                gerakan = "left";
            } else if (tombol.TekanKanan == true) {
                x += speed;
                gerakan = "right";
            }
            if (sp1 > 12) {
                if (sp2 == 1) {
                    sp2 = 2;
                } else if (sp2 == 2) {
                    sp2 = 1;
                }
                sp1 = 0;
            }
        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        hp.draw(g2, x, y - 10, 70, 4);
        switch (gerakan) {
            case "up":
                if (sp2 == 1) {
                    image = up1;
                }
                if (sp2 == 2) {
                    image = up2;
                }
            default:

            case "down":
                if (sp2 == 1) {
                    image = down1;
                }
                if (sp2 == 2) {
                    image = down2;
                }
                break;
            case "left":
                if (sp2 == 1) {
                    image = left1;
                }
                if (sp2 == 2) {
                    image = left2;
                }
                break;
            case "right":
                if (sp2 == 1) {
                    image = right1;
                }
                if (sp2 == 2) {
                    image = right2;
                }
                break;
        }
        g2.drawImage(image, x, y, 70, 70, null);
    }
}