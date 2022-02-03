package model;

public class User {
    private int id;
    private String kullaniciAdi;
    private String email;
    private String sifre;
    private String abonelik;
    private String ulke;
    private boolean adminlik;
    private int takipci;

    public User(int id, String kullaniciAdi, String email, String sifre, String abonelik, String ulke, boolean adminlik, int takipci) {
        setId(id);
        setKullaniciAdi(kullaniciAdi);
        setEmail(email);
        setSifre(sifre);
        setAbonelik(abonelik);
        setUlke(ulke);
        setAdminlik(adminlik);
        setTakipci(takipci);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKullaniciAdi() {
        return kullaniciAdi;
    }

    public void setKullaniciAdi(String kullaniciAdi) {
        this.kullaniciAdi = kullaniciAdi;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSifre() {
        return sifre;
    }

    public void setSifre(String sifre) {
        this.sifre = sifre;
    }

    public String getAbonelik() {
        return abonelik;
    }

    public void setAbonelik(String abonelik) {
        this.abonelik = abonelik;
    }

    public String getUlke() {
        return ulke;
    }

    public void setUlke(String ulke) {
        this.ulke = ulke;
    }

    public boolean isAdminlik() {
        return adminlik;
    }

    public void setAdminlik(boolean adminlik) {
        this.adminlik = adminlik;
    }

    public int getTakipci() {
        return takipci;
    }

    public void setTakipci(int takipci) {
        this.takipci = takipci;
    }
}