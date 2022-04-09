package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public static Stage mainStage;
    public static String projectPath;

    @Override
    public void start(Stage primaryStage) throws Exception{
        mainStage = primaryStage;
        projectPath = System.getProperty("user.dir");
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("مدیریت املاک");
        primaryStage.setScene(new Scene(root, 853.33, 480));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
