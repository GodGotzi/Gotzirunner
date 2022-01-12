package at.gotzi.itsmanager.controller;

import at.gotzi.itsmanager.ITSManager;
import at.gotzi.itsmanager.helper.controller.SceneController;
import at.gotzi.itsmanager.handler.WorkspaceHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class WelcomeController extends SceneController implements Initializable {

    @FXML
    private ChoiceBox<String> choiceBox;

    @FXML
    private TextField compilerPath;

    @FXML
    public void compilerPathAction(ActionEvent actionEvent) {
        getITSManager().setCompilerPath(compilerPath.getText());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        choiceBox.getItems().addAll(ITSManager.getInstance().getLastWorkspaces());
        choiceBox.setOnAction(this::getChoice);
        String path = getITSManager().getCompilerPath();
        if (path != null) compilerPath.setText(path);
    }

    public void getChoice(ActionEvent actionEvent) {
        String choice = choiceBox.getValue();
        File directory = new File(choice);
        WorkspaceHandler.chooseWorkSpace(directory);
    }
}
