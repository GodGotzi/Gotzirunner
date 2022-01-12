package at.gotzi.itsmanager.api.workspace;

import java.util.List;

public record StudentTestResult(String name, int kn, int time, double points, double percent, List<StudentTestResultQuestion> studentResultQuestions) {}
