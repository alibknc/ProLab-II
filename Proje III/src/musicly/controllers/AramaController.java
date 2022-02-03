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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.User;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AramaController {
    @FXML
    private VBox ekran;

    @FXML
    private TextField searchBar;

    @FXML
    private Button btnAbonelik;

    @FXML
    private Label lblUser;

    @FXML
    private Button btnCikis;

    @FXML
    private VBox sonucListe;

    @FXML
    void arama(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            if(!searchBar.getText().equals("")) sonucGetir(searchBar.getText());
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
    void close(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    void kucult(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    public void setData(List<User> liste) throws IOException {
        lblUser.setText(LoginController.user.getKullaniciAdi());
        btnAbonelik.setText(LoginController.user.getAbonelik());

        int i = 1;
        for (User kullanici : liste) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("../FXMLs/aramaListe.fxml"));

            HBox hBox = fxmlLoader.load();
            AramaListeController aramaListeController = fxmlLoader.getController();
            aramaListeController.setData(kullanici, i, ekran);

            sonucListe.getChildren().add(hBox);
            i++;
        }
    }
}