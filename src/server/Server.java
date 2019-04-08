package server;

import javafx.application.Application;
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

    public static ServerSocket serverSocket;
    public static DataInputStream in;
    public static DataOutputStream out;

    @Override
    public void start(Stage primaryStage) throws Exception {
        theStage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Just Chat SERVER");
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

        new Thread(() -> {
            ta.appendText(  "Сервер запущен, ожидаем подключения..."+"\n");
            try {
                serverSocket = new ServerSocket(8189);
                Socket socket = serverSocket.accept();
                ta.appendText("Клиент подключился: "+socket+"\n");
                in = new DataInputStream(socket.getInputStream());
                ta.appendText("Входящий поток создан."+"\n");
                out = new DataOutputStream(socket.getOutputStream());
                ta.appendText("Исходящий поток создан."+"\n");

                while (true) {
                    ta.appendText("Ожидание сообщений от клиента..."+"\n");
                    String str = in.readUTF();
                    if (str.equals("/end")) {
                        ta.appendText("Клиент разорвал соединение.");
                        socket.close();
                        break;
                    }
                    ta.appendText( "КЛИЕНТ: " + str + "\n");
                }
            } catch (
                    IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static void main(String[] args) {

        launch(args);
    }
}
