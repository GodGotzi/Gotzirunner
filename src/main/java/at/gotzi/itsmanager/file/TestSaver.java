package at.gotzi.itsmanager.file;

import at.gotzi.itsmanager.ITSManager;
import at.gotzi.itsmanager.api.workspace.Test;
import at.gotzi.itsmanager.api.workspace.question.Question;
import at.gotzi.itsmanager.api.workspace.question.QuestionExample;
import at.gotzi.itsmanager.api.workspace.question.QuestionTest;
import at.gotzi.itsmanager.panel.control.ErrorPanel;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class TestSaver {

    public TestSaver(Test test, ITSManager itsManager) {
        try {
            save(test, itsManager);
        } catch (IOException e) {
            new ErrorPanel("Error at saving test", e);
        }
    }

    private void save(Test test, ITSManager itsManager) throws IOException {
        File file = new File(itsManager.getWorkspaceHandler().getCurrentWorkspace()
                + "//tests//" + test.getName() + ".json");
        if (!file.exists()) file.createNewFile();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", test.getName());
        jsonObject.put("index", test.getIndex());

        JSONObject kfg = new JSONObject();
        kfg.put("kfg1", test.getKfg().getKfg1());
        kfg.put("kfg2", test.getKfg().getKfg2());
        kfg.put("kfg3", test.getKfg().getKfg3());
        kfg.put("kfg4", test.getKfg().getKfg4());
        jsonObject.put("kfg", kfg);

        JSONArray jsonArray = new JSONArray();
        for (Question question : test.getQuestionList()) {
            JSONObject jsonQuestion = new JSONObject();
            jsonQuestion.put("description", question.getDescription());
            jsonQuestion.put("points", String.valueOf(question.getPoints()));

            JSONArray jsonExamples = new JSONArray();
            convertExamples(jsonExamples, question.getExamples());
            jsonQuestion.put("examples", jsonExamples);

            JSONArray jsonTests = new JSONArray();
            convertTests(jsonTests, question.getTests());
            jsonQuestion.put("tests", jsonTests);
            jsonArray.add(jsonQuestion);
        }

        jsonObject.put("questions", jsonArray);

        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(jsonObject.toJSONString());
        fileWriter.close();
    }

    private void convertExamples(JSONArray jsonExamples, List<QuestionExample> questionExampleList) {
        for (QuestionExample questionExample : questionExampleList) {
            JSONObject jsonExample = new JSONObject();
            JSONArray inputs = new JSONArray();
            questionExample.getInputs().forEach(i -> inputs.add(i.input()));
            jsonExample.put("inputs", inputs);
            jsonExample.put("expected", questionExample.getExpected());
            jsonExamples.add(jsonExample);
        }
    }

    private void convertTests(JSONArray jsonExamples, List<QuestionTest> testList) {
        for (QuestionTest test : testList) {
            JSONObject jsonExample = new JSONObject();
            JSONArray inputs = new JSONArray();
            test.getInputs().forEach(i -> inputs.add(i.input()));
            jsonExample.put("inputs", inputs);
            jsonExample.put("expected", test.getExpected());
            jsonExamples.add(jsonExample);
        }
    }
}
