package server;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import javafx.scene.input.KeyEvent;

import static server.Server.*;

public class Controller{

    @FXML
    public void onSendButtonClicked(){
        if (!tf.getText().equals("")) {
            ta.setText(ta.getText().concat("\n").concat(tf.getText()));
            tf.setText("");
            bt.setDisable(true);
        }
    }

    @FXML
    public void onKeyPressed(KeyEvent k){
        switch(k.getCode()){
            case ENTER:{
                onSendButtonClicked();
                break;
            }
            default: bt.setDisable(false);
        }
    }

    @FXML
    public void onClearChat(){
        ta.setText("");
    }

    public void onClose(){
        System.out.println("Trying to close...");
        Server.theStage.close();
    }

    public void onHelp(){
        TitledPane p = (TitledPane) Server.theScene.lookup("#helpPane");
        TextArea t = (TextArea) Server.theScene.lookup("#helpContent");
        t.setText("JUST CHAT\nVersion 1.0\nBy Alexey Zimin\n\nGeekBrains JAVA 2 training course");
        p.setVisible(true);
    }

    public void onCloseHelp(){
        System.out.println("Закрываю помощь...");
        TitledPane p = (TitledPane) Server.theScene.lookup("#helpPane");
        p.setVisible(false);
    }

}
