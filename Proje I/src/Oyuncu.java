public class Oyuncu extends Karakter{
    private int oyuncuID;
    private String oyuncuAdi;
    private Oyuncu oyuncuTur;
    private int skor;

    Oyuncu(){}

    void puaniGoster(){}

    public int getOyuncuID() {
        return oyuncuID;
    }

    public void setOyuncuID(int oyuncuID) {
        this.oyuncuID = oyuncuID;
    }

    public String getOyuncuAdi() {
        return oyuncuAdi;
    }

    public void setOyuncuAdi(String oyuncuAdi) {
        this.oyuncuAdi = oyuncuAdi;
    }

    public Oyuncu getOyuncuTur() {
        return oyuncuTur;
    }

    public void setOyuncuTur(Oyuncu oyuncuTur) {
        this.oyuncuTur = oyuncuTur;
    }

    public int getSkor() {
        return skor;
    }

    public void setSkor(int skor) {
        this.skor = skor;
    }
}