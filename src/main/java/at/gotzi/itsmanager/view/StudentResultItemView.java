package at.gotzi.itsmanager.view;

import at.gotzi.itsmanager.api.workspace.KFG;
import at.gotzi.itsmanager.api.workspace.StudentTestResult;
import at.gotzi.itsmanager.api.workspace.Test;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;

import java.util.Random;

public abstract class StudentResultItemView extends AnchorPane implements ItemView {

    private final StudentTestResult studentTestResult;
    private final Test test;

    private Label grade;

    public StudentResultItemView(StudentTestResult studentTestResult, Test test) {
        this.studentTestResult = studentTestResult;
        this.test = test;

        this.build();
    }

    @Override
    public void build() {
        this.init();
        this.initImageViewBackGround();
        this.initNameLabel();
        this.initKnLabel();
        this.initPointsLabel();
        this.initTimeLabel();
        this.initPercentLabel();
        this.initGrade();
        this.initInfoButton();
        this.setRandomBackgroundColor();
    }

    @Override
    public void init() {
        setPrefHeight(65);
        setPrefWidth(565);
    }

    private void initImageViewBackGround() {
        ImageView imageView = new ImageView();
        imageView.setFitHeight(65);
        imageView.setFitWidth(550);
        imageView.setLayoutX(8);
        imageView.setLayoutY(5);
        imageView.setPickOnBounds(true);
        imageView.setPreserveRatio(true);
        imageView.setImage(new Image("/TestPanel2.png"));
        getChildren().add(imageView);
    }

    private void initNameLabel() {
        Label name = new Label();
        name.setLayoutX(14);
        name.setLayoutY(18);
        name.setPrefHeight(30);
        name.setPrefWidth(250);
        name.setText(studentTestResult.name());
        name.setFont(Font.font(20));
        getChildren().add(name);
    }

    private void initKnLabel() {
        Label kn = new Label();
        kn.setLayoutX(263);
        kn.setLayoutY(18);
        kn.setPrefHeight(30);
        kn.setPrefWidth(71);
        kn.setText("KN: " + studentTestResult.kn());
        kn.setFont(Font.font(20));
        getChildren().add(kn);
    }
    
    private void addInfoLabel(double y, String value) {
        Label label = new Label();
        label.setLayoutX(350);
        label.setLayoutY(y);
        label.setPrefHeight(23);
        label.setPrefWidth(100);
        label.setText(value);
        getChildren().add(label);
    }
    
    private void initTimeLabel() {
        this.addInfoLabel(3, studentTestResult.time()/60 + "min " +
                studentTestResult.time()%60 + "sec");
    }
    
    private void initPointsLabel() {
        this.addInfoLabel(21, studentTestResult.points() + "/" +
                test.getMaxPoints() + " P");
    }
    
    private void initPercentLabel() {
        this.addInfoLabel(40, studentTestResult.percent() + "%");
    }

    private void initGrade() {
        this.grade = new Label();
        grade.setLayoutX(416);
        grade.setLayoutY(6);
        grade.setPrefHeight(54);
        grade.setPrefWidth(24);
        grade.setText(getGrade(studentTestResult.percent(), test.getKfg()));
        grade.setFont(Font.font(36));
        getChildren().add(grade);
    }

    private void initInfoButton() {
        Button infoButton = new Button();
        infoButton.setLayoutX(480);
        infoButton.setLayoutY(14);
        infoButton.setMnemonicParsing(false);
        infoButton.setPrefHeight(37);
        infoButton.setPrefWidth(71);
        infoButton.setText("Info");
        infoButton.setOnAction(actionEvent -> onButtonAction(Action.Info, this));
        getChildren().add(infoButton);
    }

    private void setRandomBackgroundColor() {
        Random random = new Random();
        int r = random.nextInt(255);
        int g = random.nextInt(255);
        int b = random.nextInt(255);
        setStyle("-fx-background-color: rgba(0,0,0,0.25)" );
    }

    public int getKN() {
        return studentTestResult.kn();
    }

    public void updateGrade() {
        grade.setText(getGrade(studentTestResult.percent(), test.getKfg()));
    }

    public abstract String getGrade(double percent, KFG kfg);
}
