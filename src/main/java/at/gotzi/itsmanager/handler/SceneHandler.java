package at.gotzi.itsmanager.handler;

import at.gotzi.itsmanager.ITSManager;
import at.gotzi.itsmanager.api.WindowScene;
import at.gotzi.itsmanager.panel.control.ErrorPanel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.util.Objects;

public class SceneHandler {

    private WindowScene currentScene;
    private final ITSManager itsManager;

    public SceneHandler(ITSManager itsManager) {
        this.itsManager = itsManager;
    }

    public void setCurrentScene(WindowScene windowScene, boolean compute, boolean ignore) {
        if (compute && currentScene != windowScene || compute && ignore) {
            Parent root = null;
            try {
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/scenes/" + windowScene.name() + ".fxml")));
                Scene scene = new Scene(root, Color.WHITE);
                this.itsManager.getPrimaryStage().setScene(scene);
            } catch (IOException e) {
                new ErrorPanel("Couldn't load FXML File, contact me", e);
            }
        }

        this.currentScene = windowScene;
    }



    public WindowScene getCurrentScene() {
        return currentScene;
    }
}
