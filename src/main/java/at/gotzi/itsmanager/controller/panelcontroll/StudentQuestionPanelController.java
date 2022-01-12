package at.gotzi.itsmanager.controller.panelcontroll;

import at.gotzi.itsmanager.api.workspace.StudentTestResult;
import at.gotzi.itsmanager.api.workspace.StudentTestResultQuestion;
import at.gotzi.itsmanager.api.workspace.StudentTestResultQuestionTest;
import at.gotzi.itsmanager.api.workspace.Test;
import at.gotzi.itsmanager.api.workspace.question.Input;
import at.gotzi.itsmanager.api.workspace.question.Question;
import at.gotzi.itsmanager.api.workspace.question.QuestionTest;
import at.gotzi.itsmanager.helper.SourceCodePanelHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

public class StudentQuestionPanelController {

    public StudentQuestionPanelController() {
        lastInstance = this;
    }

    @FXML
    private Label question;

    @FXML
    private Label kn;

    @FXML
    private Label name;

    @FXML
    private Label points;

    @FXML
    private ListView<AnchorPane> paneListView;

    private StudentTestResult studentResult;
    private StudentTestResultQuestion studentResultQuestion;
    private Test test;
    private Question q;

    public void initQuestionController(StudentTestResult studentResult,
                                       StudentTestResultQuestion studentResultQuestion, Test test) {
        this.studentResult = studentResult;
        this.studentResultQuestion = studentResultQuestion;
        this.test = test;
        this.q = getQuestion();

        this.initTestCaseList();
        this.initNodes();
    }

    private void initTestCaseList() {
        for (int i = 0; i < studentResultQuestion.questionTests().size(); i++) {
            StudentTestResultQuestionTest studentResultQuestionTest = studentResultQuestion.questionTests().get(i);
            AnchorPane anchorPane = new AnchorPane();
            QuestionTest questionTest = getQuestionTest(studentResultQuestionTest, studentResultQuestion);

            anchorPane.setStyle("-fx-background-color: " + getPassedColor(questionTest, studentResultQuestionTest));
            anchorPane.setPrefWidth(345);
            anchorPane.setPrefHeight(100);
            this.setChildrenForAnchorPane(anchorPane,
                    studentResultQuestionTest, questionTest, i);
            paneListView.getItems().add(anchorPane);
        }
    }

    private void initNodes() {
        question.setText("Question " + (studentResult.studentResultQuestions().indexOf(studentResultQuestion)+1));
        kn.setText("KN: " + studentResult.kn());
        name.setText(studentResult.name());
        points.setText(studentResultQuestion.points()  + "/" + q.getPoints() + "P");
    }

    private void setChildrenForAnchorPane(AnchorPane anchorPane,
                                          StudentTestResultQuestionTest studentResultQuestionTest,
                                          QuestionTest questionTest, int index) {
        Label testLabel = createTestLabel(questionTest, studentResultQuestionTest, index);
        ListView<Label> inputView = createInputView();
        ScrollPane expectedFlow = createExpectedFlow();
        ScrollPane outputFlow = createOutputFlow();

        //set shit
        this.setInputs(inputView, questionTest);
        this.setExpected(expectedFlow, questionTest, studentResultQuestionTest.output());
        this.setOutput(outputFlow, studentResultQuestionTest.output());

        anchorPane.getChildren().add(outputFlow);
        anchorPane.getChildren().add(expectedFlow);
        anchorPane.getChildren().add(inputView);
        anchorPane.getChildren().add(testLabel);
    }

    private QuestionTest getQuestionTest(StudentTestResultQuestionTest studentResultQuestionTest,
                                         StudentTestResultQuestion studentResultQuestion) {
        QuestionTest questionTest;
        int questionTestIndex = studentResultQuestion.questionTests().indexOf(studentResultQuestionTest);
        if (questionTestIndex > q.getTests().size())
            questionTest = q.getTests().get(questionTestIndex);
        else
            questionTest = createEmptyQuestionTest();
        return questionTest;
    }

    private Question getQuestion() {
        Question question;
        int questionIndex = studentResult.studentResultQuestions().indexOf(studentResultQuestion);
        if (questionIndex < test.getQuestionList().size())
            question = test.getQuestionList().get(questionIndex);
        else
            question = createEmptyQuestion();

        return question;
    }

    private Question createEmptyQuestion() {
        return new Question("", new ArrayList<>(), new ArrayList<>(), -1);
    }

    private void setInputs(ListView<Label> listView, QuestionTest questionTest) {
        for (int i = 0; i < questionTest.getInputs().size(); i++) {
            String input = questionTest.getInputs().get(i).input();
            Label l = new Label();
            l.setText(input);
            l.setFont(Font.font("Verdana", 10));
            listView.getItems().add(l);
        }
    }

    private void setExpected(ScrollPane scrollPane, QuestionTest questionTest, String output) {
        AnchorPane anchorPane = new AnchorPane();
        char[] expectedChars = questionTest.getExpected().toCharArray();
        char[] outputChars = output.toCharArray();
        double colum = 0;
        double row = 0;
        for (int index = 0; index < expectedChars.length; index++) {
            Label label = new Label();
            label.setText(String.valueOf(expectedChars[index]));
            label.setLayoutX(colum);
            label.setLayoutY(row);
            label.setFont(Font.font("Verdana", 10));

            if (!isSame(expectedChars, outputChars, index))
                label.setStyle("-fx-background-color: yellow");

            Text text = new Text(label.getText());
            if (expectedChars[index] == '\n') {
                row += text.getLayoutBounds().getHeight();
                colum = 0;
            } else colum += text.getLayoutBounds().getWidth();

            anchorPane.getChildren().add(label);
        }
        scrollPane.setContent(anchorPane);
    }

    private void setOutput(ScrollPane scrollPane, String output) {
        AnchorPane anchorPane = new AnchorPane();
        double colum = 0;
        double row = 0;
        for (char outputChar : output.toCharArray()) {
            Label label = new Label();
            label.setText(String.valueOf(outputChar));
            label.setLayoutX(colum);
            label.setLayoutY(row);
            label.setFont(Font.font("Verdana", 10));

            Text text = new Text(label.getText());
            if (outputChar == '\n') {
                row += text.getLayoutBounds().getHeight();
                colum = 0;
            } else colum += text.getLayoutBounds().getWidth();

            anchorPane.getChildren().add(label);
        }
        scrollPane.setContent(anchorPane);
    }

    private boolean isSame(char[] expectedChars, char[] outputChars, int index) {
        if (outputChars.length > index) {
            return expectedChars[index] == outputChars[index];
        } else return false;
    }

    private ScrollPane createExpectedFlow() {
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setStyle("-fx-background-color: " + "#ffffff");
        scrollPane.setLayoutY(5);
        scrollPane.setLayoutX(385);
        scrollPane.setPrefHeight(90);
        scrollPane.setPrefWidth(150);
        return scrollPane;
    }

    private ScrollPane createOutputFlow() {
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setStyle("-fx-background-color: " + "#ffffff");
        scrollPane.setLayoutY(5);
        scrollPane.setLayoutX(550);
        scrollPane.setPrefHeight(90);
        scrollPane.setPrefWidth(150);
        return scrollPane;
    }

    private ListView<Label> createInputView() {
        ListView<Label> listView = new ListView<>();
        listView.setLayoutY(5);
        listView.setLayoutX(225);
        listView.setPrefHeight(90);
        listView.setPrefWidth(120);
        return listView;
    }

    private QuestionTest createEmptyQuestionTest() {
        QuestionTest questionTest = new QuestionTest();
        questionTest.setExpected("(No expected String exists)");
        List<Input> inputList = new ArrayList<>();
        inputList.add(new Input("(No input exists)"));
        questionTest.getInputs().addAll(inputList);
        return questionTest;
    }

    private Label createTestLabel(QuestionTest questionTest, StudentTestResultQuestionTest studentTestResultQuestionTest, int index) {
        Label label = new Label();
        label.setFont(Font.font("Verdana", 20));
        label.setLayoutY(37.5);
        if (questionTest.getExpected().equals(studentTestResultQuestionTest.output()))
            label.setText("Test " + (index+1) + " " + "passed");
        else if (questionTest.getExpected().equals("(No expected String exists)"))
            label.setText("Test " + (index+1) + " " + "passed/failed");
        else
            label.setText("Test " + (index+1) + " " + "failed");
        return label;
    }

    private String getPassedColor(QuestionTest questionTest, StudentTestResultQuestionTest studentTestResultQuestionTest) {
        if (questionTest.getExpected().equals(studentTestResultQuestionTest.output()))
            return "rgba(0,128,0,0.16)";
        else if (questionTest.getExpected().equals("(No expected String exists)"))
            return "rgba(255,255,0,0.16)";
        else
            return "rgba(0,128,0,0.16)";
    }

    public void showCode(ActionEvent actionEvent) {
        new SourceCodePanelHelper(studentResult, studentResultQuestion);
    }

    private static StudentQuestionPanelController lastInstance;

    public static StudentQuestionPanelController getLastInstance() {
        return lastInstance;
    }
}
