package at.gotzi.itsmanager.api;

import at.gotzi.itsmanager.api.workspace.*;
import at.gotzi.itsmanager.api.workspace.question.QuestionExample;
import at.gotzi.itsmanager.api.workspace.question.Question;
import at.gotzi.itsmanager.api.workspace.question.Input;
import at.gotzi.itsmanager.api.workspace.question.QuestionTest;
import at.gotzi.itsmanager.panel.control.ErrorPanel;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorkspaceBuilder {

    private final File[] files;
    private final File[] data;
    private final String path;

    public WorkspaceBuilder(File[] files, File[] data, String path) {
        this.files = files;
        this.path = path;
        this.data = data;
    }

    public Workspace build() {
        List<Test> testList = new ArrayList<>();
        Map<String, List<StudentTestResult>> studentResults = new HashMap<>();
        for (File f : files) {
            try {
                FileReader fileReader = new FileReader(f);
                JSONParser jsonParser = new JSONParser();
                JSONObject jsonObject = (JSONObject) jsonParser.parse(fileReader);
                String name = jsonObject.get("name").toString();
                int index = Integer.parseInt(jsonObject.get("index").toString());
                JSONArray jsonQuestions = (JSONArray) jsonObject.get("questions");
                List<Question> questions = new ArrayList<>();
                for (Object j : jsonQuestions) {
                    JSONObject question = (JSONObject) j;
                    String description = question.get("description").toString();
                    int points = Integer.parseInt(question.get("points").toString());
                    List<QuestionExample> questionExamples = new ArrayList<>();
                    JSONArray jsonExamples = (JSONArray) question.get("examples");
                    getExamples(questionExamples, jsonExamples);

                    List<QuestionTest> tests = new ArrayList<>();
                    JSONArray jsonTests = (JSONArray) question.get("tests");
                    getTests(tests, jsonTests);
                    questions.add(new Question(description, questionExamples, tests, points));
                }
                
                JSONObject jsonObject1 = (JSONObject) jsonObject.get("kfg");
                double kfg1 = Double.parseDouble(jsonObject1.get("kfg1").toString());
                double kfg2 = Double.parseDouble(jsonObject1.get("kfg2").toString());
                double kfg3 = Double.parseDouble(jsonObject1.get("kfg3").toString());
                double kfg4 = Double.parseDouble(jsonObject1.get("kfg4").toString());

                testList.add(new Test(name, index, questions, new KFG(kfg1, kfg2, kfg3, kfg4)));
                fileReader.close();
            } catch (Exception e) {
                new ErrorPanel("Something is wrong with json layout in one test file", e);
            }
        }

        for (File f: data) {
            try {
                FileReader fileReader = new FileReader(f);
                JSONParser jsonParser = new JSONParser();
                JSONObject jsonObject = (JSONObject) jsonParser.parse(fileReader);
                JSONArray jsonStudentResults = (JSONArray) jsonObject.get("results");
                List<StudentTestResult> results = new ArrayList<>();

                for (Object j : jsonStudentResults) {
                    JSONObject result = (JSONObject) j;
                    String name = result.get("name").toString();
                    int kn = Integer.parseInt(result.get("kn").toString());
                    int time = Integer.parseInt(result.get("time").toString());
                    double points = Integer.parseInt(result.get("points").toString());
                    double percent = Double.parseDouble(result.get("percent").toString());
                    List<StudentTestResultQuestion> studentResultQuestions = new ArrayList<>();
                    JSONArray jsonStudentResultQuestions = (JSONArray) result.get("questions");

                    for (Object o : jsonStudentResultQuestions) {
                        if (o instanceof JSONObject jsonObject1) {
                            String sourceCode = jsonObject1.get("sourcecode").toString();
                            double p = Double.parseDouble(jsonObject1.get("points").toString());
                            JSONArray jsonArray = (JSONArray) jsonObject1.get("tests");
                            List<StudentTestResultQuestionTest> studentResultQuestionTests = new ArrayList<>();
                            for (Object o1 : jsonArray) {
                                JSONObject jsonObject2 = (JSONObject) o1;
                                String output = jsonObject2.get("output").toString();
                                studentResultQuestionTests.add(new StudentTestResultQuestionTest(output));
                            }

                            studentResultQuestions.add(new StudentTestResultQuestion(p, sourceCode, studentResultQuestionTests));
                        }
                    }

                    results.add(new StudentTestResult(name, kn, time, points, percent, studentResultQuestions));
                }

                studentResults.put(f.getName().split("\\.")[0], results);
                fileReader.close();
            } catch (Exception e) {
                e.printStackTrace();
                new ErrorPanel("Something is wrong with json layout in one test file", e);
            }
        }

        testList.stream().filter(t -> studentResults.get(t.getName()) == null).forEach(t ->
                studentResults.put(t.getName(), new ArrayList<>()));

        Workspace workspace = new Workspace(testList, studentResults, path);
        return workspace;
    }

    private boolean getBoolean(String bool) {
        return switch (bool) {
            case "true" -> true;
            case "false" -> false;
            default -> false;
        };
    }

    private void getExamples(List<QuestionExample> questionExamples, JSONArray jsonExamples) {
        for (Object k : jsonExamples) {
            JSONObject jsonExample = (JSONObject) k;
            List<Input> inputs = new ArrayList<>();
            JSONArray jsonArray = (JSONArray) jsonExample.get("inputs");
            jsonArray.forEach(e -> inputs.add(new Input(e.toString())));
            String expected = jsonExample.get("expected").toString();
            QuestionExample questionExample = new QuestionExample();
            questionExample.setExpected(expected);
            questionExample.getInputs().addAll(inputs);
            questionExamples.add(questionExample);
        }
    }

    private void getTests(List<QuestionTest> tests, JSONArray jsonTests) {
        for (Object k : jsonTests) {
            JSONObject jsonTest = (JSONObject) k;
            List<Input> inputs = new ArrayList<>();
            JSONArray jsonArray = (JSONArray) jsonTest.get("inputs");
            jsonArray.forEach(e -> inputs.add(new Input(e.toString())));
            String expected = jsonTest.get("expected").toString();
            QuestionTest test = new QuestionTest();
            test.setExpected(expected);
            test.getInputs().addAll(inputs);
            tests.add(test);
        }
    }

}
