package at.gotzi.itsmanager.view;

import at.gotzi.itsmanager.api.workspace.Test;
import at.gotzi.itsmanager.api.workspace.question.Question;
import at.gotzi.itsmanager.helper.QuestionEditPanelHelper;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;

import java.util.Random;

public abstract class QuestionItemView extends AnchorPane implements ItemView {

    private final Test test;
    private final Question question;

    public Label points;
    public TextArea descriptionArea;

    private Label info;

    public QuestionItemView(Test test, Question question) {
        this.test = test;
        this.question = question;
        this.build();
    }

    @Override
    public void build() {
        this.init();
        this.setRandomBackgroundColor();
        this.setQuestionInfoLabel(test.getQuestionList().indexOf(question));
        this.setDeleteButton();
        this.setDescriptionArea();
        this.setEditButton();
        this.setPointsInfoLabel();
        this.setDescriptionTitle();
    }

    @Override
    public void init() {
        setPrefHeight(200);
        setPrefWidth(820);
    }

    private void setRandomBackgroundColor() {
        Random random = new Random();
        int r = random.nextInt(255);
        int g = random.nextInt(255);
        int b = random.nextInt(255);
        setStyle("-fx-background-color: rgba(" + r + "," + g + "," + b + ",0.25)" );
    }

    private void setQuestionInfoLabel(int index) {
        Label info = new Label();
        info.setLayoutX(29);
        info.setLayoutY(75);
        info.setPrefHeight(51);
        info.setPrefWidth(241);
        info.setText("Question " + (index+1));
        info.setFont(Font.font(30));
        this.info = info;
        getChildren().add(info);
    }

    private void setEditButton() {
        Button editButton = new Button();
        editButton.setLayoutX(686);
        editButton.setLayoutY(14);
        editButton.setMnemonicParsing(false);
        editButton.setPrefHeight(51);
        editButton.setPrefWidth(120);
        editButton.setText("Edit");

        editButton.setOnAction(actionEvent -> {
            new QuestionEditPanelHelper(question, test, s -> descriptionArea.setText(s), integer -> {
                points.setText("Points: " + integer + "P");
            });
        });

        getChildren().add(editButton);
    }

    private void setDeleteButton() {
        Button deleteButton = new Button();
        deleteButton.setLayoutX(686);
        deleteButton.setLayoutY(139);
        deleteButton.setMnemonicParsing(false);
        deleteButton.setPrefHeight(51);
        deleteButton.setPrefWidth(120);
        deleteButton.setText("Delete");
        deleteButton.setOnAction(actionEvent -> onButtonAction(Action.Delete, this));
        getChildren().add(deleteButton);
    }

    private void setPointsInfoLabel() {
        Label info = new Label();
        info.setAlignment(Pos.CENTER);
        info.setLayoutX(29);
        info.setLayoutY(126);
        info.setPrefHeight(26);
        info.setPrefWidth(176);
        info.setText("Points: " + question.getPoints() + "P");
        info.setFont(Font.font(16));

        this.points = info;
        getChildren().add(info);
    }

    private void setDescriptionArea() {
        TextArea textArea = new TextArea();
        textArea.setLayoutX(214);
        textArea.setLayoutY(23);
        textArea.setPrefHeight(170);
        textArea.setPrefWidth(459);
        textArea.setText(question.getDescription());
        textArea.setEditable(false);
        this.descriptionArea = textArea;
        getChildren().add(textArea);
    }

    private void setDescriptionTitle() {
        Label info = new Label();
        info.setAlignment(Pos.CENTER);
        info.setLayoutX(333);
        info.setLayoutY(2);
        info.setPrefHeight(17);
        info.setPrefWidth(221);
        info.setText("Description");
        info.setFont(Font.font(16));
        getChildren().add(info);
    }

    public void setIndex(int index) {
        this.info.setText("Question " + (index+1));
    }

    public Question getQuestion() {
        return question;
    }

    public Test getTest() {
        return test;
    }
}
