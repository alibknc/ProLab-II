package musicly.controllers;

import database.DbHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.User;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AramaListeController {
    public VBox ekran;
    public User aranan;
    private Boolean mevcutMu = false;

    @FXML
    private HBox sonuc;

    @FXML
    private Label lblID;

    @FXML
    private Label lblKullanici;

    @FXML
    private Label lblAbonelik;

    @FXML
    private Button btnTakip;

    @FXML
    public void takipEt(ActionEvent event) {
        if (event.getSource() == btnTakip) {
            if (!mevcutMu) {
                Connection connection;
                DbHelper helper = new DbHelper();
                PreparedStatement statement;
                String sql = "insert into takip (takipID, takipciID) values(?, ?)";

                try {
                    connection = helper.getConnection();
                    statement = connection.prepareStatement(sql);
                    statement.setInt(1, aranan.getId());
                    statement.setInt(2, LoginController.user.getId());
                    statement.executeUpdate();

                    sql = "update users set takipci = ? where ID = ?";
                    statement = connection.prepareStatement(sql);
                    statement.setInt(1, aranan.getTakipci()+1);
                    statement.setInt(2, aranan.getId());
                    statement.executeUpdate();

                    btnTakip.setText("Takip Ediliyor");
                    btnTakip.setOnAction(this::listedenCikar);
                    mevcutMu = true;
                    takipMenuEkle();
                } catch (SQLException exception) {
                    helper.showErrorMessage(exception);
                }
            }
        }
    }

    @FXML
    public void listedenCikar(ActionEvent event) {
        if (event.getSource() == btnTakip) {
            Connection connection;
            DbHelper helper = new DbHelper();
            PreparedStatement statement;
            String sql = "delete from takip where takipID = ? and takipciID = ?";

            try {
                connection = helper.getConnection();
                statement = connection.prepareStatement(sql);
                statement.setInt(1, aranan.getId());
                statement.setInt(2, LoginController.user.getId());
                statement.executeUpdate();

                sql = "update users set takipci = ? where ID = ?";
                statement = connection.prepareStatement(sql);
                statement.setInt(1, aranan.getTakipci()-1);
                statement.setInt(2, aranan.getId());
                statement.executeUpdate();

                btnTakip.setText("Takip Et");
                btnTakip.setOnAction(this::takipEt);
                mevcutMu = false;
                TitledPane tp = (TitledPane) HomeController.anasayfaTakip.lookup("#"+aranan.getId());
                HomeController.anasayfaTakip.getPanes().remove(tp);
            } catch (SQLException exception) {
                helper.showErrorMessage(exception);
            }
        }
    }

    public void takipMenuEkle() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("../FXMLs/takipListe.fxml"));
            TitledPane tp = fxmlLoader.load();
            TakipListeController takipListeController = fxmlLoader.getController();
            takipListeController.setData(aranan);
            HomeController.anasayfaTakip.getPanes().add(tp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listeSorgula() {
        Connection connection;
        DbHelper helper = new DbHelper();
        PreparedStatement statement;
        ResultSet resultSet;
        String sql = "select ID from takip where takipID = ? and takipciID = ?";

        try {
            connection = helper.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, aranan.getId());
            statement.setInt(2, LoginController.user.getId());
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                mevcutMu = true;
            }
        } catch (SQLException exception) {
            helper.showErrorMessage(exception);
        }
    }

    public void setData(User kullanici, int id, VBox sonucEkrani) {
        aranan = kullanici;
        ekran = sonucEkrani;
        listeSorgula();
        if (kullanici.getAbonelik().equals("NORMAL") || LoginController.user.getId() == kullanici.getId() || LoginController.user.getAbonelik().equals("PREMIUM")) {
            sonuc.getChildren().removeAll(btnTakip);
        }
        lblID.setText(Integer.toString(id));
        lblAbonelik.setText(kullanici.getAbonelik() + " ÜYE");
        lblKullanici.setText(kullanici.getKullaniciAdi());
        if(mevcutMu){
            btnTakip.setText("Takip Ediliyor");
            btnTakip.setOnAction(this::listedenCikar);
        }
    }
}