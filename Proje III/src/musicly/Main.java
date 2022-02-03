package musicly;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.sql.SQLException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("FXMLs/login.fxml"));
        primaryStage.setTitle("musicly");
        primaryStage.getIcons().add(new Image("file:C:\\Users\\MuhammedAli\\IdeaProjects\\prolab3\\src\\img\\icon.png"));
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(new Scene(root, 1536,824));
        primaryStage.show();
    }

    public static void main(String[] args) throws SQLException {
        launch(args);
    }
}