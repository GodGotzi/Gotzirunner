package at.gotzi.itsmanager.file;

import at.gotzi.itsmanager.ITSManager;
import at.gotzi.itsmanager.api.Sorter;
import at.gotzi.itsmanager.api.workspace.KFG;
import at.gotzi.itsmanager.api.workspace.Test;
import at.gotzi.itsmanager.panel.control.ErrorPanel;
import at.gotzi.itsmanager.panel.control.InfoPanel;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class TestCreator {

    private final File folder;
    private final ITSManager itsManager;

    private Test test;

    public TestCreator(String name, ITSManager itsManager) {
        this.itsManager = itsManager;
        folder = new File(itsManager.getWorkspaceHandler().getCurrentWorkspace().getPath() + "//tests");


        this.createFolder();
        try {
            this.compute(name);
        } catch (IOException | ParseException | InterruptedException e) {
            new ErrorPanel("Error at creating Test File", e);
        }
    }

    public void createFolder() {
        if (!folder.exists())
            folder.mkdirs();
    }

    public void compute(String name) throws IOException, ParseException, InterruptedException {
        File f = new File(folder.getPath() + "//" + name + ".json");
        if (f.exists()) {
            new InfoPanel("Test with this name already exists");
            return;
        }

        int index =  Sorter.getHighestJsonIndex(itsManager.getWorkspaceHandler().getWorkspace().getTests())+1;

        JSONObject object = new JSONObject();
        object.put("index", index);
        object.put("name", name);
        JSONArray jsonArray = new JSONArray();
        object.put("questions", jsonArray);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("kfg1", -1);
        jsonObject.put("kfg2", -1);
        jsonObject.put("kfg3", -1);
        jsonObject.put("kfg4", -1);
        object.put("kfg", jsonObject);
        f.createNewFile();

        FileWriter fileWriter = new FileWriter(f);
        fileWriter.write(object.toString());
        fileWriter.close();
        f.setWritable(true);

        this.test = new Test(name, index,
                new ArrayList<>(), new KFG(-1, -1, -1, -1));
        itsManager.getWorkspaceHandler().getWorkspace().getTests().add(test);
    }

    public Test getTest() {
        return test;
    }
}
