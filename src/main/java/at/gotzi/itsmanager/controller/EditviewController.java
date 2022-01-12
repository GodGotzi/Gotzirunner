package at.gotzi.itsmanager.controller;

import at.gotzi.itsmanager.helper.controller.EditController;
import at.gotzi.itsmanager.api.Sorter;
import at.gotzi.itsmanager.api.WindowScene;
import at.gotzi.itsmanager.api.workspace.KFG;
import at.gotzi.itsmanager.api.workspace.StudentTestResult;
import at.gotzi.itsmanager.api.workspace.Test;
import at.gotzi.itsmanager.api.workspace.Workspace;
import at.gotzi.itsmanager.api.workspace.question.Question;
import at.gotzi.itsmanager.file.TestSaver;
import at.gotzi.itsmanager.panel.control.ConfirmPanel;
import at.gotzi.itsmanager.panel.control.ErrorPanel;
import at.gotzi.itsmanager.view.ItemView;
import at.gotzi.itsmanager.view.QuestionItemView;
import at.gotzi.itsmanager.helper.StudentPanelHelper;
import at.gotzi.itsmanager.view.StudentResultItemView;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EditviewController extends EditController {

    @FXML
    public TextField kfg1;

    @FXML
    public TextField kfg2;

    @FXML
    public TextField kfg3;

    @FXML
    public TextField kfg4;

    @FXML
    public Label testLabel;

    @FXML
    public ListView<QuestionItemView> questionListViews;

    @FXML
    public ListView<StudentResultItemView> studentResultListViews;

    private Test test;
    private Workspace workspace;
    private List<StudentTestResult> studentResults;

    public EditviewController() {
        super(WindowScene.Editview);
        lastInstance = this;
    }

    @FXML
    public void reload(ActionEvent actionEvent) {
        init(test);
    }

    private void initWorkspace() {
        workspace = getITSManager().getWorkspaceHandler().getWorkspace();
        studentResults = workspace.getStudentResults().get(test.getName());
        if (studentResults == null) studentResults = new ArrayList<>();
    }

    public void init(Test test) {
        this.test = test;
        initWorkspace();
        testLabel.setText(test.getName());

        configureKFG();
        initQuestionViews();
        initStudentResults();
    }

    private void initQuestionViews() {
        questionListViews.getItems().clear();
        for (Question question : test.getQuestionList()) {
            questionListViews.getItems().add(createNewQuestionItem(question));
        }
    }

    private void initStudentResults() {
        for (StudentTestResult studentTestResult : Sorter.sortStudentResultsViaKN(studentResults)) {
            studentResultListViews.getItems().add(createNewStudentItemView(studentTestResult));
        }
    }

    private StudentResultItemView createNewStudentItemView(StudentTestResult studentTestResult) {
        return new StudentResultItemView(studentTestResult, test) {
            @Override
            public String getGrade(double percent, KFG kfg) {
                char grade;

                if (kfg.getKfg1() == -1 || kfg.getKfg2() == -1
                        || kfg.getKfg3() == -1 || kfg.getKfg4() == -1)
                    grade = 'N';
                else if (percent >= kfg.getKfg1())
                    grade = '1';
                else if (percent >= kfg.getKfg2())
                    grade = '2';
                else if (percent >= kfg.getKfg3())
                    grade = '3';
                else if (percent >= kfg.getKfg4())
                    grade = '4';
                else
                    grade = '5';

                return String.valueOf(grade);
            }

            @Override
            public void onButtonAction(Action action, ItemView itemView) {
                if (action == Action.Info) {
                    new StudentPanelHelper(studentTestResult, test);
                }
            }
        };
    }

    private QuestionItemView createNewQuestionItem(Question question) {
        return new QuestionItemView(test, question) {

            @Override
            public void onButtonAction(Action action, ItemView itemView) {
                if (action == Action.Delete) {
                    new ConfirmPanel("Confirm Deletement") {
                        @Override
                        public void passed() {
                            test.getQuestionList().remove(question);
                            questionListViews.getItems().remove((QuestionItemView) itemView);
                            updateQuestions();
                        }
                    };
                }
            }
        };
    }

    @FXML
    public void createNewQuestion(ActionEvent actionEvent) {
        Question question = new Question("", new ArrayList<>(), new ArrayList<>(), -1);
        test.getQuestionList().add(question);
        questionListViews.getItems().add(createNewQuestionItem(question));
    }

    @FXML
    public void clearData(ActionEvent actionEvent) {
        new ConfirmPanel("Confirm to clear Student Data") {
            @Override
            public void passed() {
                File file = new File(getITSManager().getWorkspaceHandler().getDataFolder().getPath()
                        + "//" + test.getName() + ".json");
                if (file.exists()) file.delete();
                workspace.getStudentResults().remove(test.getName());
                studentResultListViews.getItems().clear();
            }
        };
    }

    public void changeKFG(ActionEvent actionEvent) {
        KFG kfg = test.getKfg();
        try {
            if (!kfg1.getText().equals("")) kfg.setKfg1(Double.parseDouble(kfg1.getText()));
            if (!kfg2.getText().equals("")) kfg.setKfg2(Double.parseDouble(kfg2.getText()));
            if (!kfg3.getText().equals("")) kfg.setKfg3(Double.parseDouble(kfg3.getText()));
            if (!kfg4.getText().equals("")) kfg.setKfg4(Double.parseDouble(kfg4.getText()));
        } catch (Exception e) {
            new ErrorPanel("Input Value should be type Double", e);
        }
        studentResultListViews.getItems().forEach(StudentResultItemView::updateGrade);
    }

    private void configureKFG() {
        KFG kfg = test.getKfg();
        if (kfg.getKfg1() != -1) kfg1.setText(String.valueOf(kfg.getKfg1()));
        if (kfg.getKfg2() != -1) kfg2.setText(String.valueOf(kfg.getKfg2()));
        if (kfg.getKfg3() != -1) kfg3.setText(String.valueOf(kfg.getKfg3()));
        if (kfg.getKfg4() != -1) kfg4.setText(String.valueOf(kfg.getKfg4()));
    }

    public void updateQuestions() {
        for (int i = 0; i < questionListViews.getItems().size(); i++) {
            AnchorPane anchorPane = questionListViews.getItems().get(i);
            if (anchorPane instanceof QuestionItemView questionItemView)
                questionItemView.setIndex(i);
        }
    }

    @Override
    public void save() {
        new TestSaver(test, getITSManager());
    }

    private static EditviewController lastInstance;

    public static EditviewController getLastInstance() {
        return lastInstance;
    }
}
