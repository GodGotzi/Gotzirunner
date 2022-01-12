package at.gotzi.itsmanager.helper;

import at.gotzi.itsmanager.ITSManager;
import at.gotzi.itsmanager.api.workspace.Test;
import at.gotzi.itsmanager.api.workspace.question.Question;
import at.gotzi.itsmanager.controller.panelcontroll.QuestionEditPanelController;
import at.gotzi.itsmanager.handler.StageHandler;
import at.gotzi.itsmanager.panel.control.ErrorPanel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class QuestionEditPanelHelper {

    public QuestionEditPanelHelper(Question question, Test test, Update<String> update, Update<Integer> updateInteger) {
        Stage stage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/scenes/QuestionEditPanel.fxml")));
        } catch (IOException e) {
            new ErrorPanel("Error while loading StudentPanel from fxml file", e);
            return;
        }

        Scene scene = new Scene(root, Color.WHITE);

        stage.setScene(scene);
        stage.getIcons().add(ITSManager.image);
        stage.setTitle("Question View");
        stage.setResizable(false);
        stage.show();
        StageHandler.getStageList().add(stage);

        QuestionEditPanelController.getLastInstance().initQuestionView(question, test, update, updateInteger);
    }
}
