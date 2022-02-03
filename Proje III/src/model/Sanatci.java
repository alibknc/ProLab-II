package model;

import database.DbHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import musicly.controllers.AdminController;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Sanatci {
    private int ID;
    private String sanatciAdi;
    private String ulke;
    private Button silButon;
    private Button guncelleButon;

    public Sanatci(int ID, String sanatciAdi, String ulke) {
        setID(ID);
        setSanatciAdi(sanatciAdi);
        setUlke(ulke);

        Button sb = new Button("Sil");
        Button gb = new Button("Güncelle");
        sb.setOnAction(this::kayitSil);
        gb.setOnAction(this::kayitGuncelle);

        setSilButon(sb);
        setGuncelleButon(gb);
    }

    @FXML
    public void kayitGuncelle(ActionEvent event) {
        Connection connection;
        DbHelper helper = new DbHelper();
        PreparedStatement statement;

        try {
            connection = helper.getConnection();
            String sql = "update sanatci set sanatciAdi = ?, ulkesi = ? where id = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, this.sanatciAdi);
            statement.setString(2, this.ulke);
            statement.setInt(3, this.ID);
            statement.executeUpdate();
            AdminController.refreshTableSanatci();
        } catch (SQLException exception) {
            helper.showErrorMessage(exception);
        }
    }

    @FXML
    public void kayitSil(ActionEvent event) {
        Connection connection;
        DbHelper helper = new DbHelper();
        PreparedStatement statement;
        ResultSet resultSet;

        try {
            connection = helper.getConnection();
            String sql = "delete from sanatci where id = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, this.ID);
            statement.executeUpdate();

            sql = "select ID from sarki where sanatciID = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, this.ID);
            resultSet = statement.executeQuery();
            if(resultSet.next()){
                sql = "delete from playlist_sarkilar where sarkiID = ?";
                statement = connection.prepareStatement(sql);
                statement.setInt(1, resultSet.getInt("ID"));
                statement.executeUpdate();
            }

            sql = "delete from sarki where sanatciID = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, this.ID);
            statement.executeUpdate();

            sql = "delete from album where sanatciID = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, this.ID);

            AdminController.refreshTableSanatci();
            AdminController.refreshTableSarki();
            AdminController.refreshTableAlbum();
        } catch (SQLException exception) {
            helper.showErrorMessage(exception);
        }
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getSanatciAdi() {
        return sanatciAdi;
    }

    public void setSanatciAdi(String sanatciAdi) {
        this.sanatciAdi = sanatciAdi;
    }

    public String getUlke() {
        return ulke;
    }

    public void setUlke(String ulke) {
        this.ulke = ulke;
    }

    public Button getSilButon() {
        return silButon;
    }

    public void setSilButon(Button silButon) {
        this.silButon = silButon;
    }

    public Button getGuncelleButon() {
        return guncelleButon;
    }

    public void setGuncelleButon(Button guncelleButon) {
        this.guncelleButon = guncelleButon;
    }
}