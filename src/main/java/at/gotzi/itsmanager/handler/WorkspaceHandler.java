package at.gotzi.itsmanager.handler;

import at.gotzi.itsmanager.ITSManager;
import at.gotzi.itsmanager.api.workspace.Test;
import at.gotzi.itsmanager.api.WindowScene;
import at.gotzi.itsmanager.api.workspace.Workspace;
import at.gotzi.itsmanager.api.WorkspaceBuilder;

import java.io.File;

public class WorkspaceHandler {

    private final File currentWorkspace;
    private final ITSManager itsManager;
    private final Workspace workspace;

    private final File testFolder;
    private final File dataFolder;
    private final File computeFolder;

    public WorkspaceHandler(File currentWorkspace, ITSManager itsManager) {
        this.currentWorkspace = currentWorkspace;
        this.itsManager = itsManager;

        testFolder = new File(currentWorkspace.getPath() + "//tests");
        if (!testFolder.exists())
            testFolder.mkdirs();

        dataFolder = new File(currentWorkspace.getPath() + "//data");
        if (!dataFolder.exists())
            dataFolder.mkdirs();

        computeFolder = new File(currentWorkspace.getPath() + "//compute");
        if (!computeFolder.exists()) computeFolder.mkdirs();

        WorkspaceBuilder workspaceBuilder = new WorkspaceBuilder(testFolder.listFiles(), dataFolder.listFiles(), testFolder.getPath());
        this.workspace = workspaceBuilder.build();
    }



    public Workspace getWorkspace() {
        return workspace;
    }

    public int getSize() {
        return testFolder.listFiles().length;
    }

    public void deleteTest(Test test) {
        File file = new File(testFolder.getPath() + "//" + test.getName() + ".json");
        file.delete();
        this.workspace.getTests().remove(test);
        this.workspace.getStudentResults().remove(test.getName());
    }

    public File getTestFolder() {
        return testFolder;
    }

    public File getDataFolder() {
        return dataFolder;
    }

    public File getComputeFolder() {
        return computeFolder;
    }

    public File getCurrentWorkspace() {
        return currentWorkspace;
    }

    public static void chooseWorkSpace(File file) {
        if (file != null && file.exists()) {
            WorkspaceHandler workspaceHandler = new WorkspaceHandler(file, ITSManager.getInstance());
            ITSManager.getInstance().setWorkspaceHandler(workspaceHandler);
            ITSManager.getInstance().addToLastWorkspaces(file.getPath());
            ITSManager.getInstance().getSceneHandler().setCurrentScene(WindowScene.Overview, true, false);
        }
    }
}
