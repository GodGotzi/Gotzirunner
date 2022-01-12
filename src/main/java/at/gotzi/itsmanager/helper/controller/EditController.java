package at.gotzi.itsmanager.helper.controller;

import at.gotzi.itsmanager.api.WindowScene;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public abstract class EditController extends SceneController {

    private final WindowScene windowScene;

    public EditController(WindowScene windowScene) {
        this.windowScene = windowScene;
    }

    public void back(ActionEvent actionEvent) {
        WindowScene backScene = switch (windowScene) {
            case Editview, Hostview -> WindowScene.Overview;
            default -> windowScene;
        };

        save();
        getITSManager().getSceneHandler().setCurrentScene(backScene, true, false);
    }

    @FXML
    public void save(ActionEvent actionEvent) {
        save();
    }

    public abstract void save();

}
