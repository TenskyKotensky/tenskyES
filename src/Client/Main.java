package Client;

import Database.Database;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("GUI.fxml"));
        primaryStage.setTitle("Приложение клиента");
        primaryStage.setScene(new Scene(root, 400, 400));
        primaryStage.show();
        Database.connect();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
