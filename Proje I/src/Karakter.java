public class Karakter {
    private int id;
    private String isim;
    private Karakter tur;
    private Lokasyon lokasyon;

    Karakter(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIsim() {
        return isim;
    }

    public void setIsim(String isim) {
        this.isim = isim;
    }

    public Karakter getTur() {
        return tur;
    }

    public void setTur(Karakter tur) {
        this.tur = tur;
    }

    public Lokasyon getLokasyon() {
        return lokasyon;
    }

    public void setLokasyon(Lokasyon lokasyon) {
        this.lokasyon = lokasyon;
    }

}