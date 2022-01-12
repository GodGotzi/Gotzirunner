package at.gotzi.itsmanager;

import at.gotzi.itsmanager.api.WindowScene;
import at.gotzi.itsmanager.file.TestSaver;
import at.gotzi.itsmanager.handler.CoolDownHandler;
import at.gotzi.itsmanager.handler.SceneHandler;
import at.gotzi.itsmanager.handler.StageHandler;
import at.gotzi.itsmanager.handler.WorkspaceHandler;
import at.gotzi.itsmanager.panel.control.ErrorPanel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ITSManager extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    private static ITSManager instance;

    public static ITSManager getInstance() {
        return instance;
    }

    public static Image image;

    private Stage primaryStage;

    private WorkspaceHandler workspaceHandler;

    private final SceneHandler sceneHandler;

    private final CoolDownHandler coolDownHandler;

    private final List<String> lastWorkspaces = new ArrayList<>();
    private String compilerPath;

    private final String fileLastWorkspaces = "workspaces.json";

    public ITSManager() throws IOException {
        instance = this;
        File wsSaver = new File(fileLastWorkspaces);
        if (!wsSaver.exists())
            wsSaver.createNewFile();

        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader(wsSaver));
            JSONObject jsonObject = (JSONObject) obj;
            compilerPath = jsonObject.get("compiler").toString();
            JSONArray jsonArray = (JSONArray) jsonObject.get("workspaces");
            jsonArray.forEach(o -> lastWorkspaces.add((String)o));
        } catch (Exception ignored) {}

        this.coolDownHandler = new CoolDownHandler();
        this.sceneHandler = new SceneHandler(this);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/scenes/Welcome.fxml")));
        Scene scene = new Scene(root, Color.WHITE);
        sceneHandler.setCurrentScene(WindowScene.Welcome, false, false);
        image = new Image("/GotziRunnerLogo2.png");
        primaryStage.getIcons().add(image);
        primaryStage.setTitle("Informatik Test System Dev: Elias Gottsbacher (Gotzi)");
        primaryStage.setScene(scene);
        primaryStage.show();

        primaryStage.setOnCloseRequest(windowEvent -> {
            if (workspaceHandler != null) {
                this.workspaceHandler.getWorkspace().getTests().forEach(t -> {
                    new TestSaver(t, this);
                });
            }

            StageHandler.getStageList().forEach(Stage::close);
        });

        this.primaryStage = primaryStage;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public SceneHandler getSceneHandler() {
        return sceneHandler;
    }

    public void setWorkspaceHandler(WorkspaceHandler workspaceHandler) {
        this.workspaceHandler = workspaceHandler;
    }

    public void setCompilerPath(String compilerPath) {
        this.compilerPath = compilerPath;
        saveData(compilerPath);
    }

    private void saveData(String compilerPath) {
        JSONObject object = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        jsonArray.addAll(lastWorkspaces);
        object.put("workspaces", jsonArray);
        object.put("compiler", compilerPath);

        try (FileWriter fileWriter = new FileWriter(fileLastWorkspaces)) {
            fileWriter.write(object.toString());
            fileWriter.flush();
        } catch (IOException e) {
            new ErrorPanel("Couldn't write JSON Information about last Workspaces", e);
        }
    }

    public String getCompilerPath() {
        return compilerPath;
    }

    public void addToLastWorkspaces(String folderPath) {
        if (lastWorkspaces.contains(folderPath))
            return;

        lastWorkspaces.add(folderPath);

        saveData(compilerPath);
    }

    public List<String> getLastWorkspaces() {
        return lastWorkspaces;
    }

    public WorkspaceHandler getWorkspaceHandler() {
        return workspaceHandler;
    }

    public CoolDownHandler getCoolDownHandler() {
        return coolDownHandler;
    }
}
