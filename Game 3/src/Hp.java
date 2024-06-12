import java.awt.Color;
import java.awt.Graphics;

public class Hp {
    private int maxHp;
    public int sisaHp;

    public Hp(int maxHp) {
        this.maxHp = maxHp;
        this.sisaHp = maxHp;
    }

    public void hpBerkurang(int jumlah) {
        sisaHp -= jumlah;
        if (sisaHp < 0) {
            sisaHp = 0;
        }
    }

    public void hpTambah(int jumlh) {
        sisaHp += jumlh;
        if (sisaHp > maxHp) {
            sisaHp = maxHp;
        }
    }

    public void draw(Graphics g, int x, int y, int lebar, int tinggi) {
        g.setColor(Color.RED);
        int lebarrHp = (int) ((sisaHp / (double) maxHp) * lebar);
        g.fillRect(x, y, lebarrHp, tinggi);
    }

    public int getSisaHp() {
        return sisaHp;
    }
}
