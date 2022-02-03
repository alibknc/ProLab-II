public class Node implements Comparable{
    int satir;
    int sutun;
    int deger;
    int mesafe = -1;
    boolean gecildiMi = false;

    Node sag;
    Node sol;
    Node asagi;
    Node yukari;
    Node gelinen;

    Node(){}

    @Override
    public int compareTo(Object dugum) {
        int compareage=((Node)dugum).getMesafe();
        return this.mesafe-compareage;
    }

    public int getSatir() {
        return satir;
    }

    public void setSatir(int satir) {
        this.satir = satir;
    }

    public int getSutun() {
        return sutun;
    }

    public void setSutun(int sutun) {
        this.sutun = sutun;
    }

    public int getDeger() {
        return deger;
    }

    public void setDeger(int deger) {
        this.deger = deger;
    }

    public int getMesafe() {
        return mesafe;
    }

    public void setMesafe(int mesafe) {
        this.mesafe = mesafe;
    }

    public boolean isGecildiMi() {
        return gecildiMi;
    }

    public void setGecildiMi(boolean gecildiMi) {
        this.gecildiMi = gecildiMi;
    }

    public Node getSag() {
        return sag;
    }

    public void setSag(Node sag) {
        this.sag = sag;
    }

    public Node getSol() {
        return sol;
    }

    public void setSol(Node sol) {
        this.sol = sol;
    }

    public Node getAsagi() {
        return asagi;
    }

    public void setAsagi(Node asagi) {
        this.asagi = asagi;
    }

    public Node getYukari() {
        return yukari;
    }

    public void setYukari(Node yukari) {
        this.yukari = yukari;
    }

    public Node getGelinen() {
        return gelinen;
    }

    public void setGelinen(Node gelinen) {
        this.gelinen = gelinen;
    }

}