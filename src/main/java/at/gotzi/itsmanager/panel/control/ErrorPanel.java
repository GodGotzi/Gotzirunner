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
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.*;

public class ErrorPanel extends Panel {

    public ErrorPanel(String msg, Exception e, Passed passInterface) {
        Stage stage = new Stage();
        Group group = new Group();
        TextArea textArea = new TextArea();
        textArea.setText(msg);
        textArea.setLayoutX(10);
        textArea.setLayoutY(5);
        textArea.setMaxHeight(20);
        textArea.setEditable(false);
        group.getChildren().add(textArea);

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);

        TextArea textArea2 = new TextArea();
        textArea2.setText(sw.toString());
        textArea2.setFont(Font.font("Verdana", 10));
        textArea2.setLayoutX(35);
        textArea2.setLayoutY(50);
        textArea2.setMaxHeight(100);
        textArea2.setEditable(false);
        group.getChildren().add(textArea2);

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
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                stage.close();
            }
        });

        group.getChildren().add(button);
        Scene scene = new Scene(group, 500, 200, Color.WHITE);
        stage.setTitle("Error Panel");
        stage.getIcons().add(ITSManager.image);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        StageHandler.getStageList().add(stage);
    }

    public ErrorPanel(String msg, Exception e) {
        Stage stage = new Stage();
        Group group = new Group();
        TextArea textArea = new TextArea();
        textArea.setText(msg);
        textArea.setLayoutX(10);
        textArea.setLayoutY(5);
        textArea.setMaxHeight(20);
        textArea.setEditable(false);
        group.getChildren().add(textArea);

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);

        TextArea textArea2 = new TextArea();
        textArea2.setText(sw.toString());
        textArea2.setFont(Font.font("Verdana", 10));
        textArea2.setLayoutX(35);
        textArea2.setLayoutY(50);
        textArea2.setMaxHeight(100);
        textArea2.setEditable(false);
        group.getChildren().add(textArea2);

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
        stage.setTitle("Error Panel");
        stage.getIcons().add(ITSManager.image);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        StageHandler.getStageList().add(stage);
    }
}
