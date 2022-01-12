package at.gotzi.itsmanager.controller.panelcontroll;

import at.gotzi.itsmanager.api.workspace.StudentTestResult;
import at.gotzi.itsmanager.api.workspace.StudentTestResultQuestion;
import at.gotzi.itsmanager.api.workspace.Test;
import at.gotzi.itsmanager.api.workspace.question.Question;
import at.gotzi.itsmanager.helper.StudentQuestionPanelHelper;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;

import java.util.ArrayList;

public class StudentPanelController {

    public StudentPanelController() {lastInstance = this;}

    @FXML
    private Label name;

    @FXML
    private Label time;

    @FXML
    private Label points;

    @FXML
    private Label percent;

    @FXML
    private Label kn;

    @FXML
    private ListView<AnchorPane> paneListView;

    private StudentTestResult studentResult;

    private Test test;

    public void initStudentPanel(StudentTestResult studentResult, Test test) {
        this.test = test;
        this.studentResult = studentResult;
        this.initNodes();
        this.initQuestionList();
    }

    private void initNodes() {
        int maxPoints = test.getMaxPoints();
        name.setText(studentResult.name());
        time.setText("Time: " + (studentResult.time()/60) + "min " + (studentResult.time()%60) + "sec");
        points.setText("Points: " + studentResult.points() + "/" + maxPoints + "P");
        percent.setText("Percentage: " + studentResult.percent() + "%");
        kn.setText("KN: " + studentResult.kn());
    }

    private void initQuestionList() {
        for (int i = 0; i < studentResult.studentResultQuestions().size(); i++) {
            StudentTestResultQuestion studentTestResultQuestion = studentResult.studentResultQuestions().get(i);
            Question question = getQuestion(studentTestResultQuestion);
            AnchorPane anchorPane = new AnchorPane();

            anchorPane.setStyle("-fx-background-color: " + getPassedColor(question, studentTestResultQuestion));
            anchorPane.setPrefWidth(600);
            anchorPane.setPrefHeight(75);
            this.setChildrenForAnchorPane(anchorPane, question, studentTestResultQuestion, i+1);
            paneListView.getItems().add(anchorPane);
        }
    }

    private void setChildrenForAnchorPane(AnchorPane anchorPane, Question question,
                                          StudentTestResultQuestion studentTestResultQuestion, int index) {
        Label questionLabel = createQuestionLabel(question, studentTestResultQuestion, index);
        Button showButton = createShowButton();

        showButton.setOnAction(actionEvent -> {
            new StudentQuestionPanelHelper(studentResult, studentTestResultQuestion, test);
        });

        anchorPane.getChildren().add(questionLabel);
        anchorPane.getChildren().add(showButton);
    }

    private Button createShowButton() {
        Button button = new Button();
        button.setLayoutY(10);
        button.setLayoutX(650);
        button.setFont(Font.font("Verdana", 25));
        button.setText("Show");
        return button;
    }

    private Label createQuestionLabel(Question question, StudentTestResultQuestion studentTestResultQuestion, int index) {
        Label label = new Label();
        label.setLayoutY(20);
        label.setLayoutX(10);
        label.setFont(Font.font("Verdana", 25));
        label.setText("Question " + index + " " + getPassedQuestionLabel(question, studentTestResultQuestion));

        return label;
    }

    private Question getQuestion(StudentTestResultQuestion studentResultQuestion) {
        Question question;
        int questionIndex = studentResult.studentResultQuestions().indexOf(studentResultQuestion);
        if (questionIndex < test.getQuestionList().size())
            question = test.getQuestionList().get(questionIndex);
        else
            question = createEmptyQuestion();

        return question;
    }

    private String getPassedQuestionLabel(Question question, StudentTestResultQuestion studentTestResultQuestion) {
        if (question.getPoints() == studentTestResultQuestion.points())
            return "right";
        else if (question.getPoints() == -1)
            return "(no question exists)";
        else
            return "wrong";
    }

    private String getPassedColor(Question question, StudentTestResultQuestion studentTestResultQuestion) {
        if (question.getPoints() == studentTestResultQuestion.points())
            return "rgba(0,128,0,0.16)";
        else if (question.getPoints() == -1)
            return "rgba(255,255,0,0.16)";
        else
            return "rgba(0,128,0,0.16)";
    }

    private Question createEmptyQuestion() {
        return new Question("", new ArrayList<>(), new ArrayList<>(), -1);
    }

    private static StudentPanelController lastInstance;

    public static StudentPanelController getLastInstance() {
        return lastInstance;
    }
}
