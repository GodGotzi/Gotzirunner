package at.gotzi.itsmanager.view;

import at.gotzi.itsmanager.api.workspace.question.Input;
import at.gotzi.itsmanager.api.workspace.question.Question;
import at.gotzi.itsmanager.api.workspace.question.QuestionExample;
import at.gotzi.itsmanager.api.workspace.question.QuestionTest;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;

import java.util.Random;

public abstract class QuestionTestItemView<T extends Question.QuestionTest> extends AnchorPane implements ItemView {

    private final Question question;
    private final T questionTest;

    private ListView<QuestionTestInputItem> inputView;
    private TextField createInputField;
    private TextArea expectedArea;

    public QuestionTestItemView(Question question, T questionTest) {
        this.questionTest = questionTest;
        this.question = question;
        this.build();
    }

    @Override
    public void build() {
        this.init();
        this.initInputView();
        this.initCheckBox();
        this.initDelButton();
        this.initCreateButton();
        this.initReadOnlyLabel();
        this.initInputTextField();
        this.initExpectedArea();
        this.initInfoLabel();
        this.setRandomBackgroundColor();
    }

    @Override
    public void init() {
        setPrefHeight(110);
        setPrefWidth(380);
    }

    private void initInputView() {
        this.inputView = new ListView<>();
        inputView.setLayoutX(50);
        inputView.setLayoutY(33);
        inputView.setPrefHeight(74);
        inputView.setPrefWidth(114);
        questionTest.getInputs().forEach(input -> inputView.getItems().add(new QuestionTestInputItem(input) {
            @Override
            public void onButtonAction(Action action, ItemView itemView) {
                if (action == Action.Delete) {
                    inputView.getItems().remove((QuestionTestInputItem) itemView);
                    questionTest.getInputs().remove(input);
                }
            }
        }));
        getChildren().add(inputView);
    }

    private void initCheckBox() {
        CheckBox checkBox = new CheckBox();
        checkBox.setLayoutX(361);
        checkBox.setLayoutY(56);
        checkBox.setPrefHeight(17);
        checkBox.setPrefWidth(15);
        checkBox.setMnemonicParsing(false);
        checkBox.setSelected(true);
        checkBox.setFont(Font.font(10));
        checkBox.setOnAction(actionEvent -> expectedArea.setEditable(!checkBox.isSelected()));
        getChildren().add(checkBox);
    }

    private void setRandomBackgroundColor() {
        Random random = new Random();
        int r = random.nextInt(255);
        int g = random.nextInt(255);
        int b = random.nextInt(255);
        setStyle("-fx-background-color: rgba(" + r + "," + g + "," + b + ",0.25)" );
    }

    private void initReadOnlyLabel() {
        Label label = new Label();
        label.setLayoutX(343);
        label.setLayoutY(35);
        label.setText("Read only");
        getChildren().add(label);
    }

    private void initExpectedArea() {
        expectedArea = new TextArea();
        expectedArea.setLayoutX(191);
        expectedArea.setLayoutY(8);
        expectedArea.setPrefHeight(94);
        expectedArea.setPrefWidth(150);
        expectedArea.setEditable(false);
        getChildren().add(expectedArea);
    }

    private void initInfoLabel() {
        Label label = new Label();
        label.setLayoutX(14);
        label.setLayoutY(41);
        label.setPrefHeight(29);
        label.setPrefWidth(83);
        if (questionTest instanceof QuestionTest inOfQuestionTest)
            label.setText("Test" + question.getTests().indexOf(inOfQuestionTest)+1);
        else if (questionTest instanceof QuestionExample inOfQuestionExample)
            label.setText("Example" + question.getExamples().indexOf(inOfQuestionExample)+1);

        label.setFont(Font.font(8));
        getChildren().add(label);
    }

    private void initInputTextField() {
        createInputField = new TextField();
        createInputField.setLayoutX(53);
        createInputField.setLayoutY(8);
        createInputField.setPrefHeight(25);
        createInputField.setPrefWidth(69);
        getChildren().add(createInputField);
    }

    private void initCreateButton() {
        Button button = new Button();
        button.setLayoutX(122);
        button.setLayoutY(8);
        button.setMnemonicParsing(false);
        button.setPrefHeight(25);
        button.setPrefWidth(69);
        button.setText("Create Input");
        button.setFont(Font.font(10));
        button.setOnAction(actionEvent -> {
            if (createInputField.getText() != null && !createInputField.getText().equals("")) {
                Input input = new Input(createInputField.getText());
                questionTest.getInputs().add(input);
                createInputField.setText("");
                inputView.getItems().add(new QuestionTestInputItem(input) {

                    @Override
                    public void onButtonAction(Action action, ItemView itemView) {
                        if (action == Action.Delete)
                            inputView.getItems().remove((QuestionTestInputItem) itemView);
                    }
                });
            }
        });
        getChildren().add(button);
    }

    private void initDelButton() {
        Button button = new Button();
        button.setLayoutX(348);
        button.setLayoutY(8);
        button.setMnemonicParsing(false);
        button.setPrefHeight(17);
        button.setPrefWidth(43);
        button.setText("Del");
        button.setOnAction(actionEvent -> onButtonAction(Action.Delete, this));
        getChildren().add(button);
    }

    public void updateExpected(String expected) {
        expectedArea.setText(expected);
    }

    private static abstract class QuestionTestInputItem extends AnchorPane implements ItemView {

        private final Input input;

        public QuestionTestInputItem(Input input) {
            this.input = input;
        }

        @Override
        public void build() {
            this.initDelButton();
            this.initInfoLabel();
        }

        @Override
        public void init() {

        }

        private void initInfoLabel() {
            Label label = new Label();
            label.setText(input.input());
            getChildren().add(label);
        }

        private void initDelButton() {
               Button button = new Button();
               button.setLayoutX(25);
               button.setText("Del");
               button.setOnAction(actionEvent -> onButtonAction(Action.Delete, this));
               getChildren().add(button);
        }
    }
}
