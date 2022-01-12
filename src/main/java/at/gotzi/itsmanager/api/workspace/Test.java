package at.gotzi.itsmanager.api.workspace;

import at.gotzi.itsmanager.api.workspace.question.Question;

import java.util.List;

public class Test {

    private String name;
    private List<Question> questionList;
    private final int index;
    private final KFG kfg;

    public Test(String name, int index, List<Question> questionList, KFG kfg) {
        this.name = name;
        this.questionList = questionList;
        this.index = index;
        this.kfg = kfg;
    }

    public int getMaxPoints() {
        int counter = 0;
        if (questionList.isEmpty()) return -1;
        for (Question question : questionList.stream().filter(t -> t.getPoints() != -1).toList()) {
            counter += question.getPoints();
        }
        return counter;
    }

    public KFG getKfg() {
        return kfg;
    }

    public int getIndex() {
        return index;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setQuestionList(List<Question> questionList) {
        this.questionList = questionList;
    }

    public List<Question> getQuestionList() {
        return questionList;
    }

    public String getName() {
        return name;
    }
}
