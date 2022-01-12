package at.gotzi.itsmanager.controller.panelcontroll;

import at.gotzi.itsmanager.ITSManager;
import at.gotzi.itsmanager.api.workspace.Test;
import at.gotzi.itsmanager.api.workspace.question.Question;
import at.gotzi.itsmanager.api.workspace.question.QuestionExample;
import at.gotzi.itsmanager.api.workspace.question.QuestionTest;
import at.gotzi.itsmanager.code.compile.CCodeStructureHelper;
import at.gotzi.itsmanager.code.compile.CCompiler;
import at.gotzi.itsmanager.code.compute.CTester;
import at.gotzi.itsmanager.handler.WorkspaceHandler;
import at.gotzi.itsmanager.helper.Update;
import at.gotzi.itsmanager.panel.control.ErrorPanel;
import at.gotzi.itsmanager.panel.control.InfoPanel;
import at.gotzi.itsmanager.view.ItemView;
import at.gotzi.itsmanager.view.QuestionTestItemView;
import eu.mihosoft.monacofx.MonacoFX;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;

import java.io.*;
import java.util.Arrays;

public class QuestionEditPanelController {

    public QuestionEditPanelController() {
        lastInstance = this;
    }

    @FXML
    private TextField points;
    
    @FXML
    private TextArea descriptionArea;

    @FXML
    private MonacoFX monacoFX;

    @FXML
    private TextArea output;

    @FXML
    private Label testLabel;

    @FXML
    private Label questionLabel;

    @FXML
    private CheckBox darkTheme;

    @FXML
    private ListView<QuestionTestItemView<QuestionExample>> examples;

    @FXML
    private ListView<QuestionTestItemView<QuestionTest>> tests;

    private Test test;
    private Question question;
    private Update<String> update;
    private Update<Integer> pointsUpdate;

    public void onDescriptionEdit(KeyEvent ignoredActionEvent) {
        update.update(descriptionArea.getText());
        question.setDescription(descriptionArea.getText());
    }

    public void computeResults(ActionEvent ignoredActionEvent) {
        WorkspaceHandler workspaceHandler = ITSManager.getInstance().getWorkspaceHandler();
        File txtFile = new File(workspaceHandler.getComputeFolder().getPath() + "//" + test.getName()
                + "question" + (test.getQuestionList().indexOf(question)+1) + ".c");

        File exeFile = new File(workspaceHandler.getComputeFolder().getPath() + "//" + test.getName()
                + "question" + (test.getQuestionList().indexOf(question)+1) + ".exe");

        try {
            String error = new CCompiler(ITSManager.getInstance().getCompilerPath(),
                    new CCodeStructureHelper(Arrays.stream(monacoFX.getEditor().getDocument().getText()
                            .split("\\r?\\n")).toList()).build()
                    , txtFile, exeFile).compile();
            output.setText(error);
        } catch (Exception e) {
            new ErrorPanel("Failed while compiling C programm", e);
            return;
        }

        while (txtFile.exists()) txtFile.delete();
        runExamplesAndTests(exeFile);
    }

    private void runExamplesAndTests(File exeFile) {
        if (exeFile.exists()) {
            for (QuestionExample questionExample : question.getExamples()) {
                try {
                    examples.getItems().get(question.getExamples().indexOf(questionExample))
                            .updateExpected(new CTester(questionExample, exeFile).compute());
                } catch (Exception e) {
                    new ErrorPanel("Error while running C Programm", e);
                    break;
                }
            }

            for (QuestionTest questionTest : question.getTests()) {
                try {
                    tests.getItems().get(question.getTests().indexOf(questionTest))
                            .updateExpected(new CTester(questionTest, exeFile).compute());
                } catch (Exception e) {
                    new ErrorPanel("Error while running C Programm", e);
                    break;
                }
            }
        }

        while (exeFile.exists()) exeFile.delete();
    }

    public void initQuestionView(Question question, Test test, Update<String> updateDescription, Update<Integer> updateInteger) {
        this.question = question;
        this.test = test;
        this.update = updateDescription;
        this.pointsUpdate = updateInteger;
        this.initCodeArea();
        this.initDescriptionArea();
        this.initOutputArea();
        this.initLabels();
        this.initExamples();
        this.initTests();
    }
    
    public void changePoints(ActionEvent actionEvent) {
        int num;
        try {
            num = Integer.parseInt(points.getText());
            if (num <= 0) num = -1;
            question.setPoints(num);
            pointsUpdate.update(num);
        } catch (Exception e) {
            new InfoPanel("Please use an Integer as Value");
        }
    }

    public void changeTheme(ActionEvent ignoredActionEvent) {
        if (darkTheme.isSelected()) monacoFX.getEditor().setCurrentTheme("vs-dark");
        else monacoFX.getEditor().setCurrentTheme("vs-light");
    }

    public void createTest(ActionEvent actionEvent) {
        QuestionTest questionTest = new QuestionTest();
        question.getTests().add(questionTest);
        tests.getItems().add(new QuestionTestItemView<>(question, questionTest) {
            @Override
            public void onButtonAction(Action action, ItemView itemView) {
                if (action == Action.Delete) {
                    tests.getItems().remove((QuestionTestItemView<QuestionTest>) itemView);
                    question.getTests().remove(questionTest);
                }
            }
        });
    }

    public void createExample(ActionEvent actionEvent) {
        QuestionExample questionExample = new QuestionExample();
        question.getExamples().add(questionExample);
        examples.getItems().add(new QuestionTestItemView<>(question, questionExample) {

            @Override
            public void onButtonAction(Action action, ItemView itemView) {
                if (action == Action.Delete) {
                    examples.getItems().remove((QuestionTestItemView<QuestionTest>) itemView);
                    question.getExamples().remove(questionExample);
                }
            }
        });
    }

    public void initExamples() {
        question.getExamples().forEach(test -> {
            examples.getItems().add(new QuestionTestItemView<>(question, test) {
                @Override
                public void onButtonAction(Action action, ItemView itemView) {
                    if (action == Action.Delete) {
                        examples.getItems().remove((QuestionTestItemView<QuestionExample>) itemView);
                        question.getExamples().remove(test);
                    }
                }
            });
        });
    }

    public void initTests() {
        question.getTests().forEach(test -> {
            tests.getItems().add(new QuestionTestItemView<>(question, test) {
                @Override
                public void onButtonAction(Action action, ItemView itemView) {
                    if (action == Action.Delete) {
                        tests.getItems().remove((QuestionTestItemView<QuestionTest>) itemView);
                        question.getTests().remove(test);
                    }
                }
            });
        });
    }

    public void initOutputArea() {
        output.setEditable(false);
    }

    public void initLabels() {
        testLabel.setText(test.getName());
        questionLabel.setText("Question " + (test.getQuestionList().indexOf(question)+1));
        points.setText(String.valueOf(question.getPoints()));
    }

    public void initDescriptionArea() {
        descriptionArea.setText(question.getDescription());
    }

    public void initCodeArea() {
        monacoFX.getEditor().setCurrentLanguage("c");
        monacoFX.getEditor().setCurrentTheme("vs-light");
    }

    private static QuestionEditPanelController lastInstance;

    public static QuestionEditPanelController getLastInstance() {
        return lastInstance;
    }
}
