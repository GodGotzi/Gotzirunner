package at.gotzi.itsmanager.handler;

import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class StageHandler {

    private static final List<Stage> stageList = new ArrayList<>();

    public static List<Stage> getStageList() {return stageList;}
}