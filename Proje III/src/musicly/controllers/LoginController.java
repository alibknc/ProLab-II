package musicly.controllers;

import database.DbHelper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.User;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    public static User user;
    public String abonelik;

    @FXML
    private AnchorPane ekran;

    @FXML
    private TextField txtEmail;

    @FXML
    private PasswordField txtSifre;

    @FXML
    private Button btnLogin;

    @FXML
    private Label lblError;

    @FXML
    private TextField txtEmail2;

    @FXML
    private PasswordField txtSifre2;

    @FXML
    private Button btnKayit;

    @FXML
    private Label lblError2;

    @FXML
    private TextField txtKullaniciAdi;

    @FXML
    private RadioButton normal;

    @FXML
    private RadioButton premium;

    @FXML
    private ProgressIndicator pi;

    @FXML
    private ProgressIndicator pi2;

    @FXML
    public void girisButon(ActionEvent event) {
        if (event.getSource() == btnLogin) {
            if (login().equals("true")) {
                Task task = girisGorev();
                pi.progressProperty().bind(task.progressProperty());
                task.setOnSucceeded(e -> {
                    Scene result = (Scene) task.getValue();
                    ekran.getChildren().remove(pi);
                    Node node = (Node) event.getSource();
                    Stage stage = (Stage) node.getScene().getWindow();
                    stage.setScene(result);
                });
                Thread thread = new Thread(task);
                thread.start();
            }
        }
    }

    @FXML
    void kayitOl(ActionEvent event) {
        if (event.getSource() == btnKayit) {
            if (kayit().equals("true")) {
                Task task = girisGorev();
                pi2.progressProperty().bind(task.progressProperty());
                task.setOnSucceeded(e -> {
                    Scene result = (Scene) task.getValue();
                    ekran.getChildren().remove(pi2);
                    Node node = (Node) event.getSource();
                    Stage stage = (Stage) node.getScene().getWindow();
                    stage.setScene(result);
                });
                Thread thread = new Thread(task);
                thread.start();
            }
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ToggleGroup tg = new ToggleGroup();
        normal.setToggleGroup(tg);
        premium.setToggleGroup(tg);

        tg.selectedToggleProperty().addListener(new ChangeListener<>() {
            public void changed(ObservableValue<? extends Toggle> ob,
                                Toggle o, Toggle n) {

                RadioButton rb = (RadioButton) tg.getSelectedToggle();

                if (rb != null) {
                    abonelik = rb.getText();
                }
            }
        });
    }

    private Task girisGorev() {
        return new Task() {
            @Override
            protected Object call() throws Exception {
                Scene scene;
                try {
                    if (user.isAdminlik()) {
                        scene = new Scene(FXMLLoader.load(getClass().getResource("../FXMLs/admin.fxml")));
                    } else {
                        scene = new Scene(FXMLLoader.load(getClass().getResource("../FXMLs/home.fxml")));
                    }
                    return scene;
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
                return false;
            }
        };
    }

    private String login() {
        String email = txtEmail.getText();
        String sifre = txtSifre.getText();
        String sql = "select * from users where email = ?";
        DbHelper helper = new DbHelper();
        PreparedStatement statement;
        ResultSet resultSet;

        try (Connection connection = helper.getConnection()) {
            statement = connection.prepareStatement(sql);
            statement.setString(1, email);
            resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                lblError.setTextFill(Color.TOMATO);
                lblError.setText("Kayıt Bulunamadı! Lütfen yeni kayıt oluşturun.");
                return "false";
            } else {
                if (sifre.equals(resultSet.getString("sifre"))) {
                    user = new User(resultSet.getInt("ID"), resultSet.getString("kullaniciAdi"), resultSet.getString("email"), resultSet.getString("sifre"), resultSet.getString("abonelik"), resultSet.getString("ulke"), resultSet.getBoolean("adminlik"), resultSet.getInt("takipci"));
                    lblError.setTextFill(Color.GREEN);
                    lblError.setText("Giriş Başarılı! Yönlendiriliyorsunuz...");
                    return "true";
                } else {
                    lblError.setTextFill(Color.TOMATO);
                    lblError.setText("Hatalı Şifre! Lütfen tekrar deneyin.");
                    return "false";
                }
            }
        } catch (SQLException exception) {
            helper.showErrorMessage(exception);
            return "exception";
        }
    }

    private String kayit() {
        String email = txtEmail2.getText();
        String sifre = txtSifre2.getText();
        String kullanici = txtKullaniciAdi.getText();
        String sql = "select * from users where email = ?";

        Connection connection;
        DbHelper helper = new DbHelper();
        PreparedStatement statement;
        ResultSet resultSet;

        try {
            connection = helper.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, email);
            resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                if (email.equals("") || sifre.equals("") || kullanici.equals("") || abonelik == null) {
                    lblError2.setTextFill(Color.TOMATO);
                    lblError2.setText("Lütfen tüm alanları doldurun.");
                    return "false";
                } else {
                    connection.close();
                    connection = helper.getConnection();
                    sql = "insert into users (kullaniciAdi, email, sifre, abonelik, ulke, adminlik) values(?,?,?,?,'TUR','0')";
                    statement = connection.prepareStatement(sql);
                    statement.setString(1, kullanici);
                    statement.setString(2, email);
                    statement.setString(3, sifre);
                    statement.setString(4, abonelik.toUpperCase(Locale.ENGLISH));
                    statement.executeUpdate();

                    connection.close();
                    connection = helper.getConnection();
                    sql = "select ID from users where email = ?";
                    statement = connection.prepareStatement(sql);
                    statement.setString(1, email);
                    resultSet = statement.executeQuery();
                    if (resultSet.next())
                        user = new User(resultSet.getInt("ID"), kullanici, email, sifre, abonelik.toUpperCase(), "TUR", false, 0);

                    String[] listeler = new String[]{"Pop", "Jazz", "Klasik"};
                    sql = "insert into playlists (listeAdi, userID) values(?,?)";
                    for (String listeAdi : listeler) {
                        connection.close();
                        connection = helper.getConnection();
                        statement = connection.prepareStatement(sql);
                        statement.setString(1, listeAdi);
                        statement.setInt(2, user.getId());
                        statement.executeUpdate();
                    }

                    lblError2.setTextFill(Color.GREEN);
                    lblError2.setText("Kayıt Başarılı! Yönlendiriliyorsunuz...");
                    return "true";
                }
            } else {
                lblError2.setTextFill(Color.TOMATO);
                lblError2.setText("Kayıt Mevcut! Lütfen giriş yapmayı deneyin.");
                return "false";
            }
        } catch (SQLException exception) {
            helper.showErrorMessage(exception);
            return "exception";
        }
    }
}