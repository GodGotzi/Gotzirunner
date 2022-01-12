package at.gotzi.itsmanager.view;

import at.gotzi.itsmanager.ITSManager;
import at.gotzi.itsmanager.api.WindowScene;
import at.gotzi.itsmanager.api.workspace.Test;
import at.gotzi.itsmanager.controller.EditviewController;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;

import java.util.Random;

public abstract class TestItemView extends AnchorPane implements ItemView {

    private final Test test;

    public TestItemView(Test test) {
        this.test = test;
        this.build();
    }

    @Override
    public void build() {
        this.init();
        this.initImage();
        this.initTestName();
        this.initIndexLabel();
        this.initInfoArea();
        this.initInfoLabel();
        this.initStartButton();
        this.initDelButton();
        this.initManageButton();
        this.initTopicArea();
        this.initTopicLabel();
        this.setRandomBackgroundColor();
    }

    @Override
    public void init() {
        setPrefHeight(160);
        setPrefWidth(850);
    }

    private void initImage() {
        ImageView imageView = new ImageView();
        imageView.setFitHeight(130);
        imageView.setFitWidth(1000);
        imageView.setLayoutX(13);
        imageView.setLayoutY(13);
        imageView.setPickOnBounds(true);
        imageView.setPreserveRatio(true);
        imageView.setImage(new Image("/TestPanel.png"));
        getChildren().add(imageView);
    }

    private void initTestName() {
        Label name = new Label();
        name.setLayoutX(28);
        name.setLayoutY(54);
        name.setPrefHeight(32);
        name.setPrefWidth(330);
        name.setText(test.getName());
        name.setFont(Font.font(22));
        getChildren().add(name);
    }

    private void initIndexLabel() {
        Label index = new Label();
        index.setLayoutX(28);
        index.setLayoutY(31);
        index.setPrefHeight(23);
        index.setPrefWidth(28);
        index.setText(String.valueOf(test.getIndex()));
        index.setFont(Font.font(13));
        getChildren().add(index);
    }

    private void initInfoLabel() {
        Label information = new Label();
        information.setAlignment(Pos.CENTER);
        information.setLayoutX(600);
        information.setLayoutY(22);
        information.setPrefHeight(23);
        information.setPrefWidth(84);
        information.setText("Information");
        information.setFont(Font.font(15));
        getChildren().add(information);
    }

    private void initInfoArea() {
        TextArea informationArea = new TextArea();
        informationArea.setLayoutX(555);
        informationArea.setLayoutY(50);
        informationArea.setPrefHeight(68);
        informationArea.setPrefWidth(180);
        informationArea.setEditable(false);
        getChildren().add(informationArea);
    }

    //ToDo
    private void initStartButton() {
        Button startButton = new Button();
        startButton.setLayoutX(805);
        startButton.setLayoutY(45);
        startButton.setMnemonicParsing(false);
        startButton.setPrefHeight(40);
        startButton.setPrefWidth(102);
        startButton.setText("Host Test/Start");
        getChildren().add(startButton);
    }

    private void initDelButton() {
        Button delButton = new Button();
        delButton.setLayoutX(795);
        delButton.setLayoutY(90);
        delButton.setMnemonicParsing(false);
        delButton.setOnAction(actionEvent -> onButtonAction(Action.Delete, this));
        delButton.setText("Delete");
        getChildren().add(delButton);
    }

    private void initManageButton() {
        Button manageButton = new Button();
        manageButton.setLayoutX(855);
        manageButton.setLayoutY(90);
        manageButton.setMnemonicParsing(false);
        manageButton.setOnAction(actionEvent -> {
            ITSManager.getInstance().getSceneHandler().setCurrentScene(WindowScene.Editview,
                    true, false);
            EditviewController.getLastInstance().init(test);
        });
        manageButton.setText("Manage");
        getChildren().add(manageButton);
    }

    private void initTopicArea() {
        TextArea topicArea = new TextArea();
        topicArea.setLayoutX(400);
        topicArea.setLayoutY(66);
        topicArea.setPrefHeight(23);
        topicArea.setPrefWidth(131);
        topicArea.setEditable(false);
        getChildren().add(topicArea);
    }

    private void initTopicLabel() {
        Label label = new Label();
        label.setAlignment(Pos.CENTER);
        label.setLayoutX(440);
        label.setLayoutY(36);
        label.setPrefHeight(23);
        label.setPrefWidth(51);
        label.setText("Topics");
        label.setFont(Font.font(15));
        getChildren().add(label);
    }

    private void setRandomBackgroundColor() {
        Random random = new Random();
        int r = random.nextInt(255);
        int g = random.nextInt(255);
        int b = random.nextInt(255);
        setStyle("-fx-background-color: rgba(" + r + "," + g + "," + b + ",0.5)" );
    }

    public Test getTest() {
        return test;
    }
}
