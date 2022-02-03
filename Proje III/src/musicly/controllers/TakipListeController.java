package musicly.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import model.User;
import java.io.IOException;

public class TakipListeController {
    private User takip;

    @FXML
    private TitledPane titledPane;

    @FXML
    private Label listPop;

    @FXML
    private Label listJazz;

    @FXML
    public void listeCagir(MouseEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("../FXMLs/liste.fxml"));
            VBox vBox = fxmlLoader.load();
            ListeController listeController = fxmlLoader.getController();
            if (event.getSource() == listPop) {
                listeController.setData("Pop", takip);
            } else if (event.getSource() == listJazz) {
                listeController.setData("Jazz", takip);
            } else {
                listeController.setData("Klasik", takip);
            }
            HomeController.anaEkran.setCenter(vBox);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setData(User user){
        takip = user;
        titledPane.setText(user.getKullaniciAdi());
        titledPane.setId(Integer.toString(user.getId()));
    }
}