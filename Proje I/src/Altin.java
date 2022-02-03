import java.util.Random;

public class Altin extends Obje {
    private final int sure = 5;
    private final int puan = 5;
    private final int miktar = 5;

    Altin(){}

    Lokasyon altinOlustur(int[][] map) {
        Random r = new Random();
        int w;
        int h;

        do {
            w = r.nextInt(13);
            h = r.nextInt(11);
        } while (map[h][w] != 1);
        return new Lokasyon(h, w);
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
