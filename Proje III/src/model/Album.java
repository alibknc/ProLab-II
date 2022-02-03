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

public class Album {
    private int ID;
    private String albumAdi;
    private String tarih;
    private String tur;
    private int sanatciID;
    private Button silButon;
    private Button guncelleButon;

    public Album(int ID, String albumAdi, int sanatciID, String tarih, String tur) {
        setSanatciID(ID);
        setAlbumAdi(albumAdi);
        setSanatciID(sanatciID);
        setTarih(tarih);
        setTur(tur);

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
            String sql = "update album set albumAdi = ?, sanatciID = ?, tarih = ?, tur = ? where id = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, this.albumAdi);
            statement.setInt(2, this.sanatciID);
            statement.setString(3, this.tarih);
            statement.setString(4, this.tur);
            statement.setInt(5, this.ID);
            statement.executeUpdate();
            AdminController.refreshTableAlbum();
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
            String sql = "delete from album where id = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, this.ID);
            statement.executeUpdate();

            sql = "select ID from sarki where albumID = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, this.ID);
            resultSet = statement.executeQuery();
            if(resultSet.next()){
                sql = "delete from playlist_sarkilar where sarkiID = ?";
                statement = connection.prepareStatement(sql);
                statement.setInt(1, resultSet.getInt("ID"));
                statement.executeUpdate();
            }

            sql = "delete from sarki where albumID = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, this.ID);
            statement.executeUpdate();

            AdminController.refreshTableAlbum();
            AdminController.refreshTableSarki();
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

    public String getAlbumAdi() {
        return albumAdi;
    }

    public void setAlbumAdi(String albumAdi) {
        this.albumAdi = albumAdi;
    }

    public String getTarih() {
        return tarih;
    }

    public void setTarih(String tarih) {
        this.tarih = tarih;
    }

    public String getTur() {
        return tur;
    }

    public void setTur(String tur) {
        this.tur = tur;
    }

    public int getSanatciID() {
        return sanatciID;
    }

    public void setSanatciID(int sanatciID) {
        this.sanatciID = sanatciID;
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