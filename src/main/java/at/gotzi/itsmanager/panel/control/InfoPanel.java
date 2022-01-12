package at.gotzi.itsmanager.panel.control;

import at.gotzi.itsmanager.ITSManager;
import at.gotzi.itsmanager.handler.StageHandler;
import at.gotzi.itsmanager.panel.Panel;
import at.gotzi.itsmanager.panel.Passed;
import javafx.event.EventType;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class InfoPanel extends Panel {

    public InfoPanel(String msg, Passed passInterface) {
        Stage stage = new Stage();
        Group group = new Group();
        TextArea textArea = new TextArea();
        textArea.setText(msg);
        textArea.setLayoutX(10);
        textArea.setMaxHeight(150);
        textArea.setEditable(false);
        group.getChildren().add(textArea);

        Button button = new Button();
        button.setLayoutX(230);
        button.setLayoutY(170);
        button.setText("OK");
        setPassed(false);

        button.addEventHandler(EventType.ROOT, event -> {
            if (event.getEventType().getName().equals("ACTION")) {
                setPassed(true);
                try {
                    passInterface.passed();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                stage.close();
            }
        });

        group.getChildren().add(button);
        Scene scene = new Scene(group, 500, 200, Color.WHITE);
        stage.setTitle("Information Panel");
        stage.getIcons().add(ITSManager.image);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        StageHandler.getStageList().add(stage);
    }

    public InfoPanel(String msg) {
        Stage stage = new Stage();
        Group group = new Group();
        TextArea textArea = new TextArea();
        textArea.setText(msg);
        textArea.setLayoutX(10);
        textArea.setMaxHeight(150);
        textArea.setEditable(false);
        group.getChildren().add(textArea);

        Button button = new Button();
        button.setLayoutX(230);
        button.setLayoutY(170);
        button.setText("OK");
        setPassed(false);
        button.addEventHandler(EventType.ROOT, event -> {
            if (event.getEventType().getName().equals("ACTION")) {
                setPassed(true);
                stage.close();
            }
        });

        group.getChildren().add(button);
        Scene scene = new Scene(group, 500, 200, Color.WHITE);
        stage.setTitle("Information Panel");
        stage.getIcons().add(ITSManager.image);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        StageHandler.getStageList().add(stage);
    }

}
