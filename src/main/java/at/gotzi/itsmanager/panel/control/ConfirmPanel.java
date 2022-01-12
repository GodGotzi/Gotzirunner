package at.gotzi.itsmanager.panel.control;

import at.gotzi.itsmanager.ITSManager;
import at.gotzi.itsmanager.handler.StageHandler;
import at.gotzi.itsmanager.panel.Panel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public abstract class ConfirmPanel extends Panel {

    public ConfirmPanel(String msg) {
        Stage stage = new Stage();
        Group group = new Group();
        TextArea textArea = new TextArea();
        textArea.setText(msg);
        textArea.setLayoutX(10);
        textArea.setLayoutY(5);
        textArea.setMaxHeight(150);
        textArea.setEditable(false);
        group.getChildren().add(textArea);

        Button button = new Button();
        button.setLayoutX(190);
        button.setLayoutY(170);
        button.setText("CONFIRM");
        setPassed(false);

        button.setOnAction(event -> {
            setPassed(true);
            passed();
            stage.close();
        });

        Button button2 = new Button();
        button2.setLayoutX(270);
        button2.setLayoutY(170);
        button2.setText("CANCEL");
        setPassed(false);

        button2.setOnAction(event -> {stage.close();});

        group.getChildren().add(button);
        group.getChildren().add(button2);
        Scene scene = new Scene(group, 500, 200, Color.WHITE);
        stage.setTitle("Confirm Panel");
        stage.getIcons().add(ITSManager.image);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        StageHandler.getStageList().add(stage);
    }

    public abstract void passed();
}
