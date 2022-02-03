public class Lokasyon {
    private int satir;
    private int sutun;

    Lokasyon(int satir, int sutun){
        this.setSatir(satir);
        this.setSutun(sutun);
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
}