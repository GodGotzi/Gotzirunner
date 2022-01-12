package at.gotzi.itsmanager.helper;

import at.gotzi.itsmanager.ITSManager;
import at.gotzi.itsmanager.api.workspace.StudentTestResult;
import at.gotzi.itsmanager.api.workspace.StudentTestResultQuestion;
import at.gotzi.itsmanager.handler.StageHandler;
import eu.mihosoft.monacofx.MonacoFX;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class SourceCodePanelHelper {

    public SourceCodePanelHelper(StudentTestResult studentResult, StudentTestResultQuestion studentResultQuestion) {
        String sourceCode = studentResultQuestion.sourceCode();

        Stage stage = new Stage();
        MonacoFX monacoFX = new MonacoFX();
        StackPane root = new StackPane(monacoFX);

        monacoFX.getEditor().getDocument().setText(sourceCode + "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");

        monacoFX.getEditor().setCurrentLanguage("c");
        monacoFX.getEditor().setCurrentTheme("vs-dark");

        monacoFX.setOnKeyPressed(keyEvent -> {
            System.out.println("hey");
            monacoFX.getEditor().getViewController().undo();
        });

        monacoFX.setOnKeyTyped(keyEvent -> {
            monacoFX.getEditor().getViewController().undo();
        });

        monacoFX.setOnKeyReleased(keyEvent -> {
            monacoFX.getEditor().getViewController().undo();
        });

        Scene scene = new Scene(root, 800, 600);
        stage.setTitle(studentResult.name() + " Question " +
                studentResult.studentResultQuestions().indexOf(studentResultQuestion));
        stage.setScene(scene);
        stage.getIcons().add(ITSManager.image);
        stage.show();
        StageHandler.getStageList().add(stage);
    }

    public SourceCodePanelHelper(String sourceCode) {
        Stage stage = new Stage();
        MonacoFX monacoFX = new MonacoFX();
        StackPane root = new StackPane(monacoFX);

        monacoFX.getEditor().getDocument().setText(sourceCode);

        monacoFX.getEditor().setCurrentLanguage("c");
        monacoFX.getEditor().setCurrentTheme("vs-dark");

        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("Source Code Panel");
        stage.setScene(scene);
        stage.getIcons().add(ITSManager.image);
        stage.show();
        StageHandler.getStageList().add(stage);
    }
}
