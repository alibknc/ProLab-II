import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.effect.DropShadow;
import javafx.scene.shape.Rectangle;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Main extends Application {
    Scene scene;
    Group group = new Group();
    Node[][] dugumler = new Node[11][13];
    boolean oyunBittiMi = false;

    Lokasyon aKapisi = new Lokasyon(0, 3);
    Lokasyon bKapisi = new Lokasyon(0, 10);
    Lokasyon cKapisi = new Lokasyon(5, 0);
    Lokasyon dKapisi = new Lokasyon(10, 3);
    Lokasyon cikis = new Lokasyon(7, 12);
    Lokasyon start = new Lokasyon(5, 6);
    Lokasyon d1Kapisi;
    Lokasyon d2Kapisi;

    int secilenOyuncuIndex;
    Oyuncu oyuncu;
    Dusman dusman1;
    Dusman dusman2;
    String[] tokens;
    ArrayList<Node> dusman1Path = new ArrayList<>();
    ArrayList<Node> dusman2Path = new ArrayList<>();
    ImageView oyuncuKarakterResim;
    ImageView dusman1KarakterResim;
    ImageView dusman2KarakterResim;
    ImageView sirineResim;
    Text puan;
    Rectangle[][] haritaEleman = new Rectangle[11][13];
    ArrayList<Object> oyuncuSecimEleman = new ArrayList<>();
    ArrayList<ImageView> altinResimler = new ArrayList<>();
    ImageView mantarResim;
    ArrayList<Altin> altinlar = new ArrayList<>();
    Mantar mantar;
    int[][] map = new int[11][13];
    int[][] mapAltin = new int[11][13];
    Timer altinSure = new Timer();
    Timer mantarSure = new Timer();
    int toplanilanAltin = 0;
    int toplanilanMantar = 0;

    @Override
    public void start(Stage primaryStage) {
        scene = new Scene(group, 1000, 650, Color.WHITE);

        haritaOku();
        oyuncuSecimEkrani();

        primaryStage.setTitle("Şirinler");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    void oyuncuSecimEkrani() {
        int x = 25;
        int y = 25;
        int width = 450;
        int height = 600;

        for (int i = 0; i < 2; i++) {
            int index = i;

            Rectangle rect = new Rectangle();
            rect.setX(x);
            rect.setY(y);
            rect.setWidth(width);
            rect.setHeight(height);
            rect.setFill(Color.WHITE);
            rect.setStroke(Color.BLACK);
            rect.setStrokeWidth(2);
            rect.setSmooth(true);
            rect.setEffect(new DropShadow());
            oyuncuSecimEleman.add(rect);

            String dosyaIsmi = "images/oyuncu" + (index + 1) + ".jpeg";
            Image img = new Image(dosyaIsmi);
            ImageView imgview = new ImageView(img);
            imgview.setX(x);
            imgview.setY(y);
            imgview.setFitHeight(height);
            imgview.setFitWidth(width);
            oyuncuSecimEleman.add(imgview);

            EventHandler<MouseEvent> handler = new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    secilenOyuncuIndex = index + 1;
                    oyuncuAta(index);
                }
            };

            imgview.setOnMouseClicked(handler);
            group.getChildren().addAll(rect, imgview);
            x = x + width + 50;
        }
    }

    void oyuncuAta(int secim) {
        if (secim == 0) {
            oyuncu = new Gozluklu();
        } else {
            oyuncu = new Tembel();
        }
        oyuncu.setLokasyon(start);

        for (Object a : oyuncuSecimEleman) {
            group.getChildren().remove(a);
        }

        haritaCiz();
    }

    void haritaCiz() {
        int x = 25;
        int y = 75;

        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 13; j++) {
                Rectangle rect = new Rectangle();
                rect.setX(x);
                rect.setY(y);
                rect.setWidth(50);
                rect.setHeight(50);
                if (map[i][j] == 0)
                    rect.setFill(Color.GRAY);
                else if (i == start.getSatir() && j == start.getSutun())
                    rect.setFill(Color.BLUE);
                else if (i == aKapisi.getSatir() && j == aKapisi.getSutun() ||
                        i == bKapisi.getSatir() && j == bKapisi.getSutun() ||
                        i == cKapisi.getSatir() && j == cKapisi.getSutun() ||
                        i == dKapisi.getSatir() && j == dKapisi.getSutun())
                    rect.setFill(Color.PINK);
                else
                    rect.setFill(Color.WHITE);
                rect.setStroke(Color.BLACK);
                rect.setStrokeWidth(1);
                rect.setSmooth(true);
                group.getChildren().add(rect);
                haritaEleman[i][j] = rect;
                x = x + 50;
            }
            x = 25;
            y = y + 50;
        }

        puan = new Text();
        puan.setFont(Font.font("Liberation Serif", FontWeight.BOLD, FontPosture.REGULAR, 28));
        puan.setFill(Color.RED);
        puan.setText("PUAN: " + oyuncu.getSkor());
        puan.setX(700);
        puan.setY(50);
        group.getChildren().add(puan);

        karakterleriOlustur();
    }

    void haritaOku() {
        int line = 0;

        try {
            FileInputStream fstream = new FileInputStream("src/harita.txt");
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            while ((strLine = br.readLine()) != null) {
                if (line < 2) {
                    tokens = strLine.split("Karakter:|,Kapi:");
                    if (tokens[1].equals("Azman")) {
                        dusman1 = new Azman();
                        switch (tokens[2]) {
                            case "A":
                                dusman1.setLokasyon(new Lokasyon(aKapisi.getSatir(), aKapisi.getSutun()));
                                d1Kapisi = aKapisi;
                                break;
                            case "B":
                                dusman1.setLokasyon(new Lokasyon(bKapisi.getSatir(), bKapisi.getSutun()));
                                d1Kapisi = bKapisi;
                                break;
                            case "C":
                                dusman1.setLokasyon(new Lokasyon(cKapisi.getSatir(), cKapisi.getSutun()));
                                d1Kapisi = cKapisi;
                                break;
                            case "D":
                                dusman1.setLokasyon(new Lokasyon(dKapisi.getSatir(), dKapisi.getSutun()));
                                d1Kapisi = dKapisi;
                                break;
                        }
                    } else {
                        dusman2 = new Gargamel();
                        switch (tokens[2]) {
                            case "A":
                                dusman2.setLokasyon(new Lokasyon(aKapisi.getSatir(), aKapisi.getSutun()));
                                d2Kapisi = aKapisi;
                                break;
                            case "B":
                                dusman2.setLokasyon(new Lokasyon(bKapisi.getSatir(), bKapisi.getSutun()));
                                d2Kapisi = bKapisi;
                                break;
                            case "C":
                                dusman2.setLokasyon(new Lokasyon(cKapisi.getSatir(), cKapisi.getSutun()));
                                d2Kapisi = cKapisi;
                                break;
                            case "D":
                                dusman2.setLokasyon(new Lokasyon(dKapisi.getSatir(), dKapisi.getSutun()));
                                d2Kapisi = dKapisi;
                                break;
                        }
                    }
                } else {
                    tokens = strLine.split("\t");
                    int i = 0;
                    for (String v : tokens) {
                        map[line - 2][i] = Integer.parseInt(v);
                        i = i + 1;
                    }
                }
                line++;
            }
            in.close();
        } catch (Exception e) {
            System.err.println("Hata: " + e.getMessage());
        }

        dugumOlustur();
    }

    void dugumOlustur() {
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 13; j++) {
                Node n = new Node();
                if (i == 0) {
                    n.yukari = null;
                    if (j == 0) {
                        n.sol = null;
                    } else if (j == 12) {
                        n.sag = null;
                        n.sol = dugumler[i][j - 1];
                        dugumler[i][j - 1].sag = n;
                    } else {
                        n.sol = dugumler[i][j - 1];
                        dugumler[i][j - 1].sag = n;
                    }
                    n.satir = i;
                    n.sutun = j;
                    n.deger = map[i][j];
                } else if (i == 10) {
                    n.asagi = null;
                    n.yukari = dugumler[i - 1][j];
                    dugumler[i - 1][j].asagi = n;
                    if (j == 0) {
                        n.sol = null;
                    } else if (j == 12) {
                        n.sag = null;
                        n.sol = dugumler[i][j - 1];
                        dugumler[i][j - 1].sag = n;
                    } else {
                        n.sol = dugumler[i][j - 1];
                        dugumler[i][j - 1].sag = n;
                    }
                    n.satir = i;
                    n.sutun = j;
                    n.deger = map[i][j];
                } else {
                    n.yukari = dugumler[i - 1][j];
                    dugumler[i - 1][j].asagi = n;
                    if (j == 0) {
                        n.sol = null;
                    } else if (j == 12) {
                        n.sag = null;
                        n.sol = dugumler[i][j - 1];
                        dugumler[i][j - 1].sag = n;
                    } else {
                        n.sol = dugumler[i][j - 1];
                        dugumler[i][j - 1].sag = n;
                    }
                    n.satir = i;
                    n.sutun = j;
                    n.deger = map[i][j];
                }
                dugumler[i][j] = n;
            }
        }
    }

    void karakterleriOlustur() {
        String dosyaIsmi = "images/oyuncu" + secilenOyuncuIndex + ".jpeg";
        Image img = new Image(dosyaIsmi);
        oyuncuKarakterResim = new ImageView(img);
        oyuncuKarakterResim.setX((oyuncu.getLokasyon().getSutun() * 50) + 25);
        oyuncuKarakterResim.setY((oyuncu.getLokasyon().getSatir() * 50) + 75);
        oyuncuKarakterResim.setFitHeight(50);
        oyuncuKarakterResim.setFitWidth(50);
        group.getChildren().add(oyuncuKarakterResim);

        dosyaIsmi = "images/sirine.jpeg";
        img = new Image(dosyaIsmi);
        sirineResim = new ImageView(img);
        sirineResim.setX(675);
        sirineResim.setY(425);
        sirineResim.setFitHeight(50);
        sirineResim.setFitWidth(50);
        group.getChildren().add(sirineResim);

        dosyaIsmi = "images/azman.jpeg";
        img = new Image(dosyaIsmi);
        dusman1KarakterResim = new ImageView(img);
        dusman1KarakterResim.setX((dusman1.getLokasyon().getSutun() * 50) + 25);
        dusman1KarakterResim.setY((dusman1.getLokasyon().getSatir() * 50) + 75);
        dusman1KarakterResim.setFitHeight(50);
        dusman1KarakterResim.setFitWidth(50);
        group.getChildren().add(dusman1KarakterResim);

        dosyaIsmi = "images/gargamel.jpeg";
        img = new Image(dosyaIsmi);
        dusman2KarakterResim = new ImageView(img);
        dusman2KarakterResim.setX((dusman2.getLokasyon().getSutun() * 50) + 25);
        dusman2KarakterResim.setY((dusman2.getLokasyon().getSatir() * 50) + 75);
        dusman2KarakterResim.setFitHeight(50);
        dusman2KarakterResim.setFitWidth(50);
        group.getChildren().add(dusman2KarakterResim);

        for (int i = 0; i < 11; i++)
            for (int c = 0; c < 13; c++)
                mapAltin[i][c] = map[i][c];

        mapAltin[oyuncu.getLokasyon().getSatir()][oyuncu.getLokasyon().getSutun()] = 0;
        mapAltin[dusman1.getLokasyon().getSatir()][dusman1.getLokasyon().getSutun()] = 0;
        mapAltin[dusman2.getLokasyon().getSatir()][dusman2.getLokasyon().getSutun()] = 0;
        mapAltin[aKapisi.getSatir()][aKapisi.getSutun()] = 0;
        mapAltin[bKapisi.getSatir()][bKapisi.getSutun()] = 0;
        mapAltin[cKapisi.getSatir()][cKapisi.getSutun()] = 0;
        mapAltin[dKapisi.getSatir()][dKapisi.getSutun()] = 0;
        mapAltin[cikis.getSatir()][cikis.getSutun()] = 0;

        objeOlusturucu();
        kisaYoluCiz();

        scene.setOnKeyPressed(e -> {
            if (e.getCode().equals(KeyCode.UP)) {
                oyuncuHareket("up");
            } else if (e.getCode() == KeyCode.DOWN) {
                oyuncuHareket("down");
            } else if (e.getCode() == KeyCode.LEFT) {
                oyuncuHareket("left");
            } else if (e.getCode() == KeyCode.RIGHT) {
                oyuncuHareket("right");
            }
        });
    }

    void puanGuncelle() {
        puan.setText("PUAN: " + (oyuncu.getSkor()) + "\nToplanılan Altın: " + toplanilanAltin + "\nToplanılan Mantar: " + toplanilanMantar);

        if (oyuncu.getLokasyon().getSatir() == cikis.getSatir() && oyuncu.getLokasyon().getSutun() == cikis.getSutun()) {
            haritaSil();
            Text text = new Text();
            text.setFont(Font.font("Liberation Serif", FontWeight.BOLD, FontPosture.REGULAR, 36));
            text.setFill(Color.BLACK);
            text.setText("KAZANDINIZ");
            oyunBittiMi = true;
            text.setX(300);
            text.setY(300);
            group.getChildren().add(text);
            altinSure.cancel();
            mantarSure.cancel();
        }

        if (oyuncu.getSkor() <= 0) {
            haritaSil();
            Text text = new Text();
            text.setFont(Font.font("Liberation Serif", FontWeight.BOLD, FontPosture.REGULAR, 36));
            text.setFill(Color.BLACK);
            text.setText("KAYBETTİNİZ");
            oyunBittiMi = true;
            text.setX(300);
            text.setY(300);
            group.getChildren().add(text);
            altinSure.cancel();
            mantarSure.cancel();
        }
    }

    private void haritaSil() {
        group.getChildren().removeAll(dusman1KarakterResim, dusman2KarakterResim, oyuncuKarakterResim, sirineResim, mantarResim);
        group.getChildren().removeAll(altinResimler);
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 13; j++) {
                group.getChildren().remove(haritaEleman[i][j]);
            }
        }
    }

    void oyuncuHareket(String yon) {
        Lokasyon lokasyon = oyuncu.getLokasyon();
        switch (yon) {
            case "up":
                if (oyuncu.getOyuncuAdi().equals("Gözlüklü") && (lokasyon.getSatir() - 2) >= 0 && map[lokasyon.getSatir() - 1][lokasyon.getSutun()] == 1 && map[lokasyon.getSatir() - 2][lokasyon.getSutun()] == 1) {
                    oyuncu.getLokasyon().setSatir(lokasyon.getSatir() - 2);
                    oyuncuKarakterResim.setY(oyuncuKarakterResim.getY() - 100);
                    dusmanHareket();
                } else if (oyuncu.getOyuncuAdi().equals("Tembel") && (lokasyon.getSatir() - 1) >= 0 && map[lokasyon.getSatir() - 1][lokasyon.getSutun()] == 1) {
                    oyuncu.getLokasyon().setSatir(lokasyon.getSatir() - 1);
                    oyuncuKarakterResim.setY(oyuncuKarakterResim.getY() - 50);
                    dusmanHareket();
                }
                break;
            case "down":
                if (oyuncu.getOyuncuAdi().equals("Gözlüklü") && (lokasyon.getSatir() + 2) <= 10 && map[lokasyon.getSatir() + 1][lokasyon.getSutun()] == 1 && map[lokasyon.getSatir() + 2][lokasyon.getSutun()] == 1) {
                    oyuncu.getLokasyon().setSatir(lokasyon.getSatir() + 2);
                    oyuncuKarakterResim.setY(oyuncuKarakterResim.getY() + 100);
                    dusmanHareket();
                } else if (oyuncu.getOyuncuAdi().equals("Tembel") && (lokasyon.getSatir() + 1) <= 10 && map[lokasyon.getSatir() + 1][lokasyon.getSutun()] == 1) {
                    oyuncu.getLokasyon().setSatir(lokasyon.getSatir() + 1);
                    oyuncuKarakterResim.setY(oyuncuKarakterResim.getY() + 50);
                    dusmanHareket();
                }
                break;
            case "right":
                if (oyuncu.getOyuncuAdi().equals("Gözlüklü") && (lokasyon.getSutun() + 2) <= 12 && map[lokasyon.getSatir()][lokasyon.getSutun() + 1] == 1 && map[lokasyon.getSatir()][lokasyon.getSutun() + 2] == 1) {
                    oyuncu.getLokasyon().setSutun(lokasyon.getSutun() + 2);
                    oyuncuKarakterResim.setX(oyuncuKarakterResim.getX() + 100);
                    dusmanHareket();
                } else if (oyuncu.getOyuncuAdi().equals("Tembel") && (lokasyon.getSutun() + 1) <= 12 && map[lokasyon.getSatir()][lokasyon.getSutun() + 1] == 1) {
                    oyuncu.getLokasyon().setSutun(lokasyon.getSutun() + 1);
                    oyuncuKarakterResim.setX(oyuncuKarakterResim.getX() + 50);
                    dusmanHareket();
                }
                break;
            case "left":
                if (oyuncu.getOyuncuAdi().equals("Gözlüklü") && (lokasyon.getSutun() - 2) >= 0 && map[lokasyon.getSatir()][lokasyon.getSutun() - 1] == 1 && map[lokasyon.getSatir()][lokasyon.getSutun() - 2] == 1) {
                    oyuncu.getLokasyon().setSutun(lokasyon.getSutun() - 2);
                    oyuncuKarakterResim.setX(oyuncuKarakterResim.getX() - 100);
                    dusmanHareket();
                } else if (oyuncu.getOyuncuAdi().equals("Tembel") && (lokasyon.getSutun() - 1) >= 0 && map[lokasyon.getSatir()][lokasyon.getSutun() - 1] == 1) {
                    oyuncu.getLokasyon().setSutun(lokasyon.getSutun() - 1);
                    oyuncuKarakterResim.setX(oyuncuKarakterResim.getX() - 50);
                    dusmanHareket();
                }
                break;
        }

        objeToplama();
    }

    private void objeToplama() {
        if (mantar != null) {
            if (oyuncu.getLokasyon().getSatir() == mantar.getKonum().getSatir() && oyuncu.getLokasyon().getSutun() == mantar.getKonum().getSutun()) {
                group.getChildren().remove(mantarResim);
                oyuncu.setSkor(oyuncu.getSkor() + mantar.getPuan());
                toplanilanMantar++;
                puanGuncelle();
                mantar = null;
            }
        }

        if (!altinlar.isEmpty()) {
            for (int i = 0; i < altinlar.size(); i++) {
                Altin a = altinlar.get(i);
                if (oyuncu.getLokasyon().getSatir() == a.getKonum().getSatir() && oyuncu.getLokasyon().getSutun() == a.getKonum().getSutun()) {
                    group.getChildren().remove(altinResimler.get(i));
                    altinlar.remove(a);
                    oyuncu.setSkor(oyuncu.getSkor() + a.getPuan());
                    toplanilanAltin++;
                    puanGuncelle();
                }
            }
        }
    }

    void mantarOlustur() {
        String mantarDosya = "images/mantar.png";
        mantar = new Mantar(mapAltin);

        Image img = new Image(mantarDosya);
        ImageView iv = new ImageView(img);
        iv.setX((mantar.getKonum().getSutun() * 50) + 25);
        iv.setY((mantar.getKonum().getSatir() * 50) + 75);
        iv.setFitHeight(50);
        iv.setFitWidth(50);
        group.getChildren().add(iv);
        mantarResim = iv;

        objeToplama();
    }

    void altinOlustur() {
        String altinDosya = "images/altin.png";
        Altin altin = new Altin();
        Lokasyon l;
        boolean b;

        for (int i = 0; i < 11; i++)
            for (int c = 0; c < 13; c++)
                mapAltin[i][c] = map[i][c];

        mapAltin[oyuncu.getLokasyon().getSatir()][oyuncu.getLokasyon().getSutun()] = 0;
        mapAltin[dusman1.getLokasyon().getSatir()][dusman1.getLokasyon().getSutun()] = 0;
        mapAltin[dusman2.getLokasyon().getSatir()][dusman2.getLokasyon().getSutun()] = 0;
        mapAltin[aKapisi.getSatir()][aKapisi.getSutun()] = 0;
        mapAltin[bKapisi.getSatir()][bKapisi.getSutun()] = 0;
        mapAltin[cKapisi.getSatir()][cKapisi.getSutun()] = 0;
        mapAltin[dKapisi.getSatir()][dKapisi.getSutun()] = 0;
        mapAltin[cikis.getSatir()][cikis.getSutun()] = 0;

        for (int i = 0; i < altin.getMiktar(); i++) {
            Altin tmp = new Altin();
            do {
                l = altin.altinOlustur(map);
                b = altinKontrolEt(l);
                mapAltin[l.getSatir()][l.getSatir()] = 0;
            } while ((mantar != null && l.getSutun() == mantar.getKonum().getSutun() && l.getSatir() == mantar.getKonum().getSatir()) || !b);

            Image img = new Image(altinDosya);
            ImageView iv = new ImageView(img);
            iv.setX((l.getSutun() * 50) + 25);
            iv.setY((l.getSatir() * 50) + 75);
            iv.setFitHeight(50);
            iv.setFitWidth(50);
            group.getChildren().add(iv);
            altinResimler.add(iv);

            tmp.setKonum(l);
            altinlar.add(tmp);
        }
        objeToplama();
    }

    void objeOlusturucu() {
        altinSure.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    if (!altinResimler.isEmpty()) {
                        group.getChildren().removeAll(altinResimler);
                        altinResimler.removeAll(altinResimler);
                    }
                    if (!altinlar.isEmpty()) {
                        altinlar.removeAll(altinlar);
                    }
                    altinOlustur();
                });
            }
        }, 1000, 7000);

        mantarSure.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    if (mantarResim != null) {
                        group.getChildren().remove(mantarResim);
                        mantarResim = null;
                    }
                    if (mantar != null) mantar = null;
                    mantarOlustur();
                });
            }
        }, 900, 10000);
    }

    boolean altinKontrolEt(Lokasyon l) {
        for (Altin tmp : altinlar) {
            if (tmp.getKonum().getSatir() == l.getSatir() && tmp.getKonum().getSutun() == l.getSutun()) {
                return false;
            }
            if ((l.getSutun() == dusman1.getLokasyon().getSutun() && l.getSatir() == dusman1.getLokasyon().getSatir()) || (l.getSutun() == dusman2.getLokasyon().getSutun() && l.getSatir() == dusman2.getLokasyon().getSatir())) {
                return false;
            }
        }
        return true;
    }

    void kisaYoluCiz() {
        dugumOlustur();

        dusman1.setKomsular(new ArrayList<>());
        dusman1.enKisaYol(dugumler[dusman1.getLokasyon().getSatir()][dusman1.getLokasyon().getSutun()], dugumler[oyuncu.getLokasyon().getSatir()][oyuncu.getLokasyon().getSutun()]);
        Node tmp = dusman1.getKomsular().get(0);
        while (tmp != dugumler[dusman1.getLokasyon().getSatir()][dusman1.getLokasyon().getSutun()]) {
            dusman1Path.add(tmp);
            haritaEleman[tmp.getSatir()][tmp.getSutun()].setFill(Color.LIGHTBLUE);
            tmp = tmp.gelinen;
        }

        dugumOlustur();

        dusman2.setKomsular(new ArrayList<>());
        dusman2.enKisaYol(dugumler[dusman2.getLokasyon().getSatir()][dusman2.getLokasyon().getSutun()], dugumler[oyuncu.getLokasyon().getSatir()][oyuncu.getLokasyon().getSutun()]);
        tmp = dusman2.getKomsular().get(0);
        while (tmp != dugumler[dusman2.getLokasyon().getSatir()][dusman2.getLokasyon().getSutun()]) {
            dusman2Path.add(tmp);
            haritaEleman[tmp.getSatir()][tmp.getSutun()].setFill(Color.LIGHTGREEN);
            tmp = tmp.gelinen;
        }
    }

    void kisaYoluTemizle() {
        for (Node a : dusman1Path) {
            haritaEleman[a.getSatir()][a.getSutun()].setFill(Color.WHITE);
        }
        for (Node a : dusman2Path) {
            haritaEleman[a.getSatir()][a.getSutun()].setFill(Color.WHITE);
        }

        dusman1Path.removeAll(dusman1Path);
        dusman2Path.removeAll(dusman2Path);
    }

    void dusmanHareket() {
        kisaYoluTemizle();
        kisaYoluCiz();

        if (dusman2Path.size() >= 2) {
            haritaEleman[dusman2Path.get(dusman2Path.size() - 1).getSatir()][dusman2Path.get(dusman2Path.size() - 1).getSutun()].setFill(Color.WHITE);

            dusman2.getLokasyon().setSatir(dusman2Path.get(dusman2Path.size() - 2).getSatir());
            dusman2.getLokasyon().setSutun(dusman2Path.get(dusman2Path.size() - 2).getSutun());

            dusman2KarakterResim.setX((dusman2.getLokasyon().getSutun() * 50) + 25);
            dusman2KarakterResim.setY((dusman2.getLokasyon().getSatir() * 50) + 75);
        }

        if (dusman1Path.size() >= 1) {
            dusman1.getLokasyon().setSatir(dusman1Path.get(dusman1Path.size() - 1).getSatir());
            dusman1.getLokasyon().setSutun(dusman1Path.get(dusman1Path.size() - 1).getSutun());

            dusman1KarakterResim.setX((dusman1.getLokasyon().getSutun() * 50) + 25);
            dusman1KarakterResim.setY((dusman1.getLokasyon().getSatir() * 50) + 75);
        }

        if (oyuncu.getLokasyon().getSutun() == dusman2.getLokasyon().getSutun() && oyuncu.getLokasyon().getSatir() == dusman2.getLokasyon().getSatir()) {
            dusman2.getLokasyon().setSatir(d2Kapisi.getSatir());
            dusman2.getLokasyon().setSutun(d2Kapisi.getSutun());
            oyuncu.setSkor(oyuncu.getSkor() - 15);
            puanGuncelle();
            dusman2KarakterResim.setX((dusman2.getLokasyon().getSutun() * 50) + 25);
            dusman2KarakterResim.setY((dusman2.getLokasyon().getSatir() * 50) + 75);
            dusman2Path.removeAll(dusman2Path);
        }

        if (oyuncu.getLokasyon().getSutun() == dusman1.getLokasyon().getSutun() && oyuncu.getLokasyon().getSatir() == dusman1.getLokasyon().getSatir()) {
            dusman1.getLokasyon().setSatir(d1Kapisi.getSatir());
            dusman1.getLokasyon().setSutun(d1Kapisi.getSutun());
            oyuncu.setSkor(oyuncu.getSkor() - 5);
            puanGuncelle();
            dusman1KarakterResim.setX((dusman1.getLokasyon().getSutun() * 50) + 25);
            dusman1KarakterResim.setY((dusman1.getLokasyon().getSatir() * 50) + 75);
            dusman1Path.removeAll(dusman1Path);
        }
        puanGuncelle();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
