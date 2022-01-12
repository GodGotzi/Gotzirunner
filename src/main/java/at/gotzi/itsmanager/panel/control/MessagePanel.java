package at.gotzi.itsmanager.panel.control;

import at.gotzi.itsmanager.ITSManager;
import at.gotzi.itsmanager.handler.StageHandler;
import at.gotzi.itsmanager.panel.Panel;
import javafx.event.EventType;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public abstract class MessagePanel extends Panel {

    private String input;

    public MessagePanel(String msg) {
        Stage stage = new Stage();
        Group group = new Group();
        TextArea textArea = new TextArea();
        textArea.setText(msg);
        textArea.setLayoutX(10);
        textArea.setLayoutY(5);
        textArea.setMaxHeight(100);
        textArea.setEditable(false);
        group.getChildren().add(textArea);

        TextField textField = new TextField();
        textField.setLayoutX(182.5);
        textField.setLayoutY(125);
        group.getChildren().add(textField);

        Button button = new Button();
        button.setLayoutX(200);
        button.setLayoutY(170);
        button.setText("OK");
        setPassed(false);

        button.addEventHandler(EventType.ROOT, event -> {
            if (event.getEventType().getName().equals("ACTION")) {
                if (textField.getText().equals(""))
                    new InfoPanel("You need to specify a name for your Test");
                else {
                    input = textField.getText();
                    setPassed(true);
                    passed();
                    stage.close();
                }
            }
        });

        Button button2 = new Button();
        button2.setLayoutX(260);
        button2.setLayoutY(170);
        button2.setText("CANCEL");
        setPassed(false);

        button2.addEventHandler(EventType.ROOT, event -> {
            if (event.getEventType().getName().equals("ACTION"))
                stage.close();
        });

        group.getChildren().add(button);
        group.getChildren().add(button2);
        Scene scene = new Scene(group, 500, 200, Color.WHITE);
        stage.setTitle("Message Input Panel");
        stage.getIcons().add(ITSManager.image);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        StageHandler.getStageList().add(stage);
    }

    public abstract void passed();

    public String getInput() {
        return input;
    }
}
