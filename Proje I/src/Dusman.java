import java.util.ArrayList;
import java.util.Collections;

public class Dusman extends Karakter{
    private int dusmanID;
    private String dusmanAdi;
    private Oyuncu dusmanTur;
    private ArrayList<Node> komsular;

    Dusman(){}

    void enKisaYol(Node start, Node end){
        ArrayList<Node> komsular_gecici = new ArrayList();
        if(!komsular.isEmpty()){
            komsular.remove(start);
            start.gecildiMi = true;
        }

        if(start.yukari != null && start.yukari.deger != 0) komsular_gecici.add(start.yukari);
        if(start.asagi != null && start.asagi.deger != 0) komsular_gecici.add(start.asagi);
        if(start.sag != null && start.sag.deger != 0) komsular_gecici.add(start.sag);
        if(start.sol != null && start.sol.deger != 0) komsular_gecici.add(start.sol);

        for(Node a : komsular_gecici){
            if(start.mesafe == -1){
                a.mesafe = 1;
                a.gelinen = start;
            }else if(a.mesafe != -1){
                if((start.mesafe + 1) <= a.mesafe){
                    a.mesafe = start.mesafe + 1;
                    a.gelinen = start;
                }
            }else{
                a.mesafe = start.mesafe + 1;
                a.gelinen = start;
            }
            if(!komsular.contains(a) && !a.gecildiMi) komsular.add(a);
        }
        Collections.sort(komsular);
        if(komsular.get(0) != end) enKisaYol(komsular.get(0), end);
    }

    public int getDusmanID() {
        return dusmanID;
    }

    public void setDusmanID(int dusmanID) {
        this.dusmanID = dusmanID;
    }

    public String getDusmanAdi() {
        return dusmanAdi;
    }

    public void setDusmanAdi(String dusmanAdi) {
        this.dusmanAdi = dusmanAdi;
    }

    public Oyuncu getDusmanTur() {
        return dusmanTur;
    }

    public void setDusmanTur(Oyuncu dusmanTur) {
        this.dusmanTur = dusmanTur;
    }

    public ArrayList<Node> getKomsular() {
        return komsular;
    }

    public void setKomsular(ArrayList<Node> komsular) {
        this.komsular = komsular;
    }

}
