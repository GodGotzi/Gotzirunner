package at.gotzi.itsmanager.api.workspace;

import java.util.List;
import java.util.Map;

public class Workspace {


    private final List<Test> tests;
    private final Map<String, List<StudentTestResult>> studentResults;
    private final String path;

    public Workspace(List<Test> testList, Map<String, List<StudentTestResult>> studentResults, String path) {
        this.tests = testList;
        this.path = path;
        this.studentResults = studentResults;
    }

    public Map<String, List<StudentTestResult>> getStudentResults() {
        return studentResults;
    }

    public List<Test> getTests() {
        return tests;
    }

    public String getPath() {
        return path;
    }
}