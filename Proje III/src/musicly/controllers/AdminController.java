package musicly.controllers;

import database.DbHelper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import model.Album;
import model.Sanatci;
import model.Sarki;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class AdminController implements Initializable {
    public static TableView<Sarki> sarkiTablosu;
    public static TableView<Sanatci> sanatciTablosu;
    public static TableView<Album> albumTablosu;

    @FXML
    private TableView<Sarki> tblSarki;

    @FXML
    private TableView<Sanatci> tblSanatci;

    @FXML
    private TableView<Album> tblAlbum;

    @FXML
    private TableColumn<Sarki, Integer> clmID;

    @FXML
    private TableColumn<Sanatci, Integer> clmID2;

    @FXML
    private TableColumn<Album, Integer> clmID3;

    @FXML
    private TableColumn<Sarki, String> clmSarki;

    @FXML
    private TableColumn<Sarki, Integer> clmSanatci;

    @FXML
    private TableColumn<Sanatci, String> clmSanatci2;

    @FXML
    private TableColumn<Album, Integer> clmSanatci3;

    @FXML
    private TableColumn<Sarki, Integer> clmAlbum;

    @FXML
    private TableColumn<Album, String> clmAlbum2;

    @FXML
    private TableColumn<Sarki, String> clmTur;

    @FXML
    private TableColumn<Album, String> clmTur2;

    @FXML
    private TableColumn<Sarki, String> clmTarih;

    @FXML
    private TableColumn<Album, String> clmTarih2;

    @FXML
    private TableColumn<Sanatci, String> clmUlke;

    @FXML
    private TableColumn<Sarki, String> clmSure;

    @FXML
    private TableColumn<Sarki, String> clmDinlenme;

    @FXML
    private TableColumn<Sarki, Button> clmSil;

    @FXML
    private TableColumn<Sarki, Button> clmGuncelle;

    @FXML
    private TableColumn<Sanatci, Button> clmSil2;

    @FXML
    private TableColumn<Sanatci, Button> clmGuncelle2;

    @FXML
    private TableColumn<Album, Button> clmSil3;

    @FXML
    private TableColumn<Album, Button> clmGuncelle3;

    @FXML
    private Button btnCikis;

    @FXML
    private Label lblUser;

    @FXML
    private TextField txtSarki;

    @FXML
    private TextField txtSanatci;

    @FXML
    private TextField txtAlbum;

    @FXML
    private TextField txtTarih;

    @FXML
    private TextField txtTur;

    @FXML
    private TextField txtSure;

    @FXML
    private Button btnEkleSarki;

    @FXML
    private TextField txtSanatciAdi;

    @FXML
    private TextField txtUlke;

    @FXML
    private Button btnEkleSanatci;

    @FXML
    private TextField txtAlbumAdi;

    @FXML
    private TextField txtSanatciID;

    @FXML
    private TextField txtTarihAlbum;

    @FXML
    private TextField txtTurAlbum;

    @FXML
    private Button btnEkleAlbum;

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
    void albumEkle(ActionEvent event) {
        if(event.getSource() == btnEkleAlbum){
            String albumAdi = txtAlbumAdi.getText();
            String sanatciID = txtSanatciID.getText();
            String tarih = txtTarihAlbum.getText();
            String tur = txtTurAlbum.getText();
            String sql = "select * from album where albumAdi = ? and sanatciID = ?";
            Connection connection;
            DbHelper helper = new DbHelper();
            PreparedStatement statement;
            ResultSet resultSet;

            try {
                connection = helper.getConnection();
                statement = connection.prepareStatement(sql);
                statement.setString(1, albumAdi);
                statement.setString(2, sanatciID);
                resultSet = statement.executeQuery();
                if (!resultSet.next()) {
                    if(!albumAdi.equals("") && !sanatciID.equals("") && !tarih.equals("") && !tur.equals("")){
                        connection.close();
                        connection = helper.getConnection();
                        sql = "insert into album (albumAdi, sanatciID, tarih, tur) values (?,?,?,?)";
                        statement = connection.prepareStatement(sql);
                        statement.setString(1, albumAdi);
                        statement.setString(2, sanatciID);
                        statement.setString(3, tarih);
                        statement.setString(4, tur);
                        statement.executeUpdate();
                        refreshTableAlbum();
                        txtAlbumAdi.setText(null);
                        txtSanatciID.setText(null);
                        txtTarihAlbum.setText(null);
                        txtTurAlbum.setText(null);
                    }
                }
            } catch (SQLException exception) {
                helper.showErrorMessage(exception);
            }
        }
    }

    @FXML
    void sanatciEkle(ActionEvent event) {
        if(event.getSource() == btnEkleSanatci){
            String sanatciAdi = txtSanatciAdi.getText();
            String ulke = txtUlke.getText();
            String sql = "select * from sanatci where sanatciAdi = ? and ulkesi = ?";
            Connection connection;
            DbHelper helper = new DbHelper();
            PreparedStatement statement;
            ResultSet resultSet;

            try {
                connection = helper.getConnection();
                statement = connection.prepareStatement(sql);
                statement.setString(1, sanatciAdi);
                statement.setString(2, ulke);
                resultSet = statement.executeQuery();
                if (!resultSet.next()) {
                    if(!sanatciAdi.equals("") && !ulke.equals("")){
                        connection.close();
                        connection = helper.getConnection();
                        sql = "insert into sanatci (sanatciAdi, ulkesi) values (?,?)";
                        statement = connection.prepareStatement(sql);
                        statement.setString(1, sanatciAdi);
                        statement.setString(2, ulke);
                        statement.executeUpdate();
                        refreshTableSanatci();
                        txtSanatciAdi.setText(null);
                        txtUlke.setText(null);
                    }
                }
            } catch (SQLException exception) {
                helper.showErrorMessage(exception);
            }
        }
    }

    @FXML
    void sarkiEkle(ActionEvent event) {
        if(event.getSource() == btnEkleSarki){
            String sarkiAdi = txtSarki.getText();
            String sanatci = txtSanatci.getText();
            String album = txtAlbum.getText();
            String tarih = txtTarih.getText();
            String tur = txtTur.getText();
            String sure = txtSure.getText();
            String sql = "select * from sarki where sarkiAdi = ? and albumID = ?";
            Connection connection;
            DbHelper helper = new DbHelper();
            PreparedStatement statement;
            ResultSet resultSet;

            try {
                connection = helper.getConnection();
                statement = connection.prepareStatement(sql);
                statement.setString(1, sarkiAdi);
                statement.setString(2, album);
                resultSet = statement.executeQuery();
                if (!resultSet.next()) {
                    if(!sarkiAdi.equals("") && !sanatci.equals("") && !album.equals("") && !tarih.equals("") && !tur.equals("") && !sure.equals("")){
                        connection.close();
                        connection = helper.getConnection();
                        sql = "insert into sarki (sarkiAdi, tarih, sanatciID, albumID, tur, sure, dinlenme) values (?,?,?,?,?,?,0)";
                        statement = connection.prepareStatement(sql);
                        statement.setString(1, sarkiAdi);
                        statement.setString(2, tarih);
                        statement.setString(3, sanatci);
                        statement.setString(4, album);
                        statement.setString(5, tur);
                        statement.setString(6, sure);
                        statement.executeUpdate();
                        refreshTableSarki();
                        txtSarki.setText(null);
                        txtSanatci.setText(null);
                        txtAlbum.setText(null);
                        txtTarih.setText(null);
                        txtTur.setText(null);
                        txtSure.setText(null);
                    }
                }
            } catch (SQLException exception) {
                helper.showErrorMessage(exception);
            }
        }
    }

    @FXML
    public void handleButtonAction(ActionEvent event) throws IOException {
        if (event.getSource() == btnCikis) {
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("../FXMLs/login.fxml")));
            stage.setScene(scene);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lblUser.setText(LoginController.user.getKullaniciAdi());
        insertTableSarki();
        insertTableSanatci();
        insertTableAlbum();
    }

    public void insertTableSarki() {
        ObservableList<Sarki> sarkiList = FXCollections.observableArrayList();
        DbHelper helper = new DbHelper();
        Statement statement;
        ResultSet resultSet;

        try (Connection connection = helper.getConnection()) {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("select * from sarki");
            while (resultSet.next()) {
                sarkiList.add(new Sarki(resultSet.getInt("ID"), resultSet.getString("sarkiAdi"), resultSet.getInt("sanatciID"), resultSet.getString("tarih"), resultSet.getInt("albumID"), resultSet.getString("tur"), resultSet.getString("sure"), resultSet.getInt("dinlenme")));
            }
        } catch (SQLException exception) {
            helper.showErrorMessage(exception);
        }

        clmID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        clmAlbum.setCellValueFactory(new PropertyValueFactory<>("albumID"));
        clmDinlenme.setCellValueFactory(new PropertyValueFactory<>("dinlenme"));
        clmSanatci.setCellValueFactory(new PropertyValueFactory<>("sanatciID"));
        clmSarki.setCellValueFactory(new PropertyValueFactory<>("sarkiAdi"));
        clmTur.setCellValueFactory(new PropertyValueFactory<>("tur"));
        clmSure.setCellValueFactory(new PropertyValueFactory<>("sure"));
        clmTarih.setCellValueFactory(new PropertyValueFactory<>("tarih"));
        clmSil.setCellValueFactory(new PropertyValueFactory<>("silButon"));
        clmGuncelle.setCellValueFactory(new PropertyValueFactory<>("guncelleButon"));

        tblSarki.setItems(sarkiList);
        sarkiTablosu = tblSarki;
        tabloYeniDegerSarki();
    }

    public void insertTableSanatci() {
        ObservableList<Sanatci> sanatciList = FXCollections.observableArrayList();
        DbHelper helper = new DbHelper();
        Statement statement;
        ResultSet resultSet;

        try (Connection connection = helper.getConnection()) {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("select * from sanatci");
            while (resultSet.next()) {
                sanatciList.add(new Sanatci(resultSet.getInt("ID"), resultSet.getString("sanatciAdi"), resultSet.getString("ulkesi")));
            }
        } catch (SQLException exception) {
            helper.showErrorMessage(exception);
        }

        clmID2.setCellValueFactory(new PropertyValueFactory<>("ID"));
        clmSanatci2.setCellValueFactory(new PropertyValueFactory<>("sanatciAdi"));
        clmUlke.setCellValueFactory(new PropertyValueFactory<>("ulke"));
        clmSil2.setCellValueFactory(new PropertyValueFactory<>("silButon"));
        clmGuncelle2.setCellValueFactory(new PropertyValueFactory<>("guncelleButon"));

        tblSanatci.setItems(sanatciList);
        sanatciTablosu = tblSanatci;
        tabloYeniDegerSanatci();
    }

    public void insertTableAlbum() {
        ObservableList<Album> albumList = FXCollections.observableArrayList();
        DbHelper helper = new DbHelper();
        Statement statement;
        ResultSet resultSet;

        try (Connection connection = helper.getConnection()) {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("select * from album");
            while (resultSet.next()) {
                albumList.add(new Album(resultSet.getInt("ID"), resultSet.getString("albumAdi"), resultSet.getInt("sanatciID"), resultSet.getString("tarih"), resultSet.getString("tur")));
            }
        } catch (SQLException exception) {
            helper.showErrorMessage(exception);
        }

        clmID3.setCellValueFactory(new PropertyValueFactory<>("ID"));
        clmAlbum2.setCellValueFactory(new PropertyValueFactory<>("albumAdi"));
        clmSanatci3.setCellValueFactory(new PropertyValueFactory<>("sanatciID"));
        clmTarih2.setCellValueFactory(new PropertyValueFactory<>("tarih"));
        clmTur2.setCellValueFactory(new PropertyValueFactory<>("tur"));
        clmSil3.setCellValueFactory(new PropertyValueFactory<>("silButon"));
        clmGuncelle3.setCellValueFactory(new PropertyValueFactory<>("guncelleButon"));

        tblAlbum.setItems(albumList);
        albumTablosu = tblAlbum;
        tabloYeniDegerAlbum();
    }

    public static void refreshTableSarki() {
        ObservableList<Sarki> sarkiList = FXCollections.observableArrayList();
        DbHelper helper = new DbHelper();
        Statement statement;
        ResultSet resultSet;

        try (Connection connection = helper.getConnection()) {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("select * from sarki");
            while (resultSet.next()) {
                sarkiList.add(new Sarki(resultSet.getInt("ID"), resultSet.getString("sarkiAdi"), resultSet.getInt("sanatciID"), resultSet.getString("tarih"), resultSet.getInt("albumID"), resultSet.getString("tur"), resultSet.getString("sure"), resultSet.getInt("dinlenme")));
            }
        } catch (SQLException exception) {
            helper.showErrorMessage(exception);
        }

        sarkiTablosu.setItems(sarkiList);
    }

    public static void refreshTableSanatci() {
        ObservableList<Sanatci> sanatciList = FXCollections.observableArrayList();
        DbHelper helper = new DbHelper();
        Statement statement;
        ResultSet resultSet;

        try (Connection connection = helper.getConnection()) {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("select * from sanatci");
            while (resultSet.next()) {
                sanatciList.add(new Sanatci(resultSet.getInt("ID"), resultSet.getString("sanatciAdi"), resultSet.getString("ulkesi")));
            }
        } catch (SQLException exception) {
            helper.showErrorMessage(exception);
        }

        sanatciTablosu.setItems(sanatciList);
    }

    public static void refreshTableAlbum() {
        ObservableList<Album> albumList = FXCollections.observableArrayList();
        DbHelper helper = new DbHelper();
        Statement statement;
        ResultSet resultSet;

        try (Connection connection = helper.getConnection()) {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("select * from album");
            while (resultSet.next()) {
                albumList.add(new Album(resultSet.getInt("ID"), resultSet.getString("albumAdi"), resultSet.getInt("sanatciID"), resultSet.getString("tarih"), resultSet.getString("tur")));
            }
        } catch (SQLException exception) {
            helper.showErrorMessage(exception);
        }

        albumTablosu.setItems(albumList);
    }

    public void tabloYeniDegerSarki(){
        clmTarih.setCellFactory(TextFieldTableCell.forTableColumn());
        clmSure.setCellFactory(TextFieldTableCell.forTableColumn());
        clmSarki.setCellFactory(TextFieldTableCell.forTableColumn());
        clmSanatci.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        clmAlbum.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        clmTur.setCellFactory(TextFieldTableCell.forTableColumn());

        clmTarih.setOnEditCommit(new EventHandler<>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Sarki, String> event) {
                Sarki sarki = event.getRowValue();
                sarki.setTarih(event.getNewValue());
            }
        });

        clmSure.setOnEditCommit(new EventHandler<>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Sarki, String> event) {
                Sarki sarki = event.getRowValue();
                sarki.setSure(event.getNewValue());
            }
        });

        clmTur.setOnEditCommit(new EventHandler<>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Sarki, String> event) {
                Sarki sarki = event.getRowValue();
                sarki.setTur(event.getNewValue());
            }
        });

        clmAlbum.setOnEditCommit(new EventHandler<>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Sarki, Integer> event) {
                Sarki sarki = event.getRowValue();
                sarki.setAlbumID(event.getNewValue());
            }
        });

        clmSanatci.setOnEditCommit(new EventHandler<>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Sarki, Integer> event) {
                Sarki sarki = event.getRowValue();
                sarki.setSanatciID(event.getNewValue());
            }
        });

        clmSarki.setOnEditCommit(new EventHandler<>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Sarki, String> event) {
                Sarki sarki = event.getRowValue();
                sarki.setSarkiAdi(event.getNewValue());
            }
        });
    }

    public void tabloYeniDegerSanatci(){
        clmUlke.setCellFactory(TextFieldTableCell.forTableColumn());
        clmSanatci2.setCellFactory(TextFieldTableCell.forTableColumn());

        clmSanatci2.setOnEditCommit(new EventHandler<>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Sanatci, String> event) {
                Sanatci sanatci = event.getRowValue();
                sanatci.setSanatciAdi(event.getNewValue());
            }
        });

        clmUlke.setOnEditCommit(new EventHandler<>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Sanatci, String> event) {
                Sanatci sanatci = event.getRowValue();
                sanatci.setUlke(event.getNewValue());
            }
        });
    }

    public void tabloYeniDegerAlbum(){
        clmTarih2.setCellFactory(TextFieldTableCell.forTableColumn());
        clmSanatci3.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        clmAlbum2.setCellFactory(TextFieldTableCell.forTableColumn());
        clmTur2.setCellFactory(TextFieldTableCell.forTableColumn());

        clmTarih2.setOnEditCommit(new EventHandler<>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Album, String> event) {
                Album album = event.getRowValue();
                album.setTarih(event.getNewValue());
            }
        });

        clmTur2.setOnEditCommit(new EventHandler<>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Album, String> event) {
                Album album = event.getRowValue();
                album.setTur(event.getNewValue());
            }
        });

        clmAlbum2.setOnEditCommit(new EventHandler<>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Album, String> event) {
                Album album = event.getRowValue();
                album.setAlbumAdi(event.getNewValue());
            }
        });

        clmSanatci3.setOnEditCommit(new EventHandler<>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Album, Integer> event) {
                Album album = event.getRowValue();
                album.setSanatciID(event.getNewValue());
            }
        });
    }

}