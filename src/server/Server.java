package server;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Application {

    static Scene theScene;
    static Stage theStage;

    static TextArea ta;
    static TextField tf;
    static Button bt;

    @Override
    public void start(Stage primaryStage) throws Exception {
        theStage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Just Chat");
        theScene = new Scene(root, 300, 500);
        theStage.setScene(theScene);
        theStage.show();

        tf = (TextField) theScene.lookup("#messageField");
        ta = (TextArea) theScene.lookup("#chatWindow");
        bt = (Button) theScene.lookup("#sendButton");
        if (tf == null || ta == null || bt == null) {
            System.out.println("Не смог обнаружить один из необходимых визуальных элементов на сцене!");
            stop();
        }

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                runServer();
            }
            });
    }

    public static void runServer(){
        Socket socket = null;
        try {
            ServerSocket serverSocket = new ServerSocket(8189);
            ta.appendText("\n" + "Сервер запущен, ожидаем подключения...");
            System.out.println("Сервер запущен, ожидаем подключения...");
            socket = serverSocket.accept();
            ta.appendText("\n" + "Клиент подключился");
            System.out.println("Клиент подключился");
            DataInputStream in = new DataInputStream(socket.getInputStream());
            System.out.println("Input stream assigned");
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            System.out.println("Output stream assigned");
            theStage.show();

            while (true) {
                System.out.println("Enter listening mode");
                String str = in.readUTF();
                System.out.println("Message " + str + " received.");
                if (str.equals("/end"))
                    break;
                ta.setText(ta.getText() + "\n" + "КЛИЕНТ: " + str);
            }
        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        launch(args);
    }
}
