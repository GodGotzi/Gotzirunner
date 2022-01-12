package at.gotzi.itsmanager.api.workspace.question;

import java.util.ArrayList;
import java.util.List;

public class Question {

    private String description;
    private final List<QuestionExample> questionExamples;
    private final List<at.gotzi.itsmanager.api.workspace.question.QuestionTest> tests;
    private int points;

    public Question(String description, List<QuestionExample> questionExamples, List<at.gotzi.itsmanager.api.workspace.question.QuestionTest> tests, int points) {
        this.description = description;
        this.questionExamples = questionExamples;
        this.tests = tests;
        this.points = points;
    }

    public List<QuestionExample> getExamples() {
        return questionExamples;
    }

    public List<at.gotzi.itsmanager.api.workspace.question.QuestionTest> getTests() {
        return tests;
    }

    public String getDescription() {
        return description;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static enum QuestionMode {
        Normal,
        Formatting
    }

    public static class QuestionTest {
        private final List<Input> inputs = new ArrayList<>();
        private String expected = "";

        public QuestionTest() {
        }

        public void setExpected(String expected) {
            this.expected = expected;
        }

        public List<Input> getInputs() {
            return inputs;
        }

        public String getExpected() {
            return expected;
        }
    }
}


