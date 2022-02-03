public class Tembel extends Oyuncu{
    private final int adim = 1;

    Tembel(){
        super.setOyuncuAdi("Tembel");
        super.setSkor(20);
    }

    public int getAdim() {
        return adim;
    }
}