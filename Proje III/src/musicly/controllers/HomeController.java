package musicly.controllers;

import database.DbHelper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
    public static BorderPane anaEkran;
    public static HBox anasayfaPlayer;
    public static Accordion anasayfaTakip;

    @FXML
    private BorderPane ekran;

    @FXML
    private VBox btnGiris;

    @FXML
    private Label listPop;

    @FXML
    private Label listJazz;

    @FXML
    public Label lblSanatci;

    @FXML
    private HBox player;

    @FXML
    private Accordion takipList;

    @FXML
    public void anaSayfa(MouseEvent event) {
        if (event.getSource() == btnGiris) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("../FXMLs/giris.fxml"));
                VBox vBox = fxmlLoader.load();
                ekran.setCenter(vBox);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void listeCagir(MouseEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("../FXMLs/liste.fxml"));
            VBox vBox = fxmlLoader.load();
            ListeController listeController = fxmlLoader.getController();
            if (event.getSource() == listPop) {
                listeController.setData("Pop", LoginController.user);
            } else if (event.getSource() == listJazz) {
                listeController.setData("Jazz", LoginController.user);
            } else {
                listeController.setData("Klasik", LoginController.user);
            }
            ekran.setCenter(vBox);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        anasayfaPlayer = player;
        anasayfaTakip = takipList;
        anaEkran = ekran;
        takipListe();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("../FXMLs/giris.fxml"));
            VBox vBox = fxmlLoader.load();
            ekran.setCenter(vBox);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void takipListe(){
        List<User> takipEdilen = new ArrayList<>();
        Connection connection;
        DbHelper helper = new DbHelper();
        PreparedStatement statement;
        ResultSet resultSet;
        String sql = "select * from users where ID in (select takipID from takip where takipciID = ?)";

        try {
            connection = helper.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, LoginController.user.getId());
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                takipEdilen.add(new User(resultSet.getInt("ID"), resultSet.getString("kullaniciAdi"), resultSet.getString("email"), resultSet.getString("sifre"), resultSet.getString("abonelik"), resultSet.getString("ulke"), resultSet.getBoolean("adminlik"), resultSet.getInt("takipci")));
            }
        } catch (SQLException exception) {
            helper.showErrorMessage(exception);
        }

        try {
            for (User user : takipEdilen) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("../FXMLs/takipListe.fxml"));
                TitledPane tp = fxmlLoader.load();
                TakipListeController takipListeController = fxmlLoader.getController();
                takipListeController.setData(user);
                takipList.getPanes().add(tp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}