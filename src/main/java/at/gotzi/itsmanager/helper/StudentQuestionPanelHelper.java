package at.gotzi.itsmanager.helper;

import at.gotzi.itsmanager.ITSManager;
import at.gotzi.itsmanager.api.workspace.StudentTestResult;
import at.gotzi.itsmanager.api.workspace.StudentTestResultQuestion;
import at.gotzi.itsmanager.api.workspace.Test;
import at.gotzi.itsmanager.controller.panelcontroll.StudentQuestionPanelController;
import at.gotzi.itsmanager.handler.StageHandler;
import at.gotzi.itsmanager.panel.control.ErrorPanel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class StudentQuestionPanelHelper {

    public StudentQuestionPanelHelper(StudentTestResult studentResult, StudentTestResultQuestion studentResultQuestion, Test test) {
        Stage stage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/scenes/Questionpanel.fxml")));
        } catch (IOException e) {
            new ErrorPanel("Error while loading StudentPanel from fxml file", e);
            return;
        }

        Scene scene = new Scene(root, Color.WHITE);

        stage.setScene(scene);
        stage.getIcons().add(ITSManager.image);
        stage.setTitle("Question Panel");
        stage.setResizable(false);
        stage.show();
        StageHandler.getStageList().add(stage);

        StudentQuestionPanelController.getLastInstance().initQuestionController(studentResult, studentResultQuestion, test);
    }
}
