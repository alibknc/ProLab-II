public class Gozluklu extends Oyuncu{
    private final int adim = 2;

    Gozluklu(){
        super.setOyuncuAdi("Gözlüklü");
        super.setSkor(20);
    }

    public int getAdim() {
        return adim;
    }
}