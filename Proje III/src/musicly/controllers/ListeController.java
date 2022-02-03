package musicly.controllers;

import database.DbHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Sarki;
import model.User;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ListeController {
    private User user;
    int listID = -1;

    @FXML
    private VBox ekran;

    @FXML
    private Button btnAbonelik;

    @FXML
    private Button btnListeyeEkle;

    @FXML
    private Label lblUser;

    @FXML
    private Button btnCikis;

    @FXML
    private ImageView imgKapak;

    @FXML
    private Label lblListeAdi;

    @FXML
    private Label lblListeDetay;

    @FXML
    private VBox vboxListe;

    @FXML
    private HBox baslik;

    @FXML
    private TextField searchBar;

    public void listeCagir() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("../FXMLs/liste.fxml"));
            VBox vBox = fxmlLoader.load();
            ListeController listeController = fxmlLoader.getController();
            if (lblListeAdi.getText().equals("POP")) {
                listeController.setData("Pop", user);
            } else if (lblListeAdi.getText().equals("Jazz")) {
                listeController.setData("JAZZ", user);
            } else {
                listeController.setData("Klasik", user);
            }
            HomeController.anaEkran.setCenter(vBox);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void listedenCikar(ActionEvent event) {
        if (event.getSource() == btnListeyeEkle) {
            for(Sarki sarki : liste){
                Connection connection;
                DbHelper helper = new DbHelper();
                PreparedStatement statement;
                String sql = "delete from playlist_sarkilar where listeID = ? and sarkiID = ?";

                try {
                    connection = helper.getConnection();
                    statement = connection.prepareStatement(sql);
                    statement.setInt(1, listID);
                    statement.setInt(2, sarki.getID());
                    statement.executeUpdate();
                    btnListeyeEkle.setText("Tümünü Listeye Ekle");
                    btnListeyeEkle.setOnAction(this::tumunuEkle);
                    listeCagir();
                } catch (SQLException exception) {
                    helper.showErrorMessage(exception);
                }
            }
        }
    }

    @FXML
    void tumunuEkle(ActionEvent event) {
        for (Sarki sarki : liste) {
            boolean mevcutMu = listeSorgula(sarki);
            if(!mevcutMu){
                Connection connection;
                DbHelper helper = new DbHelper();
                PreparedStatement statement;
                String sql = "insert into playlist_sarkilar (listeID, sarkiID) values(?, ?)";

                try {
                    connection = helper.getConnection();
                    statement = connection.prepareStatement(sql);
                    statement.setInt(1, listID);
                    statement.setInt(2, sarki.getID());
                    statement.executeUpdate();
                    btnListeyeEkle.setText("Tümünü Listemden Çıkar");
                    btnListeyeEkle.setOnAction(this::listedenCikar);
                    listeCagir();
                } catch (SQLException exception) {
                    helper.showErrorMessage(exception);
                }
            }
        }
    }

    public boolean listeSorgula(Sarki sarki) {
        Connection connection;
        DbHelper helper = new DbHelper();
        PreparedStatement statement;
        ResultSet resultSet;
        String sql = "select ID from playlists where listeAdi = ? and userID = ?";

        try {
            connection = helper.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, lblListeAdi.getText());
            statement.setInt(2, LoginController.user.getId());
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                listID = resultSet.getInt("ID");
            }

            sql = "select * from playlist_sarkilar where sarkiID = ? and listeID = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, sarki.getID());
            statement.setInt(2, listID);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException exception) {
            helper.showErrorMessage(exception);
        }
        return false;
    }

    @FXML
    void close(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    void kucult(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    public void cikisYap(ActionEvent event) throws IOException {
        if (event.getSource() == btnCikis) {
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("../FXMLs/login.fxml")));
            stage.setScene(scene);
        }
    }

    @FXML
    void arama(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            if (!searchBar.getText().equals("")) sonucGetir(searchBar.getText());
        }
    }

    private void sonucGetir(String text) {
        List<User> ls = new ArrayList<>();
        DbHelper helper = new DbHelper();
        PreparedStatement statement;
        ResultSet resultSet;
        String sql = "select * from users where kullaniciAdi like '%" + text + "%'";

        try (Connection connection = helper.getConnection()) {
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                if (!resultSet.getBoolean("adminlik")) {
                    ls.add(new User(resultSet.getInt("ID"), resultSet.getString("kullaniciAdi"), resultSet.getString("email"), resultSet.getString("sifre"), resultSet.getString("abonelik"), resultSet.getString("ulke"), resultSet.getBoolean("adminlik"), resultSet.getInt("takipci")));
                }
            }
        } catch (SQLException exception) {
            helper.showErrorMessage(exception);
        }

        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("../FXMLs/arama.fxml"));
            VBox vBox = fxmlLoader.load();
            AramaController aramaController = fxmlLoader.getController();
            aramaController.setData(ls);
            ekran.getChildren().removeAll(ekran.getChildren());
            ekran.getChildren().add(vBox);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    List<Sarki> liste;

    public void setData(String listeTur, User kullanici) {
        user = kullanici;
        lblUser.setText(LoginController.user.getKullaniciAdi());
        btnAbonelik.setText(LoginController.user.getAbonelik());
        liste = new ArrayList<>(listeGetir(listeTur, kullanici.getId()));

        try {
            int i = 1;
            for (Sarki sarki : liste) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("../FXMLs/sarkiListe.fxml"));

                HBox hBox = fxmlLoader.load();
                SarkiListeController sarkiListeController = fxmlLoader.getController();
                sarkiListeController.setData(sarki, i);

                vboxListe.getChildren().add(hBox);
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        String file;
        if (listeTur.equals("Pop")) {
            file = "../../img/pop.png";
        } else if (listeTur.equals("Jazz")) {
            file = "../../img/jazz.png";
        } else {
            file = "../../img/klasik.png";
        }

        DbHelper helper = new DbHelper();
        PreparedStatement statement;
        ResultSet resultSet;
        String sql = "select takipci from users where ID = ?";

        try (Connection connection = helper.getConnection()) {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, kullanici.getId());
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                kullanici.setTakipci(resultSet.getInt("takipci"));
            }
        } catch (SQLException exception) {
            helper.showErrorMessage(exception);
        }

        Image image = new Image(getClass().getResourceAsStream(file));
        imgKapak.setImage(image);
        lblListeAdi.setText(listeTur.toUpperCase());

        boolean tumuVarMi = true;
        for(Sarki sarki : liste){
            if(!listeSorgula(sarki)) tumuVarMi = false;
        }

        if(tumuVarMi){
            btnListeyeEkle.setText("Tümünü Listemden Çıkar");
            btnListeyeEkle.setOnAction(this::listedenCikar);
        }

        if (kullanici.getId() == LoginController.user.getId()) baslik.getChildren().remove(btnListeyeEkle);
        if (kullanici.getAbonelik().equals("NORMAL")) {
            lblListeDetay.setText(kullanici.getKullaniciAdi().toUpperCase() + " - " + liste.size() + " ŞARKI");
        } else
            lblListeDetay.setText(kullanici.getKullaniciAdi().toUpperCase() + " - " + kullanici.getTakipci() + " TAKİPÇİ - " + liste.size() + " ŞARKI");
    }

    private List<Sarki> listeGetir(String tur, int userID) {
        List<Sarki> ls = new ArrayList<>();
        List<Integer> sarkiIDs = new ArrayList<>();
        int playlistID = -1;

        DbHelper helper = new DbHelper();
        PreparedStatement statement;
        ResultSet resultSet;
        String sql = "select ID from playlists where listeAdi = ? and userID = ?";

        try (Connection connection = helper.getConnection()) {
            statement = connection.prepareStatement(sql);
            statement.setString(1, tur);
            statement.setInt(2, userID);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                playlistID = resultSet.getInt("ID");
            }
        } catch (SQLException exception) {
            helper.showErrorMessage(exception);
        }

        sql = "select sarkiID from playlist_sarkilar where listeID = ?";

        try (Connection connection = helper.getConnection()) {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, playlistID);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                sarkiIDs.add(resultSet.getInt("sarkiID"));
            }
        } catch (SQLException exception) {
            helper.showErrorMessage(exception);
        }

        sql = "select * from sarki where ID = ?";

        try (Connection connection = helper.getConnection()) {
            statement = connection.prepareStatement(sql);
            for (int i : sarkiIDs) {
                statement.setInt(1, i);
                resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    ls.add(new Sarki(resultSet.getInt("ID"), resultSet.getString("sarkiAdi"), resultSet.getInt("sanatciID"), resultSet.getString("tarih"), resultSet.getInt("albumID"), resultSet.getString("tur"), resultSet.getString("sure"), resultSet.getInt("dinlenme")));
                }
            }
        } catch (SQLException exception) {
            helper.showErrorMessage(exception);
        }

        return ls;
    }
}