package musicly.controllers;

import database.DbHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import model.Sarki;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SarkiListeController {
    Sarki sarkiListe;
    private Boolean mevcutMu = false;
    private int listID = -1;

    @FXML
    private ImageView imgKapak;

    @FXML
    private Label lblID;

    @FXML
    private Label lblSarki;

    @FXML
    private Label lblSanatci;

    @FXML
    private Label lblAlbum;

    @FXML
    private Label lblSure;

    @FXML
    private Button btnEkle;

    @FXML
    public void listeyeEkle(ActionEvent event) {
        if (event.getSource() == btnEkle) {
            if(!mevcutMu){
                Connection connection;
                DbHelper helper = new DbHelper();
                PreparedStatement statement;
                String sql = "insert into playlist_sarkilar (listeID, sarkiID) values(?, ?)";

                try {
                    connection = helper.getConnection();
                    statement = connection.prepareStatement(sql);
                    statement.setInt(1, listID);
                    statement.setInt(2, sarkiListe.getID());
                    statement.executeUpdate();
                    btnEkle.setText("Eklendi");
                    btnEkle.setOnAction(this::listedenCikar);
                    mevcutMu = true;
                } catch (SQLException exception) {
                    helper.showErrorMessage(exception);
                }
            }
        }
    }

    @FXML
    public void listedenCikar(ActionEvent event) {
        if (event.getSource() == btnEkle) {
            Connection connection;
            DbHelper helper = new DbHelper();
            PreparedStatement statement;
            String sql = "delete from playlist_sarkilar where listeID = ? and sarkiID = ?";

            try {
                connection = helper.getConnection();
                statement = connection.prepareStatement(sql);
                statement.setInt(1, listID);
                statement.setInt(2, sarkiListe.getID());
                statement.executeUpdate();
                btnEkle.setText("Listeye Ekle");
                btnEkle.setOnAction(this::listeyeEkle);
                mevcutMu = false;
            } catch (SQLException exception) {
                helper.showErrorMessage(exception);
            }
        }
    }

    public void listeSorgula() {
        Connection connection;
        DbHelper helper = new DbHelper();
        PreparedStatement statement;
        ResultSet resultSet;
        String sql = "select ID from playlists where listeAdi = ? and userID = ?";

        try {
            connection = helper.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, sarkiListe.getTur());
            statement.setInt(2, LoginController.user.getId());
            resultSet = statement.executeQuery();
            while(resultSet.next()){
                listID = resultSet.getInt("ID");
            }

            sql = "select * from playlist_sarkilar where sarkiID = ? and listeID = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, sarkiListe.getID());
            statement.setInt(2, listID);
            resultSet = statement.executeQuery();
            while(resultSet.next()){
                mevcutMu = true;
            }
        } catch (SQLException exception) {
            helper.showErrorMessage(exception);
        }
    }

    @FXML
    public void cal(MouseEvent event){
        Connection connection;
        DbHelper helper = new DbHelper();
        PreparedStatement statement;
        ResultSet resultSet;
        String sql = "select dinlenme from sarki where ID = ?";

        try {
            connection = helper.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, sarkiListe.getID());
            resultSet = statement.executeQuery();
            if(resultSet.next()) sarkiListe.setDinlenme(resultSet.getInt("dinlenme"));

            sql = "update sarki set dinlenme = ? where ID = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, sarkiListe.getDinlenme()+1);
            statement.setInt(2, sarkiListe.getID());
            statement.executeUpdate();
        } catch (SQLException exception) {
            helper.showErrorMessage(exception);
        }

        Label lbl = (Label) HomeController.anasayfaPlayer.lookup("#lblSarki");
        lbl.setText(sarkiListe.getSarkiAdi());
        lbl = (Label) HomeController.anasayfaPlayer.lookup("#lblSanatci");
        lbl.setText(lblSanatci.getText());
        Image file = new Image(getClass().getResourceAsStream("../../img/"+sarkiListe.getTur().toLowerCase()+".png"));
        ImageView img = (ImageView) HomeController.anasayfaPlayer.lookup("#imgKapak");
        img.setImage(file);
        lbl = (Label) HomeController.anasayfaPlayer.lookup("#lblSure");
        lbl.setText(sarkiListe.getSure());
    }

    public void setData(Sarki sarki, int id){
        sarkiListe = sarki;
        listeSorgula();
        String file;

        if(sarki.getTur().equals("Pop")){
            file = "../../img/pop.png";
        }else if(sarki.getTur().equals("Jazz")){
            file = "../../img/jazz.png";
        }else{
            file = "../../img/klasik.png";
        }

        Image image = new Image(getClass().getResourceAsStream(file));
        imgKapak.setImage(image);
        lblID.setText(Integer.toString(id));
        lblSarki.setText(sarki.getSarkiAdi());

        Connection connection;
        DbHelper helper = new DbHelper();
        PreparedStatement statement;
        ResultSet resultSet;
        String sql = "select sanatciAdi from sanatci where ID = ?";

        try {
            connection = helper.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, sarki.getSanatciID());
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                lblSanatci.setText(resultSet.getString("sanatciAdi"));
            }

            sql = "select albumAdi from album where ID = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, sarki.getAlbumID());
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                lblAlbum.setText(resultSet.getString("albumAdi"));
            }
        } catch (SQLException exception) {
            helper.showErrorMessage(exception);
        }
        lblSure.setText(sarki.getSure());
        if(mevcutMu){
            btnEkle.setText("Eklendi");
            btnEkle.setOnAction(this::listedenCikar);
        }
    }
}