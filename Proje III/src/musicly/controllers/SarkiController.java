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

public class SarkiController {
    private Sarki sarki;
    private boolean mevcutMu = false;
    private int listID = -1;

    @FXML
    private ImageView img;

    @FXML
    private Label songName;

    @FXML
    private Label artist;

    @FXML
    private Button btnListe;

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
            statement.setInt(1, sarki.getID());
            resultSet = statement.executeQuery();
            if(resultSet.next()) sarki.setDinlenme(resultSet.getInt("dinlenme"));

            sql = "update sarki set dinlenme = ? where ID = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, sarki.getDinlenme()+1);
            statement.setInt(2, sarki.getID());
            statement.executeUpdate();
        } catch (SQLException exception) {
            helper.showErrorMessage(exception);
        }

        Label lbl = (Label) HomeController.anasayfaPlayer.lookup("#lblSarki");
        lbl.setText(sarki.getSarkiAdi());
        lbl = (Label) HomeController.anasayfaPlayer.lookup("#lblSanatci");
        lbl.setText(artist.getText());
        Image file = new Image(getClass().getResourceAsStream("../../img/"+sarki.getTur().toLowerCase()+".png"));
        ImageView img = (ImageView) HomeController.anasayfaPlayer.lookup("#imgKapak");
        img.setImage(file);
        lbl = (Label) HomeController.anasayfaPlayer.lookup("#lblSure");
        lbl.setText(sarki.getSure());
    }

    @FXML
    public void listeyeEkle(ActionEvent event) {
        if (event.getSource() == btnListe) {
            listeSorgula();
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
                } catch (SQLException exception) {
                    helper.showErrorMessage(exception);
                }
            }
            btnListe.setText("Eklendi");
            btnListe.setOnAction(this::listedenCikar);
            mevcutMu = true;
        }
    }

    @FXML
    public void listedenCikar(ActionEvent event) {
        if (event.getSource() == btnListe) {
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
                    btnListe.setText("Listeye Ekle");
                    btnListe.setOnAction(this::listeyeEkle);
                    mevcutMu = false;
                } catch (SQLException exception) {
                    helper.showErrorMessage(exception);
                }
        }
    }

    public void setData(Sarki sarki){
        this.sarki = sarki;
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
        img.setImage(image);
        songName.setText(sarki.getSarkiAdi());

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
                artist.setText(resultSet.getString("sanatciAdi"));
            }
        } catch (SQLException exception) {
            helper.showErrorMessage(exception);
        }

        if(mevcutMu){
            btnListe.setText("Eklendi");
            btnListe.setOnAction(this::listedenCikar);
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
            statement.setString(1, sarki.getTur());
            statement.setInt(2, LoginController.user.getId());
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                listID = resultSet.getInt("ID");
            }
        } catch (SQLException exception) {
            helper.showErrorMessage(exception);
        }

        sql = "select * from playlist_sarkilar where listeID = ? and sarkiID = ?";

        try {
            connection = helper.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, listID);
            statement.setInt(2, sarki.getID());
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                mevcutMu = true;
            }
        } catch (SQLException exception) {
            helper.showErrorMessage(exception);
        }
    }
}