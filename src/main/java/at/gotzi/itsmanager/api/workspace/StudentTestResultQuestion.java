package at.gotzi.itsmanager.api.workspace;

import java.util.List;

public record StudentTestResultQuestion(double points, String sourceCode, List<StudentTestResultQuestionTest> questionTests) { }
