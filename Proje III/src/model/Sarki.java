package model;

import database.DbHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import musicly.controllers.AdminController;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Sarki {
    private int ID;
    private String sarkiAdi;
    private int sanatciID;
    private String tarih;
    private int albumID;
    private String tur;
    private String sure;
    private int dinlenme;
    private Button silButon;
    private Button guncelleButon;

    public Sarki(int ID, String sarkiAdi, int sanatciID, String tarih, int albumID, String tur, String sure, int dinlenme) {
        setID(ID);
        setSarkiAdi(sarkiAdi);
        setSanatciID(sanatciID);
        setTarih(tarih);
        setAlbumID(albumID);
        setTur(tur);
        setSure(sure);
        setDinlenme(dinlenme);

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
            String sql = "update sarki set sarkiAdi = ?, tarih = ?, sanatciID = ?, albumID = ?, tur = ?, sure = ? where id = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, this.sarkiAdi);
            statement.setString(2, this.tarih);
            statement.setInt(3, this.sanatciID);
            statement.setInt(4, this.albumID);
            statement.setString(5, this.tur);
            statement.setString(6, this.sure);
            statement.setInt(7, this.ID);
            statement.executeUpdate();
            AdminController.refreshTableSarki();
        } catch (SQLException exception) {
            helper.showErrorMessage(exception);
        }
    }

    @FXML
    public void kayitSil(ActionEvent event) {
        Connection connection;
        DbHelper helper = new DbHelper();
        PreparedStatement statement;

        try {
            connection = helper.getConnection();
            String sql = "delete from sarki where id = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, this.ID);
            statement.executeUpdate();
            sql = "delete from playlist_sarkilar where sarkiID = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, this.ID);
            statement.executeUpdate();
            AdminController.refreshTableSarki();
        } catch (SQLException exception) {
            helper.showErrorMessage(exception);
        }
    }

    public String getSarkiAdi() {
        return sarkiAdi;
    }

    public void setSarkiAdi(String sarkiAdi) {
        this.sarkiAdi = sarkiAdi;
    }

    public int getSanatciID() {
        return sanatciID;
    }

    public void setSanatciID(int sanatciID) {
        this.sanatciID = sanatciID;
    }

    public String getTarih() {
        return tarih;
    }

    public void setTarih(String tarih) {
        this.tarih = tarih;
    }

    public int getAlbumID() {
        return albumID;
    }

    public void setAlbumID(int albumID) {
        this.albumID = albumID;
    }

    public String getTur() {
        return tur;
    }

    public void setTur(String tur) {
        this.tur = tur;
    }

    public String getSure() {
        return sure;
    }

    public void setSure(String sure) {
        this.sure = sure;
    }

    public int getDinlenme() {
        return dinlenme;
    }

    public void setDinlenme(int dinlenme) {
        this.dinlenme = dinlenme;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
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