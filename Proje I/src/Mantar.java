import java.util.Random;

public class Mantar extends Obje {
    private final int sure = 7;
    private final int puan = 50;
    private final int miktar = 1;

    Mantar(int[][] map) {
        Random r = new Random();
        int w;
        int h;
        do {
            w = r.nextInt(13);
            h = r.nextInt(11);
        } while (map[h][w] != 1);
        this.setKonum(new Lokasyon(h, w));
    }

    public int getSure() {
        return sure;
    }

    public int getPuan() {
        return puan;
    }

    public int getMiktar() {
        return miktar;
    }
}