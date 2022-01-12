package at.gotzi.itsmanager.controller;

import at.gotzi.itsmanager.api.Sorter;
import at.gotzi.itsmanager.api.workspace.Test;
import at.gotzi.itsmanager.file.TestCreator;
import at.gotzi.itsmanager.helper.controller.SceneController;
import at.gotzi.itsmanager.panel.control.ConfirmPanel;
import at.gotzi.itsmanager.panel.control.MessagePanel;
import at.gotzi.itsmanager.view.ItemView;
import at.gotzi.itsmanager.view.TestItemView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class OverviewController extends SceneController implements Initializable {

    @FXML
    public ListView<TestItemView> testItemViews;

    @FXML
    public Label workspaceLabel;

    @FXML
    public void createTest(ActionEvent actionEvent) {
        new MessagePanel("""
                Bitte benutzten Sie einen informativen Namen für ihren Test.
                Ich empfehle Ihnen etwas wie "BHME20T1J22" zu benutzen.
                "BHME20" steht für die Klasse
                "T1" steht für die Test Nummer
                Und "J22" für das Jahr""") {
            @Override
            public void passed() {
                Test test = new TestCreator(this.getInput(), getITSManager()).getTest();
                createTestItemView(test);
                update();
            }
        };
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        workspaceLabel.setText(getITSManager().getWorkspaceHandler().getCurrentWorkspace().getPath());
        this.initTestViews();
    }

    private void createTestItemView(Test test) {
        testItemViews.getItems().add(new TestItemView(test) {
            @Override
            public void onButtonAction(Action action, ItemView itemView) {
                if (action == Action.Delete) {
                    new ConfirmPanel("Sind Sie sich sicher, dass dieser Test gelöscht werden sollte?") {
                        @Override
                        public void passed() {
                            testItemViews.getItems().remove((TestItemView) itemView);
                            getITSManager().getWorkspaceHandler().deleteTest(test);
                            update();
                        }
                    };
                }
            }
        });
    }

    private void initTestViews() {
        getITSManager().getWorkspaceHandler()
                .getWorkspace().getTests().forEach(this::createTestItemView);
        update();
    }

    private void update() {
        TestItemView[] testItems = testItemViews.getItems().toArray(new TestItemView[0]);
        testItemViews.getItems().clear();
        testItemViews.getItems().addAll(Sorter.sortTestsViaIndex(testItems));
    }
}