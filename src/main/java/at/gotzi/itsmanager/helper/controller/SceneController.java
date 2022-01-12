package at.gotzi.itsmanager.helper.controller;

import at.gotzi.itsmanager.ITSManager;
import at.gotzi.itsmanager.api.WindowScene;
import at.gotzi.itsmanager.file.TestSaver;
import at.gotzi.itsmanager.handler.StageHandler;
import at.gotzi.itsmanager.handler.WorkspaceHandler;
import javafx.event.ActionEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class SceneController {

    private final ITSManager itsManager = ITSManager.getInstance();

    public ITSManager getITSManager() {
        return itsManager;
    }

    //open
    public void fileOpen(ActionEvent actionEvent) throws IOException {
        DirectoryChooser dC = new DirectoryChooser();
        File directory = dC.showDialog(null);
        WorkspaceHandler.chooseWorkSpace(directory);
    }

    //closing File switching to Main menu
    public void fileClose(ActionEvent actionEvent) throws IOException {
        if (itsManager.getWorkspaceHandler() != null) {
            itsManager.getWorkspaceHandler().getWorkspace().getTests().forEach(t -> {
                new TestSaver(t, itsManager);
            });
        }

        StageHandler.getStageList().stream().filter(s -> itsManager.getPrimaryStage() != s).forEach(Stage::close);
        ITSManager.getInstance().getSceneHandler().setCurrentScene(WindowScene.Welcome, true, false);
    }

    //Help
    public void helpSend(ActionEvent actionEvent) {
    }

}
