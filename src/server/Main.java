package server;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Main extends Application {

    static Scene theScene;
    static Stage theStage;

    static TextArea ta;
    static TextField tf;
    static Button bt;

    @Override
    public void start(Stage primaryStage) throws Exception{
        theStage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Just Chat");
        theScene = new Scene(root, 300, 500);
        primaryStage.setScene(theScene);
        primaryStage.show();

        tf = (TextField) theScene.lookup("#messageField");
        ta = (TextArea) theScene.lookup("#chatWindow");
        bt = (Button) theScene.lookup("#sendButton");
        if (tf==null || ta==null || bt==null){
            System.out.println("Не смог обнаружить один из необходимых визуальных элементов на сцене!");
            stop();
        }

    }

    public static void main(String[] args) {
        launch(args);
    }
}
