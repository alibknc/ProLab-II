package musicly.controllers;

import database.DbHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Sarki;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class GirisController implements Initializable {
    @FXML
    private VBox ekran;

    @FXML
    private VBox imgSponsor;

    @FXML
    private Label lblUser;

    @FXML
    private Button btnAbonelik;

    @FXML
    private Button btnCikis;

    @FXML
    private HBox hboxPop;

    @FXML
    private HBox hboxJazz;

    @FXML
    private HBox hboxKlasik;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private TextField searchBar;

    @FXML
    private HBox top10Pop;

    @FXML
    private HBox top10Jazz;

    @FXML
    private HBox top10Klasik;

    @FXML
    private HBox top10Genel;

    @FXML
    private HBox top10TR;

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
                if(!resultSet.getBoolean("adminlik")){
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

    List<Sarki> pop;
    List<Sarki> jazz;
    List<Sarki> klasik;
    List<Sarki> t10Pop;
    List<Sarki> t10Jazz;
    List<Sarki> t10Klasik;
    List<Sarki> t10Tumu;
    List<Sarki> t10TR;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lblUser.setText(LoginController.user.getKullaniciAdi());
        btnAbonelik.setText(LoginController.user.getAbonelik());
        if (LoginController.user.getAbonelik().equals("PREMIUM")) {
            ekran.getChildren().remove(imgSponsor);
            scrollPane.setPrefHeight(scrollPane.getPrefHeight() + 183);
        }

        pop = new ArrayList<>(getList("Pop"));
        jazz = new ArrayList<>(getList("Jazz"));
        klasik = new ArrayList<>(getList("Klasik"));
        t10Pop = new ArrayList<>(getT10("Pop"));
        t10Jazz = new ArrayList<>(getT10("Jazz"));
        t10Klasik = new ArrayList<>(getT10("Klasik"));
        t10Tumu = new ArrayList<>(getT10All());
        t10TR = new ArrayList<>(getT10TR());

        try {
            for (Sarki sarki : pop) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("../FXMLs/song.fxml"));

                VBox vBox = fxmlLoader.load();
                SarkiController sarkiController = fxmlLoader.getController();
                sarkiController.setData(sarki);

                hboxPop.getChildren().add(vBox);
            }

            for (Sarki sarki : jazz) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("../FXMLs/song.fxml"));

                VBox vBox = fxmlLoader.load();
                SarkiController sarkiController = fxmlLoader.getController();
                sarkiController.setData(sarki);

                hboxJazz.getChildren().add(vBox);
            }

            for (Sarki sarki : klasik) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("../FXMLs/song.fxml"));

                VBox vBox = fxmlLoader.load();
                SarkiController sarkiController = fxmlLoader.getController();
                sarkiController.setData(sarki);

                hboxKlasik.getChildren().add(vBox);
            }

            for (Sarki sarki : t10Pop) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("../FXMLs/song.fxml"));

                VBox vBox = fxmlLoader.load();
                SarkiController sarkiController = fxmlLoader.getController();
                sarkiController.setData(sarki);

                top10Pop.getChildren().add(vBox);
            }

            for (Sarki sarki : t10Jazz) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("../FXMLs/song.fxml"));

                VBox vBox = fxmlLoader.load();
                SarkiController sarkiController = fxmlLoader.getController();
                sarkiController.setData(sarki);

                top10Jazz.getChildren().add(vBox);
            }

            for (Sarki sarki : t10Klasik) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("../FXMLs/song.fxml"));

                VBox vBox = fxmlLoader.load();
                SarkiController sarkiController = fxmlLoader.getController();
                sarkiController.setData(sarki);

                top10Klasik.getChildren().add(vBox);
            }

            for (Sarki sarki : t10Tumu) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("../FXMLs/song.fxml"));

                VBox vBox = fxmlLoader.load();
                SarkiController sarkiController = fxmlLoader.getController();
                sarkiController.setData(sarki);

                top10Genel.getChildren().add(vBox);
            }

            for (Sarki sarki : t10TR) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("../FXMLs/song.fxml"));

                VBox vBox = fxmlLoader.load();
                SarkiController sarkiController = fxmlLoader.getController();
                sarkiController.setData(sarki);

                top10TR.getChildren().add(vBox);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<Sarki> getList(String tur) {
        List<Sarki> ls = new ArrayList<>();
        DbHelper helper = new DbHelper();
        Statement statement;
        ResultSet resultSet;

        try (Connection connection = helper.getConnection()) {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("select * from sarki where tur = '" + tur + "'");
            while (resultSet.next()) {
                ls.add(new Sarki(resultSet.getInt("ID"), resultSet.getString("sarkiAdi"), resultSet.getInt("sanatciID"), resultSet.getString("tarih"), resultSet.getInt("albumID"), resultSet.getString("tur"), resultSet.getString("sure"), resultSet.getInt("dinlenme")));
            }
        } catch (SQLException exception) {
            helper.showErrorMessage(exception);
        }

        return ls;
    }

    private List<Sarki> getT10(String tur) {
        List<Sarki> ls = new ArrayList<>();
        DbHelper helper = new DbHelper();
        Statement statement;
        ResultSet resultSet;

        try (Connection connection = helper.getConnection()) {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("select * from sarki where tur = '" + tur + "' order by dinlenme desc limit 10");
            while (resultSet.next()) {
                ls.add(new Sarki(resultSet.getInt("ID"), resultSet.getString("sarkiAdi"), resultSet.getInt("sanatciID"), resultSet.getString("tarih"), resultSet.getInt("albumID"), resultSet.getString("tur"), resultSet.getString("sure"), resultSet.getInt("dinlenme")));
            }
        } catch (SQLException exception) {
            helper.showErrorMessage(exception);
        }

        return ls;
    }

    private List<Sarki> getT10All() {
        List<Sarki> ls = new ArrayList<>();
        DbHelper helper = new DbHelper();
        Statement statement;
        ResultSet resultSet;

        try (Connection connection = helper.getConnection()) {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("select * from sarki order by dinlenme desc limit 10");
            while (resultSet.next()) {
                ls.add(new Sarki(resultSet.getInt("ID"), resultSet.getString("sarkiAdi"), resultSet.getInt("sanatciID"), resultSet.getString("tarih"), resultSet.getInt("albumID"), resultSet.getString("tur"), resultSet.getString("sure"), resultSet.getInt("dinlenme")));
            }
        } catch (SQLException exception) {
            helper.showErrorMessage(exception);
        }

        return ls;
    }

    private List<Sarki> getT10TR() {
        List<Sarki> ls = new ArrayList<>();
        DbHelper helper = new DbHelper();
        Statement statement;
        ResultSet resultSet;

        try (Connection connection = helper.getConnection()) {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("select * from sarki where sanatciID in (select ID from sanatci where ulkesi = 'TR') order by dinlenme desc limit 10");
            while (resultSet.next()) {
                ls.add(new Sarki(resultSet.getInt("ID"), resultSet.getString("sarkiAdi"), resultSet.getInt("sanatciID"), resultSet.getString("tarih"), resultSet.getInt("albumID"), resultSet.getString("tur"), resultSet.getString("sure"), resultSet.getInt("dinlenme")));
            }
        } catch (SQLException exception) {
            helper.showErrorMessage(exception);
        }

        return ls;
    }
}